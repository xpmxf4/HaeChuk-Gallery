package HailYoungHan.Board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
