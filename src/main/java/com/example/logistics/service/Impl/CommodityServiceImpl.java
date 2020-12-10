package com.example.logistics.service.Impl;

import com.example.logistics.dao.CommTotalRepository;
import com.example.logistics.dao.CommodityRepository;
import com.example.logistics.dao.GoodsRepository;
import com.example.logistics.dto.CommodityDto;
import com.example.logistics.dto.FlowDto;
import com.example.logistics.dto.TableStyles;
import com.example.logistics.dto.bean.GoodsInfos;
import com.example.logistics.dto.bean.LogisticsInfo;
import com.example.logistics.dto.bean.PackageInfo;
import com.example.logistics.dto.bean.PeakInfo;
import com.example.logistics.entity.CommTotal;
import com.example.logistics.entity.Commodity;
import com.example.logistics.entity.Goods;
import com.example.logistics.service.CommodityService;
import com.example.logistics.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    CommodityRepository commodityRepository;
    @Autowired
    CommTotalRepository commTotalRepository;
    @Autowired
    GoodsRepository goodsRepository;


    @Value("${total_bandwidth}")
    private Float count;

    @Override
    public FlowDto getFlow(Date startTime, Date endTime) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Join<Commodity, CommTotal> join = root.join("cid");
                if (null!=startTime){
                    list.add(cb.greaterThan(join.get("commTotal").get("startTime"),startTime));
                }
                if (null!=endTime){
                    list.add(cb.lessThanOrEqualTo(join.get("commTotal").get("endTime"),endTime));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        List<Commodity> list = commodityRepository.findAll(specification);

        List<GoodsInfos> collect = list.stream()
                .map(a -> {
                    GoodsInfos goodsInfos = new GoodsInfos();

                    int sendTotal = a.getCommTotal().stream().filter(s ->(s.getSendStatus()==1))
                            .collect(Collectors.summingInt(c ->c.getReceive()));
                    int receiveTotal = a.getCommTotal().stream().filter(s ->(s.getSendStatus()==2))
                            .collect(Collectors.summingInt(c ->c.getReceive()));
                    PackageInfo packageInfo = new PackageInfo();
                    packageInfo.setReceiveNum( receiveTotal);
                    packageInfo.setSentNum(sendTotal);
                    packageInfo.setTotalNum(sendTotal + receiveTotal);
                    goodsInfos.setPackageInfo(packageInfo);

                    goodsInfos.setGname(a.getName());
                    goodsInfos.setStartDate(startTime);
                    goodsInfos.setEndDate(endTime);

                    List<Integer> totalList = a.getCommTotal().stream()
                            .map(b -> {
                                return (int)b.getReceive() ;
                            }).collect(Collectors.toList());
                    Integer max = totalList.stream().max(Integer::compareTo).get();
                    Integer min = totalList.stream().min(Integer::compareTo).get();
                    PeakInfo peakInfo = new PeakInfo();
                    peakInfo.setMin(min);
                    peakInfo.setMax(max);
                    peakInfo.setTotal(count.intValue());
                    goodsInfos.setPeakInfo(peakInfo);

                    return goodsInfos;
                }).collect(Collectors.toList());

        FlowDto flowDto = new FlowDto();
        flowDto.setGoodsInfos(collect);
        return flowDto;
    }

    @Override
    public CommodityDto getShow(Date startTime, Date endTime) {

        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                Join<Commodity, CommTotal> join = root.join("cid");
                if (null!=startTime){
                    list.add(cb.greaterThan(join.get("commTotal").get("startTime"),startTime));
                }
                if (null!=endTime){
                    list.add(cb.lessThanOrEqualTo(join.get("commTotal").get("endTime"),endTime));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        List<Commodity> list = commodityRepository.findAll(specification);

        List<TableStyles> styles = list.stream()
                .map(a -> {
                    TableStyles tableStyles = new TableStyles();
                    tableStyles.setDate(a.getDate());
                    tableStyles.setExpirationDate(a.getExpirationDate());
                    tableStyles.setId(a.getId());
                    tableStyles.setOverdue(a.getOverdue());
                    tableStyles.setPrice(a.getPrice());
                    tableStyles.setProductName(a.getName());
                    tableStyles.setType(a.getType());
                    return tableStyles;
                }).collect(Collectors.toList());
        CommodityDto commodityDto = new CommodityDto();
        commodityDto.setTableStyles(styles);

        Specification specification1 = (root, criteriaQuery, cb) -> {
            List<Predicate> list1 = new ArrayList<Predicate>();
            if (null!=startTime){
                list1.add(cb.greaterThan(root.get("startTime"),startTime));
            }
            if (null!=endTime){
                list1.add(cb.lessThanOrEqualTo(root.get("endTime"),endTime));
            }
            return cb.and(list1.toArray(new Predicate[list1.size()]));
        };
        List<CommTotal> commTotalList = commTotalRepository.findAll(specification1);

        LogisticsInfo logisticsInfo = new LogisticsInfo();
        int sendT = 0;
        int receiveT = 0;
        BigDecimal sendA = BigDecimal.valueOf(0);
        BigDecimal receiveA = BigDecimal.valueOf(0);
        for (CommTotal commTotal:commTotalList){
            if (1==commTotal.getSendStatus()) {
                sendT += commTotal.getReceive();
                sendA = sendA.add(commTotal.getPrice());
            }else {
                receiveT += commTotal.getReceive();
                receiveA = receiveA.add(commTotal.getPrice());
            }
        }
        int day = TimeUtil.betweenDay(startTime, endTime);
        logisticsInfo.setReceiveAmount(receiveA);
        logisticsInfo.setReceivePackage(receiveT);
        logisticsInfo.setReceiveSpeed(receiveT/day);
        logisticsInfo.setSentAmount(sendA);
        logisticsInfo.setSentPackage(sendT);
        logisticsInfo.setSentSpeed(sendT/day);
        logisticsInfo.setTimeStr(day);
        logisticsInfo.setTotalAmount(sendA.add(receiveA));
        logisticsInfo.setTotalPackage(sendT+receiveT);
        commodityDto.setLogisticsInfo(logisticsInfo);

        List<Goods> goodsList = goodsRepository.findAll();

        commodityDto.setGoods(goodsList);
        return commodityDto;
    }
}