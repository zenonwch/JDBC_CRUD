package dao;

import model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    boolean updateUser(User user);

    boolean removeUser(int userId);

    User getUserById(int userId);

    List<User> getAllUsers();
}
