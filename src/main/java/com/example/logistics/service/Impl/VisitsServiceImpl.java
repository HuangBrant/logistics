package com.example.logistics.service.Impl;

import com.example.logistics.dao.LogisticsTotalRepository;
import com.example.logistics.dto.LoadDto;
import com.example.logistics.dto.bean.*;
import com.example.logistics.dto.VisitsDto;
import com.example.logistics.entity.LogisticsTotal;
import com.example.logistics.service.VisitsService;
import com.example.logistics.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitsServiceImpl implements VisitsService {
    @Autowired
    LogisticsTotalRepository logisticsTotalRepository;

    @Value("${total_bandwidth}")
    private Float count;

    @Override
    public VisitsDto getVisits(Date startTime, Date endTime) {
        List<LogisticsTotal> totalList = new ArrayList<>();

        if (null!=startTime && null!=endTime){
            totalList = logisticsTotalRepository.findAllByTime(startTime, endTime);
        }else if (null!=startTime && null==endTime){
            totalList = logisticsTotalRepository.findAllByStartTime(startTime);
        }else if (null==startTime && null!=endTime){
            totalList = logisticsTotalRepository.findAllByEndTime(endTime);
        }else {
            totalList = logisticsTotalRepository.findAll();
        }
        if (totalList.size()<=0){
            return null;
        }

        List<VisitsScanInfos> scanInfosList = totalList.stream()
                .map(a -> {
                    VisitsScanInfos visitsScanInfos = new VisitsScanInfos();
                    visitsScanInfos.setDrop(a.getDrop());
                    int m = a.getUsedTime() / 60;
                    int s = a.getUsedTime() % 60;
                    visitsScanInfos.setExpendTime(m+" min "+s+" s");
                    visitsScanInfos.setFieldPath(a.getPath());
                    visitsScanInfos.setInvalid(a.getInvalid());
                    visitsScanInfos.setLogisticsNum(a.getLogisticsNum());
                    visitsScanInfos.setLoss(a.getLoss());
                    String time = TimeUtil.toString(a.getDate(), "yyyyMMdd hh:mm:ss");
                    visitsScanInfos.setScanTime(time);
                    visitsScanInfos.setTotal(a.getTotal());
                    return visitsScanInfos;
                        })
                .collect(Collectors.toList());

        VisitsDto visitsDto = new VisitsDto();
        VisitsPageData visitsPageData = new VisitsPageData();
        visitsPageData.setScanInfos(scanInfosList);

        long total = totalList.stream().mapToInt(a -> (a.getLogisticsNum())).sum();//总数
        long receiveTotal = totalList.stream().mapToInt(a -> (a.getReceiveNum())).sum();//收到总数
        long sendTotal = totalList.stream().mapToInt(a -> (a.getSentNum())).sum();//收到总数

        VisitsAvgInfo visitsAvgInfo = new VisitsAvgInfo();
        visitsAvgInfo.setTotalNume((int)(total/totalList.size()));
        visitsAvgInfo.setReceiveNum((int)(receiveTotal/totalList.size()));
        visitsAvgInfo.setSentNum((int)(sendTotal/totalList.size()));
        visitsAvgInfo.setStartDate(startTime);
        visitsAvgInfo.setEndDate(endTime);
        visitsPageData.setAvgInfo(visitsAvgInfo);
        visitsDto.setPageData(visitsPageData);

        return visitsDto;
    }

    @Override
    public LoadDto getLoad(Date startTime, Date endTime) {
        List<LogisticsTotal> totalList = new ArrayList<>();

        if (null!=startTime && null!=endTime){
            totalList = logisticsTotalRepository.findAllByTime(startTime, endTime);
        }else if (null!=startTime && null==endTime){
            totalList = logisticsTotalRepository.findAllByStartTime(startTime);
        }else if (null==startTime && null!=endTime){
            totalList = logisticsTotalRepository.findAllByEndTime(endTime);
        }else {
            totalList = logisticsTotalRepository.findAll();
        }
        if (totalList.size()<=0){
            return null;
        }

        LoadDto loadDto = new LoadDto();

        long total = totalList.stream().mapToInt(a -> (a.getLogisticsNum())).sum();//总数
        long receiveTotal = totalList.stream().mapToInt(a -> (a.getReceiveNum())).sum();//收到总数
        long sendTotal = totalList.stream().mapToInt(a -> (a.getSentNum())).sum();//收到总数

        VisitsAvgInfo visitsAvgInfo = new VisitsAvgInfo();
        visitsAvgInfo.setTotalNume((int)(total/totalList.size()));
        visitsAvgInfo.setReceiveNum((int)(receiveTotal/totalList.size()));
        visitsAvgInfo.setSentNum((int)(sendTotal/totalList.size()));
        visitsAvgInfo.setStartDate(startTime);
        visitsAvgInfo.setEndDate(endTime);
        loadDto.setAvgInfo(visitsAvgInfo);

        List<Integer> totals = totalList.stream().map(a -> (a.getLogisticsNum())).collect(Collectors.toList());
        Integer max = totals.stream().max(Integer::compareTo).get();
        Integer min = totals.stream().min(Integer::compareTo).get();

        PeakInfo peakInfo = new PeakInfo();
        peakInfo.setMax(max);
        peakInfo.setMin(min);
        peakInfo.setTotal(count.intValue());
        loadDto.setPeakInfo(peakInfo);

        List<Series> datas = totalList.stream()
                .map(a -> {
                    Date date = a.getDate();
                    String time = TimeUtil.toString(date, "yyyy/MM/dd");
                    double i = a.getLogisticsNum() / count;
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    //List<String> stringList = new ArrayList<>();
                    //String s ="Date.UTC("+time+"),"+decimalFormat.format(i);
                    Series series = new Series();
                    series.setDate(time);
                    series.setValue(decimalFormat.format(i));
                    //stringList.add(s);
                    return series;
                }).collect(Collectors.toList());
        Highcharts highcharts = new Highcharts();
        highcharts.setDatas(datas);
        loadDto.setHighcharts(highcharts);

        return loadDto;
    }
}
