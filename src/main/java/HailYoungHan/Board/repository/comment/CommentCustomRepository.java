package HailYoungHan.Board.repository.comment;


import HailYoungHan.Board.dto.comment.query.CommentDbDTO;

import java.util.List;

public interface CommentCustomRepository {


    public CommentDbDTO findDTOById(Long commentId);

    public List<CommentDbDTO> findAllDTOs();

    public List<CommentDbDTO> findAllDTOsByMemberId(Long memberId, boolean isDeleted);

    public List<CommentDbDTO> findAllDTOsByMemberIdAndIsDeleted(Long memberId);
}
