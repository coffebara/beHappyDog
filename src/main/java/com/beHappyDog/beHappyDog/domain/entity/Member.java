package com.beHappyDog.beHappyDog.domain.entity;

import com.beHappyDog.beHappyDog.domain.value.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder(builderMethodName = "createMember")
    public Member(String email, String password, String name, String type, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
        this.role = role;
    }
}
