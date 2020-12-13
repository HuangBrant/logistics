package com.example.logistics.dto.bean;

import lombok.Data;

import java.util.List;

@Data
public class VisitsPageData {

    private List<VisitsScanInfos> scanInfos;

    private VisitsAvgInfo avgInfo;

}
