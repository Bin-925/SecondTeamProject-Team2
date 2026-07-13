package com.back.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

// 로그인은 비밀번호 "형식"을 검증하는 자리가 아니라 실제 일치 여부만 확인하면 되므로
// 길이 제약(@Size)은 두지 않는다. 8자 미만으로 입력해도 AuthService에서
// 동일하게 "아이디 또는 비밀번호가 일치하지 않습니다."로 응답한다.
public record LoginRequest (
    @NotBlank String id,
    @NotBlank String password
) {}
