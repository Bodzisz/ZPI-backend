package zpi.service;

import zpi.dto.NewUserDto;
import zpi.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    
    User getUser(final int id);
    
    User addUser(final NewUserDto user);

    User getByLogin(final String login);
    
    User deleteUser(final int id);
    
    void updateUser(final int id, final User user);

    boolean userExists(final String login);

    long getUsersCount();
}
