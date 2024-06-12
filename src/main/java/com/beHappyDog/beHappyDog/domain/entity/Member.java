package com.beHappyDog.beHappyDog.domain.entity;

import com.beHappyDog.beHappyDog.domain.value.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password = "passw0rd";

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder(builderMethodName = "createMember", builderClassName = "DefaultMemberBuilder")
    public Member(String email, String password, String name, String type, Role role) {
        log.info("callByCreateMember");
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
        this.role = role;
    }

    @Builder(builderMethodName = "createOauthMember", builderClassName = "OauthMemberBuilder")
    public Member(String email, String name, String type) {
        log.info("callByCreateOauthMember");
        this.email = email;
        this.password = "passw0rd";
        this.name = name;
        this.type = type;
        this.role = Role.ROLE_USER;
    }
}
