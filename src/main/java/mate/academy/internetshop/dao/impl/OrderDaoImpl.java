package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.IdGenerator;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        order.setId(IdGenerator.getOrderId());
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders
                .stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        Order old = get(order.getId()).orElseThrow(() ->
                        new NoSuchElementException("Can't update order with id "
                                + order.getId()));
        int index = Storage.orders.indexOf(old);
        return Storage.orders.set(index, order);
    }

    @Override
    public boolean deleteById(Long id) {
        Order old = get(id).orElseThrow(() ->
                        new NoSuchElementException("Can't delete order with id " + id));
        return delete(old);
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }
}
