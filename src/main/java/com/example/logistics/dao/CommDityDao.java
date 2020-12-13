package com.example.logistics.dao;

import com.example.logistics.entity.CommTotal;
import com.example.logistics.entity.Commodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class CommDityDao {

    @Autowired
    private DataSource dataSource;

    public List<Commodity> getList(Date startTime, Date endTime){
        StringBuilder csb = new StringBuilder("select * from commodity");

        StringBuilder sb = new StringBuilder("select count(receive) as receive,cid,send_status from commodity_total ");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        PreparedStatement sps = null;
        ResultSet srs = null;

        List<Commodity> commodityList = new ArrayList<>();
        try {
            log.info("sql:"+csb.toString());
            conn = dataSource.getConnection();
            ps = conn.prepareCall(csb.toString());
            rs = ps.executeQuery();

            while (rs.next()){
                Commodity commodity = new Commodity();
                commodity.setDate(rs.getDate("date"));
                commodity.setExpirationDate(rs.getInt("expiration_date"));
                commodity.setName(rs.getNString("name"));
                commodity.setOverdue(rs.getNString("overdue"));
                commodity.setPrice(rs.getBigDecimal("price"));
                commodity.setTid(rs.getNString("tid"));
                commodity.setType(rs.getNString("type"));

                sb.append(" where cid="+commodity.getId());
                if (null!=startTime){
                    sb.append(" and start_time>"+startTime);
                    if (null!=endTime){
                        sb.append(" and end_time<="+endTime);
                    }
                }else if (null!=endTime){
                    sb.append("where end_time<="+endTime);
                }
                sb.append(" GROUP BY cid,send_status");
                sps = conn.prepareStatement(sb.toString());
                srs = sps.executeQuery();
                List<CommTotal> commTotalList = new ArrayList<>();
                while (srs.next()) {
                    CommTotal commTotal = new CommTotal();
                    commTotal.setReceive(srs.getInt("receive"));
                    commTotal.setCid(srs.getInt("cid"));
                    commTotal.setSendStatus(srs.getInt("send_status"));
                    commTotalList.add(commTotal);
                }
                commodity.setCommTotal(commTotalList);
                commodityList.add(commodity);
            }
            return commodityList;
        }catch (Exception e){

        }finally {

        }
        return null;
    }
}
