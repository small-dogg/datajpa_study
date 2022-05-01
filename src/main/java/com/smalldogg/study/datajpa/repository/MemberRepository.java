package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByUsername(String username);

}
