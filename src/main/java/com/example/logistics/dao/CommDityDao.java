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
                commodity.setId(rs.getInt("id"));
                commodity.setDate(rs.getDate("date"));
                commodity.setExpirationDate(rs.getInt("expiration_date"));
                commodity.setName(rs.getNString("name"));
                commodity.setOverdue(rs.getNString("overdue"));
                commodity.setPrice(rs.getBigDecimal("price"));
                commodity.setTid(rs.getNString("tid"));
                commodity.setType(rs.getNString("type"));

                if (null==commodity.getId() || 0==commodity.getId()){
                    commodityList.add(commodity);
                    continue;
                }
                StringBuilder sb = new StringBuilder("select SUM(receive) as receive,cid,send_status from commodity_total ");
                sb.append(" where cid="+commodity.getId());
                if (null!=startTime){
                    sb.append(" and start_time>"+startTime.getTime());
                    if (null!=endTime){
                        sb.append(" and end_time<="+endTime.getTime());
                    }
                }else if (null!=endTime){
                    sb.append("where end_time<="+endTime.getTime());
                }
                sb.append(" GROUP BY cid,send_status");
                log.info("send sql: "+sb.toString());
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
            log.info("执行sql异常"+e);
        }finally {
            try {
                conn.close();
                ps.close();
                rs.close();
                sps.close();
                srs.close();
            }catch (Exception e){
                log.info("关闭流异常"+e);
            }
        }
        return null;
    }

    public List<CommTotal> getList(Integer cid){
        StringBuilder sb = new StringBuilder("select SUM(receive) as receive,start_time from commodity_total where cid="+cid+" GROUP BY cid, start_time");

        Connection conn = null;

        PreparedStatement sps = null;
        ResultSet srs = null;
        List<CommTotal> commTotalList = new ArrayList<>();

        try {
            conn =dataSource.getConnection();
            sps = conn.prepareStatement(sb.toString());
            srs = sps.executeQuery();
            while (srs.next()) {
                CommTotal commTotal = new CommTotal();
                commTotal.setReceive(srs.getInt("receive"));
                commTotal.setStartTime(srs.getDate("start_time"));
                commTotalList.add(commTotal);
            }

            return commTotalList;
        }catch (Exception e){
            log.info("执行sql异常"+e);
        }finally {
            try {
                conn.close();
                sps.close();
                srs.close();
            }catch (Exception e){
                log.info("关闭流异常"+e);
            }
        }
        return null;
    }

}
