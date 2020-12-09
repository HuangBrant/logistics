package com.example.logistics.dao;

import com.example.logistics.entity.LogisticsTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogisticsTotalRepository extends JpaRepository<LogisticsTotal,Integer> {

    @Query(value = "select * from logistics_total",nativeQuery = true)
    List<LogisticsTotal> findAll();

    @Query(value = "select * from logistics_total where date> ?1 and date<=?2",nativeQuery = true)
    List<LogisticsTotal> findAllByTime(Date startTime,Date endTime);

    @Query(value = "select * from logistics_total where date> ?1",nativeQuery = true)
    List<LogisticsTotal> findAllByStartTime(Date startTime);

    @Query(value = "select * from logistics_total where date<=?1",nativeQuery = true)
    List<LogisticsTotal> findAllByEndTime(Date endTime);
}
