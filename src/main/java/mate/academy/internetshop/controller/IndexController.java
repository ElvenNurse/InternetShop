package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class IndexController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = (Long) req.getSession().getAttribute("user_id");

        User user = userService.get(userId);
        req.setAttribute("username", user.getUsername());

        Integer isUser = verifyRole(user, Role.RoleName.USER) ? 1 : 0;
        Integer isAdmin = verifyRole(user, Role.RoleName.ADMIN) ? 1 : 0;
        req.setAttribute("is_user", isUser);
        req.setAttribute("is_admin", isAdmin);

        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles()
                .stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }
}
