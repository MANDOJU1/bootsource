package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.club.constant.ClubMemberRole;
import com.example.club.dto.ClubAuthMemberDto;
import com.example.club.entity.ClubMember;
import com.example.club.reposirtoy.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ClubOAuth2UserDetailService extends DefaultOAuth2UserService {

    private final ClubMemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("========================");
        log.info("userRequest {} ", userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName", clientName);
        log.info(userRequest.getAdditionalParameters());
        log.info("========================");

        // 필요한 값 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info("{} : {} ", k, v);
        });

        ClubMember clubMember = saveSocialMember(oAuth2User.getAttribute("email"));

        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(), clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()),
                oAuth2User.getAttributes());
        clubAuthMemberDto.setName(clubMember.getName());

        return clubAuthMemberDto;
    }

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> result = memberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            return result.get();

        }

        // 소셜 로그인해도 회원가입되도록 만들기 (DB에 값이 저장됨)
        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111")) // 임의 지정 (소셜에서 로그인하기때문에 사이트에서 임의로 지정해줌)
                .fromSocial(true)
                .build();
        clubMember.addMemberRole(ClubMemberRole.USER);
        memberRepository.save(clubMember);
        return clubMember;
    }
}
