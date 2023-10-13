package HailYoungHan.Board.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 이미 존재하는 이름으로 멤버 등록 시도 시 발생하는 예외.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "해당 이름은 이미 존재하는 멤버입니다.")
public class NameAlreadyExistsException extends RuntimeException {

    /**
     * 이미 존재하는 이름을 사용하여 상세 메시지와 함께 새로운 예외를 구성합니다.
     *
     * @param name 이미 존재하는 멤버의 이름
     */
    public NameAlreadyExistsException(String name) {
        super("name already exists : " + name);
    }

}
