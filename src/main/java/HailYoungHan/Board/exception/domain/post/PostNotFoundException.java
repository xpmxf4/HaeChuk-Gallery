package HailYoungHan.Board.exception.domain.post;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(Long postId) {
        super("No such postId : " + postId);
    }
}
