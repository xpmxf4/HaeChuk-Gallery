package HailYoungHan.Board.repository;

import HailYoungHan.Board.entity.Comment;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;

public interface CommentCustomRepository {

    public void insertCommentDTO(String content, Member author, Post commentedPost, Comment parentComment);
}
