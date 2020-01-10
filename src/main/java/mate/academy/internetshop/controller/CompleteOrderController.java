package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class CompleteOrderController extends HttpServlet {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String bucketId = req.getParameter("bucket_id");
        Bucket bucket = bucketService.get(Long.valueOf(bucketId));
        User user = userService.get(bucket.getUserId());
        orderService.completeOrder(bucket.getItems(), user);
        bucketService.clear(bucket);

        resp.sendRedirect(req.getContextPath() + "/orders?user_id=" + bucket.getUserId());
    }
}
