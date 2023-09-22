package HailYoungHan.Board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Post extends SysCols {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String name;
    private String content;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
