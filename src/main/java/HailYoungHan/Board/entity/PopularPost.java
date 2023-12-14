package HailYoungHan.Board.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularPost extends SysTimeCols{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popular_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50)
    private String title;
}
