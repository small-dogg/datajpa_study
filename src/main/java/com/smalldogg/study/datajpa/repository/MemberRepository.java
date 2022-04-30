package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
