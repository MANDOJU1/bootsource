package com.example.web1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {

    // @NotEmpty // or NotBlank
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)[A-Za-z\\d]{6,12}$", message = "아이디는 영대소문자, 숫자를 사용해서 6 ~12자리입니다")
    private String userid;

    // @NotEmpty => 패턴이 있기 때문에 중복으로 사용할 필요가 없음
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)(?=.+[!@$%])[A-Za-z\\d!@$%]{8,15}$", message = "비밀번호는 영대소문자, 숫자, 특수를 사용해서 8~15 자리입니다")
    private String password;

    @NotNull(message = "나이는 필수 요소입니다")
    @Min(value = 0)
    @Max(value = 20)
    private Integer age;

    @NotEmpty(message = "이메일은 필수 요소입니다")
    @Email
    private String email;

    @Length(min = 2, max = 6, message = "이름은 2~6자리로 작성해 주세요")
    private String name;

}
