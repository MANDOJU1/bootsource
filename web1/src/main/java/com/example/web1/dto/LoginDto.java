package com.example.web1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// @Data (Geteer, Setter, RequiredArgsConstructor ToString, EqualsAndHashCode,
// lombok.Value) => 하나만 써도 가능
public class LoginDto {

    @Email(message = "이메일을 확인해 주세요") // 이메일 검증
    @NotEmpty // @NotNull + "" 값 불가 (비어 있을 수 없습니다)
    private String email;

    @Length(min = 2, max = 6) // 길이가 2에서 6 사이어야 합니다
    // @NotBlank // @NotEmpty + "" 값 불가 => 위에서 길이를 걸었기 때문에 중복으로 사용하지 않아도 됨
    private String name;

}
