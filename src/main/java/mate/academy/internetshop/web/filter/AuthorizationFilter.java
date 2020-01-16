package mate.academy.internetshop.web.filter;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class AuthorizationFilter implements Filter {
    private static final String EMPTY_STRING = "";

    @Inject
    private static UserService userService;

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/getAllUsers", ADMIN);
        protectedUrls.put("/servlet/addUser", ADMIN);
        protectedUrls.put("/servlet/deleteUser", ADMIN);
        protectedUrls.put("/servlet/getAllItems", ADMIN);
        protectedUrls.put("/servlet/addItem", ADMIN);
        protectedUrls.put("/servlet/deleteItem", ADMIN);
        protectedUrls.put("/servlet/getAllOrders", ADMIN);
        protectedUrls.put("/servlet/deleteOrder", ADMIN);
        protectedUrls.put("/shop", USER);
        protectedUrls.put("/bucket", USER);
        protectedUrls.put("/servlet/addToBucket", USER);
        protectedUrls.put("/servlet/deleteFromBucket", USER);
        protectedUrls.put("/servlet/completeOrder", USER);
        protectedUrls.put("/orders", USER);
        protectedUrls.put("/servlet/deleteUserOrder", USER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            processUnAuthenticated(req, resp);
            return;
        }

        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthenticated(req, resp, chain);
            return;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("MATE")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            processUnAuthenticated(req, resp);
        } else {
            Optional<User> user = userService.getByToken(token);
            if (user.isPresent()) {
                if (verifyRole(user.get(), roleName)) {
                    processAuthenticated(req, resp, chain);
                } else {
                    processDenied(req, resp);
                }
            } else {
                processUnAuthenticated(req, resp);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles()
                .stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    private void processAuthenticated(HttpServletRequest req, HttpServletResponse resp,
                                      FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(req, resp);
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }
}
