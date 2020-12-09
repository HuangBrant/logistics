package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.Date;

@Data
public class VisitsAvgInfo {

    private Integer totalNume;
    private Integer receiveNum;
    private Integer sentNum;
    private Date startDate;
    private Date endDate;
}
