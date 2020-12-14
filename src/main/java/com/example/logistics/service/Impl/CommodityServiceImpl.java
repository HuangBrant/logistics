package com.example.logistics.service.Impl;

import com.example.logistics.dao.CommDityDao;
import com.example.logistics.dao.CommTotalRepository;
import com.example.logistics.dao.CommodityRepository;
import com.example.logistics.dao.GoodsRepository;
import com.example.logistics.dto.CommodityDto;
import com.example.logistics.dto.FlowDto;
import com.example.logistics.dto.TableStyles;
import com.example.logistics.dto.bean.*;
import com.example.logistics.entity.CommTotal;
import com.example.logistics.entity.Commodity;
import com.example.logistics.entity.Goods;
import com.example.logistics.service.CommodityService;
import com.example.logistics.util.TimeUtil;
import com.example.logistics.util.Util;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    CommodityRepository commodityRepository;
    @Autowired
    CommTotalRepository commTotalRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CommDityDao commDityDao;


    @Value("${total_bandwidth}")
    private Float count;

    @Override
    public FlowDto getFlow(Date startTime, Date endTime) {

        List<Commodity> list = commDityDao.getList(startTime, endTime);

        long commTotal = commTotalRepository.getCount();//总商品总数

        List<Series> seriesList = new ArrayList<>();
        List<GoodsInfos> collect = list.stream()
                .map(a -> {
                    GoodsInfos goodsInfos = new GoodsInfos();

                    int sendTotal = a.getCommTotal().stream().filter(s ->(s.getSendStatus()==1))
                            .mapToInt(c -> (c.getReceive())).sum();
                    int receiveTotal = a.getCommTotal().stream().filter(s ->(s.getSendStatus()==2))
                            .mapToInt(c -> (c.getReceive())).sum();
                    PackageInfo packageInfo = new PackageInfo();
                    packageInfo.setReceiveNum( receiveTotal);
                    packageInfo.setSentNum(sendTotal);
                    packageInfo.setTotalNum(sendTotal + receiveTotal);
                    goodsInfos.setPackageInfo(packageInfo);

                    goodsInfos.setGname(a.getName());
                    goodsInfos.setType(a.getType());
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

                    List<CommTotal> receive = commDityDao.getList(a.getId());//每类商品总数,时间

                    if (receive.size()>0) {
                        Series series = new Series();
                        List<double[]> dList = new ArrayList<>();
                        for (CommTotal c:receive) {
                            double j = c.getReceive() / commTotal;
                            BigDecimal bg = new BigDecimal(j);
                            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                            double[] db = new double[2];
                            log.info("时间:"+c.getStartTime().getTime());
                            db[0] = (double) c.getStartTime().getTime();
                            db[1] = f1;
                            dList.add(db);
                        }


                        series.setData(dList);
                        series.setType("areaspline");
                        series.setName(a.getName());
                        seriesList.add(series);
                    }

                    return goodsInfos;
                }).collect(Collectors.toList());


        FlowHighcharts flowHighcharts = new FlowHighcharts();



        FlowDto flowDto = new FlowDto();
        flowDto.setSeries(seriesList);
        flowHighcharts.setColors(Util.getColors(seriesList.size()));
        flowDto.setFlowHighcharts(flowHighcharts);
        flowDto.setGoodsInfos(collect);
        return flowDto;
    }

    @Override
    public CommodityDto getShow(Date startTime, Date endTime) {

        List<Commodity> list = commDityDao.getList(startTime, endTime);

        List<TableStyles> styles = list.stream()
                .map(a -> {
                    TableStyles tableStyles = new TableStyles();
                    tableStyles.setDate(a.getDate().getTime());
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


        Specification specification2 = (root, criteriaQuery, cb) -> {
            List<Predicate> list1 = new ArrayList<Predicate>();
            if (null!=startTime){
                list1.add(cb.greaterThan(root.get("date"),startTime));
            }
            if (null!=endTime){
                list1.add(cb.lessThanOrEqualTo(root.get("date"),endTime));
            }
            return cb.and(list1.toArray(new Predicate[list1.size()]));
        };
        List<Goods> goodsList = goodsRepository.findAll(specification2);

        commodityDto.setGoods(goodsList);
        return commodityDto;
    }
}
