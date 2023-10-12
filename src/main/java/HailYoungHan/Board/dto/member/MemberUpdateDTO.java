package HailYoungHan.Board.dto.member;

import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class MemberUpdateDTO {

    private Long id;
    private String name;
    private String password;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public Long getId() {
        return id;
    }
}
