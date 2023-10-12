package HailYoungHan.Board.exception.comment;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException(Long id) {
        super("No such commentId : " + id);
    }
}
