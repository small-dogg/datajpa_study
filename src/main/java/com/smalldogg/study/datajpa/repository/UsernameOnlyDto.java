package com.smalldogg.study.datajpa.repository;

public class UsernameOnlyDto {

    private final String username;

    //파라미터 명을 기반으로로
   public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
