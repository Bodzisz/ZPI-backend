package zpi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    public static Optional<Role> getRoleByName(String name) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getRoleName().equalsIgnoreCase(name))
                .findFirst();
    }
}
