package HailYoungHan.Board.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(Long id) {
        super("No such memberId : " + id);    }
}
