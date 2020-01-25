package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static BucketService bucketService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) throws DataProcessingException {
        return userDao.create(user);
    }

    @Override
    public User get(Long id) throws DataProcessingException {
        return userDao.get(id).orElseThrow(() ->
                new NoSuchElementException("Can't find user with id " + id));
    }

    @Override
    public User update(User user) throws DataProcessingException {
        return userDao.update(user);
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        User user = get(id);
        Bucket bucket = bucketService.getByUser(user);
        bucketService.delete(bucket);
        List<Order> userOrders = orderService.getUserOrders(user);
        for (Order order : userOrders) {
            orderService.delete(order);
        }
        return userDao.deleteById(id);
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        Bucket bucket = bucketService.getByUser(user);
        bucketService.delete(bucket);
        List<Order> userOrders = orderService.getUserOrders(user);
        for (Order order : userOrders) {
            orderService.delete(order);
        }
        return userDao.delete(user);
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        return userDao.getAll();
    }

    @Override
    public User login(String username, String password)
            throws AuthenticationException, DataProcessingException {
        Optional<User> user = userDao.findByUsername(username);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect username or password");
        }
        return user.get();
    }
}
