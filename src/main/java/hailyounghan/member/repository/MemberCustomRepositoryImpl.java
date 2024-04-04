package hailyounghan.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hailyounghan.member.dto.query.MemberDbDTO;
import hailyounghan.member.dto.query.QMemberDbDTO;

import javax.persistence.EntityManager;

import java.util.List;

import static hailyounghan.member.entity.QMember.member;


public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MemberCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberDbDTO getSingleMember(Long id) {
        return queryFactory
                .select(new QMemberDbDTO(
                        member.id,
                        member.name,
                        member.email
                ))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<MemberDbDTO> getAllMembers() {
        return queryFactory
                .select(new QMemberDbDTO(
                        member.id,
                        member.name,
                        member.email
                ))
                .from(member)
                .fetch();
    }

    @Override
    public MemberDbDTO getMemberByEmail(String email) {
        return queryFactory
                .select(
                        new QMemberDbDTO(
                                member.name,
                                member.email
                        )
                )
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }
}
