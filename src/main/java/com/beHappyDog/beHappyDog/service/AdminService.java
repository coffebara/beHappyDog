package com.beHappyDog.beHappyDog.service;

import com.beHappyDog.beHappyDog.domain.entity.Admin;
import com.beHappyDog.beHappyDog.domain.entity.Shelter;
import com.beHappyDog.beHappyDog.dto.ShelterRequestDto;
import com.beHappyDog.beHappyDog.repository.AdminRepository;
import com.beHappyDog.beHappyDog.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ShelterRepository shelterRepository;

    @Transactional
    public Long addShelter(ShelterRequestDto shelterRequestDto, String adminName) throws Exception{

        // 보호소 엔티티 생성
        Shelter shelter = shelterRequestDto.toShelterEntity();

        // authentication 객체로부터 받은 adminName을 등록하는 병원의 admin으로 설정하는 방식
        Admin admin = adminRepository.findByName(adminName).orElseThrow();
        shelter.setAdmin(admin);

        return shelter.getId();
    }

}
