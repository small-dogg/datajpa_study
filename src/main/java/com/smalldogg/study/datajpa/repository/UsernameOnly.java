package com.smalldogg.study.datajpa.repository;


import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    //인터페이스 기반의 Close 프로젝션
//    String getUsername();

    //Open 프로젝션
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
