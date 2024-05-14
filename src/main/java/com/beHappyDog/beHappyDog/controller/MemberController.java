package com.beHappyDog.beHappyDog.controller;

import com.beHappyDog.beHappyDog.dto.*;
import com.beHappyDog.beHappyDog.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 이메일 중복 확인 API
     *
     * @param requestBody 이메일 중복 확인 요청 DTO
     * @return 이메일 중복 확인 응답 DTO
     */
    @PostMapping("/email-check")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(@RequestBody @Valid EmailCheckRequestDto requestBody) {
        ResponseEntity<? super EmailCheckResponseDto> response = memberService.emailCheck(requestBody);
        return response;
    }

    /**
     * 이메일 인증코드 전송 API
     *
     * @param requestBody 이메일 인증 코드 전송 요청 DTO
     * @return 이메일 인증 코드 전송 응답 DTO
     */
    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(@RequestBody @Valid EmailCertificationRequestDto requestBody) {
        ResponseEntity<? super EmailCertificationResponseDto> response = memberService.emailCertification(requestBody);
        return response;
    }

    /**
     * 이메일 인증 코드 검증 API
     *
     * @param requestBody 이메일 인증 코드 검증 요청 DTO
     * @return 이메일 인증 코드 검증 응답 DTO
     */
    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(@RequestBody @Valid CheckCertificationRequestDto requestBody) {
        ResponseEntity<? super CheckCertificationResponseDto> response = memberService.checkCertification(requestBody);
        return response;
    }


    /**
     * 회원 가입 API
     *
     * @param requestBody 회원 가입 요청 DTO
     * @return 회원 가입 응답 DTO
     */
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        ResponseEntity<? super SignUpResponseDto> response = memberService.signUp(requestBody);
        return response;
    }


    /**
     * 로그인 API
     *
     * @param requestBody 로그인 요청 DTO
     * @return 로그인 응답 DTO
     */
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        ResponseEntity<? super SignInResponseDto> response = memberService.signIn(requestBody);
        return response;
    }

}
