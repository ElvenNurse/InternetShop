package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(RegistrationController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer isLogged = req.getSession().getAttribute("user_id") == null ? 0 : 1;
        req.setAttribute("is_logged", isLogged);

        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User newUser = new User(req.getParameter("username"));
        if (req.getParameter("psw").equals(req.getParameter("psw-repeat"))) {
            newUser.setPassword(req.getParameter("psw"));
        } else {
            req.setAttribute("errorMsg", "Passwords are not matching");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
        newUser.setFirstName(req.getParameter("firstName"));
        newUser.setSecondName(req.getParameter("secondName"));
        newUser.addRoles(Collections.singleton(Role.of("USER")));

        try {
            User user = userService.create(newUser);

            HttpSession session = req.getSession(true);
            session.setAttribute("user_id", user.getId());
        } catch (DataProcessingException e) {
            logger.error(e);
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
