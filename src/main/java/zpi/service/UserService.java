package zpi.service;

import zpi.dto.NewUserDto;
import zpi.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    User getUser(final int id);

    User addUser(final NewUserDto user);

    Optional<User> getByLogin(final String login);

    User deleteUser(final int id);

    boolean userExists(final String login);

    long getUsersCount();

    void userCheck(NewUserDto user);

    void updateUser(final int id, final User user);

}
