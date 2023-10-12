package HailYoungHan.Board.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class MemberUpdateDTO {

    private Long id;
    private String name;
    private String password;

    public Long getId() {
        return id;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
}
