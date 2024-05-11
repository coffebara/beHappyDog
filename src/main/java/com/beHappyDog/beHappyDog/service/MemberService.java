package com.beHappyDog.beHappyDog.service;

import com.beHappyDog.beHappyDog.dto.EmailCheckRequestDto;
import com.beHappyDog.beHappyDog.dto.EmailCheckResponseDto;
import com.beHappyDog.beHappyDog.dto.ResponseDto;
import com.beHappyDog.beHappyDog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto) {

        try {

            String email = dto.getEmail();
            boolean isExistEmail = memberRepository.existsByEmail(email);
            if(isExistEmail) {
                return EmailCheckResponseDto.duplicateEmail();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCheckResponseDto.success();
    }
}
