package com.example.movie.dto;

import com.example.movie.constant.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long mid;

    private String email;
    private String password;
    private String nickname;

    private MemberRole role;
}
