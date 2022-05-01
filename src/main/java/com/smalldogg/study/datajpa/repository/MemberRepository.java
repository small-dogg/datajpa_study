package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.dto.MemberDto;
import com.smalldogg.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
//메서드의 조건이 2개이상들어가면 다른방법으로 해결
    List<Member> findAllByUsername(String username);

    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);

    //실무에서 사용 X
    //우선순위가 Named쿼리에 먼저있고, 메서드명 정의 쿼리가 그다음으로 진행됨
//    @Query(name= "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    //NamedQuery 대신 실무에서 보편적으로 사용하는 방법
    //(장점)초기화중 쿼리를 파싱하는 과정에 문법 오류를 찾아줌
    //동적쿼리에 대해서는 QueryDSL 사용
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //Dto로 반환
    @Query("select new com.smalldogg.study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //여러 파라미터를 넘겨 in절
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

}
