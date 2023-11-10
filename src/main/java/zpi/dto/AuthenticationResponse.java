package zpi.dto;

import zpi.entity.Role;

public record AuthenticationResponse(String token, String username, String firstName, String lastName, Role role) {
}
