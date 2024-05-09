package com.beHappyDog.beHappyDog.dto;

import com.beHappyDog.beHappyDog.domain.entity.Shelter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelterRequestDto {
    private String shelterName;
    private String address;
    private String detailAddress;

    public Shelter toShelterEntity(){
        return Shelter.createShelter()
                .shelterName(shelterName)
                .address(address)
                .detailAddress(detailAddress)
                .build();
    }


}
