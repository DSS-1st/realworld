package com.dss.realworld.common.error;

import com.dss.realworld.common.error.exception.AbstractBaseException;
import com.dss.realworld.common.error.exception.CustomValidationException;
import com.dss.realworld.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    public static void checkOrThrow(final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> messageList = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new CustomValidationException(messageList);
        }
    }

    @ExceptionHandler(value = CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final CustomValidationException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(
                                e.getStatus().value(),
                                e.getStatus().getReasonPhrase(),
                                e.getMessageList()
                        )
                );
    }

    @ExceptionHandler(value = AbstractBaseException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final AbstractBaseException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(
                                e.getStatus().value(),
                                e.getStatus().getReasonPhrase(),
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("HttpStatus: {} {}\nMessage: {}\n",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage(),
                e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                e.getMessage()
                        )
                );
    }
}