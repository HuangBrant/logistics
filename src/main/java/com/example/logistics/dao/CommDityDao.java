package com.example.logistics.dao;

import com.example.logistics.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

@Component
public class CommDityDao {

    @Autowired
    private DataSource dataSource;

    public List<Commodity> getList(Date startTime, Date endTime){
        StringBuilder sb = new StringBuilder("select count(receive) as receive,cid,send_status from commodity_total ");

        if (null!=startTime){
            sb.append(" where");
            sb.append(" start_time>"+startTime);
            if (null!=endTime){
                sb.append(" and end_time<="+endTime);
            }
        }else if (null!=endTime){
            sb.append("where end_time<="+endTime);
        }
        sb.append(" GROUP BY cid,send_status");
        Connection conn = null;

        return null;
    }
}
