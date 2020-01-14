package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElseThrow(() ->
                new NoSuchElementException("Can't find user with id " + id));
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public boolean delete(User user) {
        return userDao.delete(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User login(String username, String password)
            throws AuthenticationException {
        Optional<User> user = userDao.findByUsername(username);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect username or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.findByToken(token);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }
}
