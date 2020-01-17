package mate.academy.internetshop.service;

import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface UserService extends GenericService<User, Long> {

    User login(String username, String password) throws AuthenticationException;
}
