package com.beHappyDog.beHappyDog.service;

import com.beHappyDog.beHappyDog.domain.entity.CustomOAuth2User;
import com.beHappyDog.beHappyDog.domain.entity.Member;
import com.beHappyDog.beHappyDog.dto.SignUpResponseDto;
import com.beHappyDog.beHappyDog.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

//        try {
//            log.info(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }

        Member member = null;
        String email = null;
        String name = null;
        String provider = oauthClientName;



        if(provider.equals("kakao")) {
            log.info("===============kakao==============");
            log.info(String.valueOf(oAuth2User.getAttributes().get("properties")));
            Map<String, String> propertiesMap = (Map<String, String>) oAuth2User.getAttributes().get("properties");
            Map<String, String> accountMap = (Map<String, String>) oAuth2User.getAttributes().get("kakao_account");

            email = ((String)accountMap.get("email"));
            name = ((String)propertiesMap.get("nickname"));
            member = Member.createOauthMember()
                    .email(email)
                    .name(name)
                    .type("kakao")
                    .build();
        }

        if (provider.equals("naver")) {
            log.info("===============naver==============");
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            email = responseMap.get("email");
            name = responseMap.get("name");
            member = Member.createOauthMember()
                    .email(email)
                    .name(name)
                    .type("naver")
                    .build();
        }

        memberRepository.save(member);

        return new CustomOAuth2User(email);
    }

}
