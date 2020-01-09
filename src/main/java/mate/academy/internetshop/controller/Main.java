package mate.academy.internetshop.controller;

import java.util.List;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private static BucketService bucketService;

    @Inject
    private static ItemService itemService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static UserService userService;

    public static void main(String[] args) {
        //Create items
        Item item1 = new Item("Item 1", 99D);
        Item item2 = new Item("Item 2", 199D);
        Item item3 = new Item("Item 3", 299D);
        Item item4 = new Item("Item 4", 399D);
        Item item5 = new Item("Item 5", 499D);
        Item item6 = new Item("Item 6", 599D);
        Item item7 = new Item("Item 7", 699D);
        itemService.create(item1);
        itemService.create(item2);
        itemService.create(item3);
        itemService.create(item4);
        itemService.create(item5);
        itemService.create(item6);
        itemService.create(item7);

        //Read items
        System.out.println("All items");
        for (Item item : itemService.getAll()) {
            System.out.println(item.getName());
        }
        System.out.println("\nItem by ID");
        System.out.println(itemService.get(item1.getId()).getName());

        //Update items
        System.out.println("\nUpdate item");
        item7.setName("Item 7 new");
        System.out.println(itemService.update(item7).getName());

        //Delete items
        System.out.println("\nDelete item");
        System.out.println(itemService.delete(item6));
        System.out.println(itemService.deleteById(item7.getId()));
        for (Item item : itemService.getAll()) {
            System.out.println(item.getName());
        }

        //Create users
        User user1 = new User("User 1");
        User user2 = new User("User 2");
        User user3 = new User("User 3");
        User user4 = new User("User 4");
        User user5 = new User("User 5");
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);
        userService.create(user5);

        //Read users
        System.out.println("\nAll users");
        for (User user : userService.getAll()) {
            System.out.println(user.getUsername());
        }
        System.out.println("\nUser by ID");
        System.out.println(userService.get(user1.getId()).getUsername());

        //Update users
        System.out.println("\nUpdate user");
        user5.setUsername("User 5 new");
        System.out.println(userService.update(user5).getUsername());

        //Delete users
        System.out.println("\nDelete user");
        System.out.println(userService.delete(user4));
        System.out.println(userService.deleteById(user5.getId()));
        for (User user : userService.getAll()) {
            System.out.println(user.getUsername());
        }

        //Create buckets
        Bucket bucket1 = new Bucket(user1);
        Bucket bucket2 = new Bucket(user2);
        Bucket bucket3 = new Bucket(user3);
        Bucket bucket4 = new Bucket(user2);
        Bucket bucket5 = new Bucket(user3);
        bucketService.create(bucket1);
        bucketService.create(bucket2);
        bucketService.create(bucket3);
        bucketService.create(bucket4);
        bucketService.create(bucket5);

        //Fill buckets
        bucketService.addItem(bucket1, item1);
        bucketService.addItem(bucket1, item2);
        bucketService.addItem(bucket1, item3);
        bucketService.addItem(bucket2, item4);
        bucketService.addItem(bucket2, item5);
        bucketService.addItem(bucket3, item1);
        bucketService.addItem(bucket3, item3);
        bucketService.addItem(bucket3, item5);
        bucketService.addItem(bucket4, item1);
        bucketService.addItem(bucket4, item2);
        bucketService.addItem(bucket5, item1);
        bucketService.addItem(bucket5, item2);

        //Read buckets
        System.out.println("\nAll buckets");
        for (Bucket bucket : bucketService.getAll()) {
            System.out.println("\nBucket ID " + bucket.getId());
            for (Item item : bucket.getItems()) {
                System.out.println(item.getName());
            }
        }
        System.out.println("\nBucket by ID");
        System.out.println(bucketService.get(bucket1.getId()).getId());

        //Update buckets
        System.out.println("\nUpdate bucket");
        bucketService.addItem(bucket5, item3);
        bucketService.addItem(bucket5, item4);
        System.out.println("Added items to bucket with ID " + bucket5.getId());
        for (Item item : bucketService.update(bucket5).getItems()) {
            System.out.println(item.getName());
        }
        bucketService.deleteItem(bucket5, item4);
        System.out.println("Deleted item from bucket with ID " + bucket5.getId());
        for (Item item : bucketService.update(bucket5).getItems()) {
            System.out.println(item.getName());
        }

        //Delete buckets
        System.out.println("\nDelete bucket");
        System.out.println(bucketService.delete(bucket4));
        System.out.println(bucketService.deleteById(bucket5.getId()));
        for (Bucket bucket : bucketService.getAll()) {
            System.out.println("Bucket ID " + bucket.getId());
        }

        //Create orders
        orderService.completeOrder(bucketService.getAllItems(bucket1), user1);
        bucketService.clear(bucket1);
        orderService.completeOrder(bucketService.getAllItems(bucket2), user2);
        bucketService.clear(bucket2);
        orderService.completeOrder(bucketService.getAllItems(bucket3), user3);
        bucketService.clear(bucket3);

        //Read orders
        System.out.println("\nFirst time read orders");
        printOrder(orderService.getAll());

        //Fill buckets
        bucketService.addItem(bucket1, item5);
        bucketService.addItem(bucket1, item4);
        bucketService.addItem(bucket2, item3);
        bucketService.addItem(bucket2, item2);
        bucketService.addItem(bucket2, item1);
        bucketService.addItem(bucket3, item2);
        bucketService.addItem(bucket3, item4);

        //Create new orders
        orderService.completeOrder(bucketService.getAllItems(bucket1), user1);
        bucketService.clear(bucket1);
        orderService.completeOrder(bucketService.getAllItems(bucket2), user2);
        bucketService.clear(bucket2);
        orderService.completeOrder(bucketService.getAllItems(bucket3), user3);
        bucketService.clear(bucket3);

        //Read all orders
        System.out.println("\nSecond time read orders");
        printOrder(orderService.getAll());
        System.out.println("\nGet order by ID");
        Order order1 = orderService.getUserOrders(user1).get(0);
        System.out.println("Order ID " + order1.getId());
        for (Item item : orderService.get(order1.getId()).getItems()) {
            System.out.println(item.getName());
        }

        //Update orders
        System.out.println("\nUpdate order");
        order1.getItems().remove(item1);
        orderService.update(order1);
        printOrder(orderService.getUserOrders(user1));

        //Delete orders
        System.out.println("\nDelete orders");
        Order order2 = orderService.getUserOrders(user2).get(0);
        Order order3 = orderService.getUserOrders(user3).get(0);
        System.out.println(orderService.delete(order2));
        System.out.println(orderService.deleteById(order3.getId()));
        System.out.println("\nThird time read orders");
        printOrder(orderService.getAll());
    }

    private static void printOrder(List<Order> orders) {
        for (Order order : orders) {
            System.out.println("\nOrder ID " + order.getId());
            System.out.println("User ID " + order.getUserId());
            for (Item item : order.getItems()) {
                System.out.println(item.getName() + " " + item.getPrice());
            }
        }
    }
}
