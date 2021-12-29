package com.oauth.modules.user.domain.dto.response;

import com.oauth.modules.user.domain.entity.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    private String nickname;
    private String email;

    public UserDTO(UserEntity user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
