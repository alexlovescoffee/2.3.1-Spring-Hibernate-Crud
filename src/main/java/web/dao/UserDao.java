package web.dao;

import web.model.Address;
import web.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    User getUser(long id);
    List<User> getAllUsers();

    void mergeAddress(User user);
}
