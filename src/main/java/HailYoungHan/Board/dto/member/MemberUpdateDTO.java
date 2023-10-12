package HailYoungHan.Board.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateDTO {

    private Long id;
    private String name;
    private String password;

}
