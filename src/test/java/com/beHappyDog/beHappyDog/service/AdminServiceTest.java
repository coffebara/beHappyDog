package com.beHappyDog.beHappyDog.service;

import com.beHappyDog.beHappyDog.domain.entity.Admin;
import com.beHappyDog.beHappyDog.domain.entity.Shelter;
import com.beHappyDog.beHappyDog.repository.AdminRepository;
import com.beHappyDog.beHappyDog.repository.ShelterRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired EntityManager em;
    @Autowired AdminRepository adminRepository;
    @Autowired
    ShelterRepository shelterRepository;

    @Test
    public void createAdmin() throws Exception {
        //given
        Admin admin = Admin.createAdmin()
                .name("admin")
                .build();
        Admin savedAdmin = adminRepository.save(admin);

        Shelter shelter = Shelter.createShelter()
                .shelterName("보호소")
                .address("인천시")
                .detailAddress("부평구")
                .build();
        Shelter savedShelter = shelterRepository.save(shelter);

        //when
        savedShelter.setAdmin(savedAdmin);

        //then
        assertEquals(admin.getId(), savedAdmin.getId());
        assertEquals(savedAdmin.getId(), shelter.getAdmin().getId());

    }
}