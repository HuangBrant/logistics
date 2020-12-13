package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsInfos {

    private String gname;
    private String type;
    private Date startDate;
    private Date endDate;

    private PeakInfo peakInfo;
    private PackageInfo packageInfo;

}
