package com.example.board.dto;

import com.example.board.constant.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {

    @Email(message = "이메일 형식이 아닙니다")
    @NotEmpty(message = "이메일은 필수요소입니다.")
    private String email;

    @NotEmpty(message = "이름은 필수요소입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수요소입니다.")
    private String password;

    private MemberRole memberRole;
}
