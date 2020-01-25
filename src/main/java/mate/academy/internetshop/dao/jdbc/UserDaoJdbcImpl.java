package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        User newUser = new User(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setSecondName(user.getSecondName());
        newUser.addRoles(user.getRoles());

        String query = "INSERT INTO users (username, password,"
                + " first_name, second_name) VALUES (?, ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                Long userId = rs.getLong(1);
                newUser.setId(userId);
            }
            addRoles(newUser, user.getRoles());
            return newUser;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create user: " + e);
        }
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = userSetFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user: " + e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String query = "UPDATE users SET username = ?, password = ?, first_name = ?,"
                + " second_name = ? WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.setLong(5, user.getId());
            statement.executeUpdate();

            Set<Role> oldRoles = getRoles(user);
            Set<Role> newRoles = user.getRoles();

            Set<Role> rolesToDelete = new HashSet<>(oldRoles);
            rolesToDelete.removeAll(newRoles);
            deleteRoles(user, rolesToDelete);

            Set<Role> rolesToAdd = new HashSet<>(newRoles);
            rolesToAdd.removeAll(oldRoles);
            addRoles(user, rolesToAdd);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update user: " + e);
        }
        return user;
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        User user = get(id).orElseThrow(NoSuchElementException::new);
        return delete(user);
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        deleteRoles(user, user.getRoles());
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete user: " + e);
        }
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = userSetFields(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all users: " + e);
        }
        return users;
    }

    @Override
    public Optional<User> findByUsername(String username) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE username = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = userSetFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user: " + e);
        }
        return Optional.empty();
    }

    private User userSetFields(ResultSet rs) throws SQLException, DataProcessingException {
        Long userId = rs.getLong("user_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstName = rs.getString("first_name");
        String secondName = rs.getString("second_name");
        User user = new User(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setId(userId);
        user.setRoles(getRoles(user));
        return user;
    }

    private void addRoles(User user, Set<Role> roles) throws DataProcessingException {
        String query = "INSERT INTO user_roles (user_id, role_id) VALUES"
                + " (?, (SELECT role_id FROM roles WHERE role_name = ?));";
        changeRolesExecute(user, roles, query);
    }

    private void deleteRoles(User user, Set<Role> roles) throws DataProcessingException {
        String query = "DELETE FROM user_roles WHERE user_id = ? AND "
                + "role_id = (SELECT role_id FROM roles WHERE role_name = ?);";
        changeRolesExecute(user, roles, query);
    }

    private void changeRolesExecute(User user, Set<Role> roles, String query)
            throws DataProcessingException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Role role : roles) {
                statement.setLong(1, user.getId());
                statement.setString(2, role.getRoleName().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update user roles: " + e);
        }
    }

    private Set<Role> getRoles(User user) throws DataProcessingException {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT role_name, r.role_id FROM roles r JOIN user_roles ur"
                + " ON r.role_id = ur.role_id AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String roleName = rs.getString("role_name");
                Long roleId = rs.getLong("r.role_id");
                Role role = new Role(roleName);
                role.setId(roleId);
                roles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user roles: " + e);
        }
        return roles;
    }
}
