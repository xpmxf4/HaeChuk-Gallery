package HailYoungHan.Board.exception.domain.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 멤버가 발견되지 않았을 때 발생하는 예외.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "해당 멤버가 존재하지 않습니다.")
public class MemberNotFoundException extends RuntimeException {

    /**
     * 멤버의 ID를 사용하여 상세 메시지와 함께 새로운 예외를 구성합니다.
     * @param id 발견되지 않은 멤버의 ID
     */
    public MemberNotFoundException(Long id) {
        super("해당 멤버 ID가 없습니다: " + id);
    }

    /**
     * 멤버의 name을 사용하여 상세 메시지와 함께 새로운 예외를 구성합니다.
     * @param name 발견되지 않은 멤버의 이름
     */
    public MemberNotFoundException(String name) {
        super("해당 멤버 이름이 없습니다: " + name);
    }
}

