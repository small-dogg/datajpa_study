package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
