package com.beHappyDog.beHappyDog.filter;

import com.beHappyDog.beHappyDog.domain.entity.Member;
import com.beHappyDog.beHappyDog.domain.value.Role;
import com.beHappyDog.beHappyDog.provider.JwtProvider;
import com.beHappyDog.beHappyDog.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            // 토큰 검증
            String token = parseBearerToken(request);
            if (token == null) { //헤더 혹은 Bearer가 없는 경우
                filterChain.doFilter(request, response);
                return;
            }

            String email = jwtProvider.validate(token);
            if (email == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 유저 정보 가져오기
            Member findMember = memberRepository.findByEmail(email);
            Role role = findMember.getRole(); // role : ROLE_USER, ROLE_ADMIN

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.getValue()));

            // 유저 정보를 담을 SecurityContext 생성
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 생성한 SecurityContext를 SecurityContextHolder에 담기
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);



        } catch (Exception exception) {
            exception.printStackTrace();;
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        //헤더 유무 확인
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if(!hasAuthorization) {
            return null;
        }

        //Bearer 확인
        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) {
            return null;
        }

        String token = authorization.substring(7); // 'Bearer '다음 문자
        return token;
    }
}
