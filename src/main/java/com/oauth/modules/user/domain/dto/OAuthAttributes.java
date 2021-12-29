package com.oauth.modules.user.domain.dto;

import com.oauth.enum_package.ProviderType;
import com.oauth.enum_package.UserRoleType;
import com.oauth.modules.user.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }
        if("github".equals(registrationId)){
            return ofGithub("id",attributes);
        }
        if("google".equals(registrationId)){
            return ofGoogle(userNameAttributeName, attributes);
        }
        if("facebook".equals(registrationId)){
            return ofFacebook(userNameAttributeName, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofFacebook(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println(attributes);
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.FACEBOOK.getKey())
                .build();
    }

    private static OAuthAttributes ofGithub(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println(attributes);
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("login")+"@github.com")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.GITHUB.getKey())
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        System.out.println(response);
        System.out.println(profile);
        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .email((String)response.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.KAKAO.getKey())
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
        System.out.println(response);
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.NAVER.getKey())
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println(attributes);
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.GOOGLE.getKey())
                .build();
    }

    public UserEntity toEntity(){
        return UserEntity.builder()
                .nickname(name)
                .email(email)
                .role(UserRoleType.USER) // 기본 권한 GUEST
                .provider(provider)
                .build();
    }
}
