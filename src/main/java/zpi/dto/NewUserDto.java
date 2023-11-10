package zpi.dto;

import zpi.entity.Role;

public record NewUserDto(String firstName, String lastName, String login, String password, Role role) {
}
