package HailYoungHan.Board.repository;


import HailYoungHan.Board.dto.CommentDTO;

import java.util.List;

public interface CommentCustomRepository {

    public void updateCommentDTO(Long commentId, CommentDTO dto);

    public CommentDTO findDTOById(Long commentId);

    public List<CommentDTO> findAllDTOs();

    public List<CommentDTO> findAllDTOsByMemberId(Long memberId);

    public List<CommentDTO> findAllDTOsBYMemberIdAndIsDeleted(Long memberId, Boolean isDeleted);
}
