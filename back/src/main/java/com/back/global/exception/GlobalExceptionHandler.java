package com.back.global.exception;

import com.back.global.rsData.RsData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(com.back.global.exception.ServiceException.class)
    public RsData<Void> handle(ServiceException ex, HttpServletResponse response) {
        RsData<Void> rsData = ex.getRsData();

        response.setStatus(rsData.statusCode());

        return rsData;
    }

    // @Valid로 걸리는 DTO 필드 검증(@NotBlank, @Pattern 등) 실패 시 발생.
    // 처리하지 않으면 Spring Boot 기본 에러 응답(msg 없음)이 내려가서
    // 프론트에서 "요청 처리 중 문제가 발생했습니다." 같은 기본 문구만 보이게 된다.
    // 필드에 정의된 메시지(예: "이름에 공백을 포함할 수 없습니다.")를 그대로 내려준다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RsData<Void> handle(MethodArgumentNotValidException ex, HttpServletResponse response) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse(ErrorCode.BAD_REQUEST.getMessage());

        RsData<Void> rsData = new RsData<>(ErrorCode.BAD_REQUEST.getResultCode(), message);

        response.setStatus(rsData.statusCode());

        return rsData;
    }
}
