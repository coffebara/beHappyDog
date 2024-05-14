package com.beHappyDog.beHappyDog.repository;

import com.beHappyDog.beHappyDog.domain.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    Certification findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
