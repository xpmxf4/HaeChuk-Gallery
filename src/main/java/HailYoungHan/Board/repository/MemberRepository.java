package HailYoungHan.Board.repository;

import HailYoungHan.Board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> , MemberCustomRepository{

    List<Member> findByName(String name);
    boolean existsByName(String name);
}
