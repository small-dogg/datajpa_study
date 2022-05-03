package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
