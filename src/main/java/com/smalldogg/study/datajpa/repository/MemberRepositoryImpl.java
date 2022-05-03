package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

//클래스명 작성 규칙을 주의해야함. 잘못하면 Bean 생성을 못할 수 있음
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
