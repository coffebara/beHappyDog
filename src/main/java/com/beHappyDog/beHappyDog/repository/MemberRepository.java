package com.beHappyDog.beHappyDog.repository;

import com.beHappyDog.beHappyDog.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    Optional<Member> findByEmailAndType(String email, String type);


    boolean existsByEmail(String email);


}
