package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class AddUserController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User newUser = new User(req.getParameter("username"));
        newUser.setPassword(req.getParameter("psw"));
        newUser.setFirstName(req.getParameter("firstName"));
        newUser.setSecondName(req.getParameter("secondName"));
        userService.create(newUser);

        resp.sendRedirect(req.getContextPath() + "/servlet/getAllUsers");
    }
}
