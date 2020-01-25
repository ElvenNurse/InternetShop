package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddUserController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AddUserController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User newUser = new User(req.getParameter("username"));
        newUser.setPassword(req.getParameter("psw"));
        newUser.setFirstName(req.getParameter("firstName"));
        newUser.setSecondName(req.getParameter("secondName"));
        newUser.addRoles(Collections.singleton(Role.of("USER")));

        try {
            userService.create(newUser);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servlet/getAllUsers");
    }
}
