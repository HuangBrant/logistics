package com.example.logistics.dao;

import com.example.logistics.entity.CommTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommTotalRepository extends JpaRepository<CommTotal,Integer> {
}
