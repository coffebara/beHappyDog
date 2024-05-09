package com.beHappyDog.beHappyDog.repository;

import com.beHappyDog.beHappyDog.domain.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

}
