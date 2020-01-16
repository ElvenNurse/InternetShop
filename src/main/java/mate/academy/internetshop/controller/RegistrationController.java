package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int isLogged = 0;
        if (req.getSession().getAttribute("user_id") != null) {
            isLogged = 1;
        }
        req.setAttribute("is_logged", Integer.valueOf(isLogged));

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
        newUser.addRole(Role.of("USER"));
        User user = userService.create(newUser);
        Cookie cookie = new Cookie("MATE", user.getToken());
        resp.addCookie(cookie);

        HttpSession session = req.getSession(true);
        session.setAttribute("user_id", user.getId());

        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
