package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.Date;

@Data
public class VisitsScanInfos {

    private Integer total;
    private Integer invalid;
    private Integer loss;
    private Integer logisticsNum;
    private Integer drop;
    private String fieldPath;
    private Date expendTime;
    private Date scanTime;
}
