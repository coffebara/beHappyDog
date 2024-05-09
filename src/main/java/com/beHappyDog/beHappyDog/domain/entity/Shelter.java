package com.beHappyDog.beHappyDog.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Shelter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelter_id")
    private Long id;

    private String shelterName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getShelters().add(this);
    }

    private String address;
    private String detailAddress;

    @Builder(builderMethodName = "createShelter")
    public Shelter(String shelterName, String address, String detailAddress) {
        this.shelterName = shelterName;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}