package test.afm_crm.user.dto;

import lombok.Builder;
import lombok.Data;
import test.afm_crm.user.User;

@Data
@Builder
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String color;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .color(user.getColor())
                .build();
    }
}
