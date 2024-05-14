package com.beHappyDog.beHappyDog.service;

import com.beHappyDog.beHappyDog.common.CertificationNumber;
import com.beHappyDog.beHappyDog.domain.entity.Certification;
import com.beHappyDog.beHappyDog.domain.entity.Member;
import com.beHappyDog.beHappyDog.domain.value.Role;
import com.beHappyDog.beHappyDog.dto.*;
import com.beHappyDog.beHappyDog.provider.EmailProvider;
import com.beHappyDog.beHappyDog.provider.JwtProvider;
import com.beHappyDog.beHappyDog.repository.CertificationRepository;
import com.beHappyDog.beHappyDog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CertificationRepository certificationRepository;
    private final EmailProvider emailProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

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

    // 인증메일 검증
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {

        try {
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            // 인증메일 전송여부 확인
            Certification certification = certificationRepository.findByEmail(email);
            if (certification == null) {
                return CheckCertificationResponseDto.certificationFail();
            }

            // 인증메일을 발송한 이메일과 인증넘버 검증
            boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) {
                return CheckCertificationResponseDto.certificationFail();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return CheckCertificationResponseDto.certificationFail();
        }

        return CheckCertificationResponseDto.success();
    }

    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        try {

            // 이메일 중복 검증
            String email = dto.getEmail();
            boolean isExistEmail = memberRepository.existsByEmail(email);
            if (isExistEmail) {
                return SignUpResponseDto.duplicateEmail();
            }

            // 인증메일 검증
            String certificationNumber = dto.getCertificationNumber();
            Certification certification = certificationRepository.findByEmail(email);
            boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) {
                return SignUpResponseDto.certificationFail();
            }

            String password = dto.getPassword();
            String name = dto.getName();

            Member member = Member.createMember()
                    .email(email)
                    .name(name)
                    .password(bCryptPasswordEncoder.encode(password))
                    .type("app")
                    .role(Role.ROLE_USER)
                    .build();
            memberRepository.save(member);
            // 가입 후 인증내역 삭제
            certificationRepository.deleteByEmail(email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return SignUpResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String token = null;

        try {

            // 이메일 확인
            String email = dto.getEmail();
            Member member = memberRepository.findByEmail(email);
            if (member == null) {
                return SignInResponseDto.signUpFail();
            }

            // 비밀번호 확인
            String password = dto.getPassword();
            String encodedPassword = member.getPassword();
            boolean isMatched = bCryptPasswordEncoder.matches(password, encodedPassword);
            if (!isMatched) {
                return SignInResponseDto.signUpFail();
            }

            // 토큰 생성
            token = jwtProvider.create(email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }


        return SignInResponseDto.success(token);
    }

}
