package com.example.board.dto;

import com.example.board.constant.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
    private String email;
    private String name;
    private String password;
    private MemberRole memberRole;
}
