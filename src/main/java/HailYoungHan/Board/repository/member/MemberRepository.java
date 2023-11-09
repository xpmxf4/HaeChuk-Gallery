package HailYoungHan.Board.repository.member;

import HailYoungHan.Board.entity.Member;
//import io.hypersistence.utils.spring.repository.HibernateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>,
//        HibernateRepository<Member>,
        MemberCustomRepository {

    boolean existsByEmail(String email);

    @Modifying
    @Query("delete Member m where m.id in :ids")
    void deleteMembers(List<Long> ids);

    @Query("select count(m) from Member m where m.id in :ids")
    long countByIds(List<Long> ids);
}
