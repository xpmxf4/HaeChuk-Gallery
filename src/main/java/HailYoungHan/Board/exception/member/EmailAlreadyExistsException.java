package HailYoungHan.Board.exception.member;

public class EmailAlreadyExistsException extends RuntimeException {
    /**
     * 이미 존재하는 이메일을 사용하여 상세 메시지와 함께 새로운 예외를 구성합니다.
     *
     * @param email
     */
    public EmailAlreadyExistsException(String email) {
        super("email already exists : " + email);
    }
}
