package HailYoungHan.Board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Comment extends SysCols {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    //===연관관계 메서드===//
    public void addChild(Comment comment) {
        children.add(comment);
        comment.setParent(this);
    }
}
