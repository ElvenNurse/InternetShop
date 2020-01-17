package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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

        User superUser = new User("superUser");
        superUser.setPassword("123");
        superUser.setFirstName("Super");
        superUser.setSecondName("User");
        superUser.addRoles(Collections.singleton(Role.of("USER")));
        superUser.addRoles(Collections.singleton(Role.of("ADMIN")));
        userService.create(superUser);

        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
