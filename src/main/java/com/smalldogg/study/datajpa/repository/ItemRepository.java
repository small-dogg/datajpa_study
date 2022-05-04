package com.smalldogg.study.datajpa.repository;

import com.smalldogg.study.datajpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
