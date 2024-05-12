package com.beHappyDog.beHappyDog.service;

import com.beHappyDog.beHappyDog.common.CertificationNumber;
import com.beHappyDog.beHappyDog.domain.entity.Certification;
import com.beHappyDog.beHappyDog.dto.*;
import com.beHappyDog.beHappyDog.provider.EmailProvider;
import com.beHappyDog.beHappyDog.repository.CertificationRepository;
import com.beHappyDog.beHappyDog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CertificationRepository certificationRepository;
    private final EmailProvider emailProvider;

    // 이메일 검증
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

    // 인증메일 발송
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {

        try {

            String email = dto.getEmail();

            // 이메일 검증
            boolean isExistEmail = memberRepository.existsByEmail(email);
            if (isExistEmail) {
                return EmailCertificationResponseDto.duplicateEmail();
            }

            // 인증번호 생성
            String certificationNumber = CertificationNumber.getCertificationNumber();

            // 메일 전송
            boolean isSucceed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSucceed) {
                return EmailCertificationResponseDto.mailSendFail();
            }

            // 전송 결과 저장
            Certification certification = new Certification(email, certificationNumber);
            certificationRepository.save(certification);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }


}
