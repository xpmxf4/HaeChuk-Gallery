package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.member.MemberDTO;
import HailYoungHan.Board.dto.member.QMemberDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static HailYoungHan.Board.entity.QMember.member;

public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MemberCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberDTO getSingleMember(Long id) {
        return queryFactory
                .select(new QMemberDTO(
                        member.id,
                        member.name,
                        member.password
                ))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return queryFactory
                .select(new QMemberDTO(
                        member.id,
                        member.name,
                        member.password
                ))
                .from(member)
                .fetch();
    }
}
