package HailYoungHan.Board.exception.handler;

import HailYoungHan.Board.exception.CustomException;
import HailYoungHan.Board.exception.ErrorCode;
import HailYoungHan.Board.exception.ErrorResponse;
import HailYoungHan.Board.exception.domain.comment.CommentNotFoundException;
import HailYoungHan.Board.exception.domain.post.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    protected ResponseEntity<ErrorResponse> catchException(RuntimeException e) {
        log.error("예외 핸들링", e);
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(e.getMessage(), e.toString()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("커스텀 에러 핸들링", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.fromErrorCodeToResponse(errorCode));
    }
}
