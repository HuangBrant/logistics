package com.example.logistics.service.Impl;

import com.example.logistics.dao.LogisticsTotalRepository;
import com.example.logistics.dto.VisitsAvgInfo;
import com.example.logistics.dto.VisitsDto;
import com.example.logistics.dto.VisitsPageData;
import com.example.logistics.dto.VisitsScanInfos;
import com.example.logistics.entity.LogisticsTotal;
import com.example.logistics.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitsServiceImpl implements VisitsService {
    @Autowired
    LogisticsTotalRepository logisticsTotalRepository;

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
                            visitsScanInfos.setExpendTime(a.getUsed_time());
                            visitsScanInfos.setFieldPath(a.getPath());
                            visitsScanInfos.setInvalid(a.getInvalid());
                            visitsScanInfos.setLogisticsNum(a.getLogisticsNum());
                            visitsScanInfos.setLoss(a.getLoss());
                            visitsScanInfos.setScanTime(a.getDate());
                            visitsScanInfos.setTotal(a.getTotal());
                            return visitsScanInfos;
                        })
                .collect(Collectors.toList());

        VisitsDto visitsDto = new VisitsDto();
        VisitsPageData visitsPageData = new VisitsPageData();
        visitsPageData.setScanInfos(scanInfosList);
        visitsDto.setPageData(visitsPageData);

        long total = totalList.stream().map(a -> (a.getLogisticsNum())).count();//总数
        long receiveTotal = totalList.stream().map(a -> (a.getReceiveNum())).count();//收到总数
        long sendTotal = totalList.stream().map(a -> (a.getSentNum())).count();//收到总数

        VisitsAvgInfo visitsAvgInfo = new VisitsAvgInfo();
        visitsAvgInfo.setTotalNume((int)(total/totalList.size()));
        visitsAvgInfo.setReceiveNum((int)(receiveTotal/totalList.size()));
        visitsAvgInfo.setSentNum((int)(sendTotal/totalList.size()));
        visitsAvgInfo.setStartDate(startTime);
        visitsAvgInfo.setEndDate(endTime);
        visitsDto.setAvgInfo(visitsAvgInfo);

        return visitsDto;
    }
}
