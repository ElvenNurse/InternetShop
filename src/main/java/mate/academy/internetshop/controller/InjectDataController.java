package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InjectDataController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(InjectDataController.class);

    @Inject
    private static ItemService itemService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {

            User user = new User("user");
            user.setPassword("123");
            user.setFirstName("Vasya");
            user.setSecondName("Pupkin");
            user.addRoles(Collections.singleton(Role.of("USER")));
            userService.create(user);

            User admin = new User("admin");
            admin.setPassword("123");
            admin.setFirstName("Petya");
            admin.setSecondName("Pyatochkin");
            admin.addRoles(Collections.singleton(Role.of("ADMIN")));
            userService.create(admin);

            User superUser = new User("superuser");
            superUser.setPassword("123");
            superUser.setFirstName("Super");
            superUser.setSecondName("User");
            superUser.addRoles(Collections.singleton(Role.of("USER")));
            superUser.addRoles(Collections.singleton(Role.of("ADMIN")));
            userService.create(superUser);

            Item itemS10 = new Item("Galaxy S10", 999.99);
            itemService.create(itemS10);
            Item itemS11 = new Item("Galaxy S11", 1199.99);
            itemService.create(itemS11);
            Item itemS12 = new Item("Galaxy S12", 1499.99);
            itemService.create(itemS12);
            Item itemI7 = new Item("iPhone 7", 800D);
            itemService.create(itemI7);
            Item itemI8 = new Item("iPhone 8", 1000D);
            itemService.create(itemI8);
            Item itemI10 = new Item("iPhone X", 1400D);
            itemService.create(itemI10);

            List<Item> itemSetFirst = new ArrayList<>();
            itemSetFirst.add(itemS10);
            itemSetFirst.add(itemI7);

            List<Item> itemSetSecond = new ArrayList<>();
            itemSetSecond.add(itemS11);
            itemSetSecond.add(itemS12);

            List<Item> itemSetThird = new ArrayList<>();
            itemSetThird.add(itemI8);
            itemSetThird.add(itemI10);

            orderService.completeOrder(itemSetFirst, user);
            orderService.completeOrder(itemSetSecond, user);
            orderService.completeOrder(itemSetThird, user);
            orderService.completeOrder(itemSetFirst, admin);
            orderService.completeOrder(itemSetSecond, superUser);
            orderService.completeOrder(itemSetThird, superUser);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("dpe_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("/WEB-INF/views/dataInjection.jsp").forward(req, resp);
    }
}
