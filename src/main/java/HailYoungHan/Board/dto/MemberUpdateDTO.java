package HailYoungHan.Board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class MemberUpdateDTO {

    private Long id;
    private String name;
    private String password;

}
