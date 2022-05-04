package com.smalldogg.study.datajpa.controller;

import com.smalldogg.study.datajpa.dto.MemberDto;
import com.smalldogg.study.datajpa.entity.Member;
import com.smalldogg.study.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //권장하지 않음.
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    //localhost:8080/members?page=7&size=3&sort=id,desc
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5) Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page.map(MemberDto::new);
    }

//    @PostConstruct
//    public void init() {
//        for (int i = 0; i <100; i++) {
//            memberRepository.save(new Member("user" +i,i));
//        }
//    }
}
