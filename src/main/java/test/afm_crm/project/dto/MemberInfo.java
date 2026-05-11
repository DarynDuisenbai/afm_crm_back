package test.afm_crm.project.dto;

import lombok.Builder;
import lombok.Data;
import test.afm_crm.user.User;

@Data
@Builder
public class MemberInfo {

    private String id;
    private String username;
    private String color;

    public static MemberInfo from(User user) {
        return MemberInfo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .color(user.getColor())
                .build();
    }
}
