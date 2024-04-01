package com.example.web1.dto;

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
public class MemberDto {
    private String email;
    private String name;

}
