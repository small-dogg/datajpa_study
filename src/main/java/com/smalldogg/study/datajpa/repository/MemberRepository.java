package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.dto.MemberDto;
import com.smalldogg.study.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //메서드의 조건이 2개이상들어가면 다른방법으로 해결
    List<Member> findAllByUsername(String username);

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

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

    List<Member> findListByUsername(String username); //컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // Optional<단건>


    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    List<Member> findListByAge(int age, Pageable pageable);

    //count는 쿼리가 복잡해져서 성능이 안나올 때 분리해서 처리하면 성능이 좋아지는 경우도 있음.
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAgeWithCountQuery(int age, Pageable pageable);

    //Modifying 애노테이션을 넣어주어야 executeUpdate 메서드가 정상적으로 수행함.
    //다만, 영속성 컨텍스트를 거치지 않고 쿼리를 수행하기 떄문에, 수행이후에는 영속성 컨텍스트를 비움.
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    //EntityGraph를 사용하면 Fetch Join을 편리하게 할 수 있다.
    //JPQL없이도 객체그래프를 엮어서 조회해줌. Join Fetch
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //위 Overriding된 findAll과 동일한 결과를 반환
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // 심화형
    @EntityGraph(attributePaths = {"team"})
//    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    //select for update(isolation, propagation 관련)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
