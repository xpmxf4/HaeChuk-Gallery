package HailYoungHan.Board.repository.comment;


import HailYoungHan.Board.dto.comment.CommentDTO;

import java.util.List;

public interface CommentCustomRepository {

    public void updateCommentDTO(Long commentId, CommentDTO dto);

    public CommentDTO findDTOById(Long commentId);

    public List<CommentDTO> findAllDTOs();

    public List<CommentDTO> findAllDTOsByMemberId(Long memberId, boolean isDeleted);

    public List<CommentDTO> findAllDTOsByMemberIdAndIsDeleted(Long memberId);
}
