package HailYoungHan.Board.exception.member;

public class NameAlreadyExistsException extends RuntimeException {

    public NameAlreadyExistsException(String message) {
        super("name already exists : " + message);
    }

}
