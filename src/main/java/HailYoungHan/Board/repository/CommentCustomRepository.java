package HailYoungHan.Board.repository;


import HailYoungHan.Board.dto.CommentDTO;

public interface CommentCustomRepository {

    public void updateCommentDTO(Long commentId, CommentDTO dto);
}
