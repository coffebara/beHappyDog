package com.beHappyDog.beHappyDog.controller;

import com.beHappyDog.beHappyDog.dto.EmailCertificationRequestDto;
import com.beHappyDog.beHappyDog.dto.EmailCertificationResponseDto;
import com.beHappyDog.beHappyDog.dto.EmailCheckRequestDto;
import com.beHappyDog.beHappyDog.dto.EmailCheckResponseDto;
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

    @PostMapping("/email-check")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(@RequestBody @Valid EmailCheckRequestDto requestBody) {
        ResponseEntity<? super EmailCheckResponseDto> response = memberService.emailCheck(requestBody);
        return response;
    }

    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(@RequestBody @Valid EmailCertificationRequestDto requestBody) {
        ResponseEntity<? super EmailCertificationResponseDto> response = memberService.emailCertification(requestBody);
        return response;
    }
}
