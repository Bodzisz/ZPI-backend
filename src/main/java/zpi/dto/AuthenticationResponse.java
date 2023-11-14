package zpi.dto;

public record AuthenticationResponse(String token, String username, String firstName, String lastName, String role) {
}
