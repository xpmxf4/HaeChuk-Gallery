package HailYoungHan.Board.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String detail) { // ErrorCode 의 detail 에 여기서의 detail 이 들어감.
        super(String.format(errorCode.getDetail(), detail));
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
