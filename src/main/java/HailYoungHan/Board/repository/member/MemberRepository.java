package HailYoungHan.Board.repository.member;

import HailYoungHan.Board.entity.Member;
//import io.hypersistence.utils.spring.repository.HibernateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>,
//        HibernateRepository<Member>,
        MemberCustomRepository {

    boolean existsByEmail(String email);


}
