package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.IdGenerator;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        user.setId(IdGenerator.getUserId());
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        User old = get(user.getId()).orElseThrow(() ->
                        new DataProcessingException("Can't update user with id "
                                + user.getId()));
        int index = Storage.users.indexOf(old);
        return Storage.users.set(index, user);
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        User old = get(id).orElseThrow(() ->
                        new DataProcessingException("Can't delete user with id " + id));
        return delete(old);
    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return getAll()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }
}
