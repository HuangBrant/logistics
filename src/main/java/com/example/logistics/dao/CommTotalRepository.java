package com.example.logistics.dao;

import com.example.logistics.entity.CommTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommTotalRepository extends JpaRepository<CommTotal,Integer>, JpaSpecificationExecutor<CommTotal> {

    @Query(value = "select count('receive') from commodity_total",nativeQuery = true)
    Integer getCount();

    @Query(value = "select count('receive') as receive from commodity_total where cid=?1 GROUP BY cid ",nativeQuery = true)
    Integer countReceive(Integer cid);

    @Query(value = "select start_time from commodity_total where cid=?1 limit 1",nativeQuery = true)
    Date receiveTime(Integer cid);
}
