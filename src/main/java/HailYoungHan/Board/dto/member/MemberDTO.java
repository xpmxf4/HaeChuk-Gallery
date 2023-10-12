package HailYoungHan.Board.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    private String name;
    private String password;

    @QueryProjection
    public MemberDTO(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
