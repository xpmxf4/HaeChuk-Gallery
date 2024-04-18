package hailyounghan.comment.repository;


import hailyounghan.comment.dto.query.CommentDbDTO;

import java.util.List;

public interface CommentCustomRepository {


    CommentDbDTO findDTOById(Long commentId);

    List<CommentDbDTO> findAllDTOs();

    List<CommentDbDTO> findAllDTOsByMemberId(Long memberId);

    List<CommentDbDTO> findAllDeletedDTOsByMemberId(Long memberId, boolean isDeleted);

}
