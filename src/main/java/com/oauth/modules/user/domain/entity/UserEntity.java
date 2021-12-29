package com.oauth.modules.user.domain.entity;

import com.oauth.enum_package.ProviderType;
import com.oauth.enum_package.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String nickname;
    private String email;

    private String oauthId;
    private String provider;

    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder
    public UserEntity(String nickname, String email, String picture, UserRoleType role,String provider){
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.provider = provider;
    }

    public UserEntity update(String nickname){
        this.nickname = nickname;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
