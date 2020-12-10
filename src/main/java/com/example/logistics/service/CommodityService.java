package com.example.logistics.service;

import com.example.logistics.dto.CommodityDto;
import com.example.logistics.dto.FlowDto;

import java.util.Date;

public interface CommodityService {

    FlowDto getFlow(Date startTime,Date endTime);

    CommodityDto getShow(Date startTime,Date endTime);
}
