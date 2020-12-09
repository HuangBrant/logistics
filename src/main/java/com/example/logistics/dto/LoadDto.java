package com.example.logistics.dto;

import com.example.logistics.dto.bean.Highcharts;
import com.example.logistics.dto.bean.PeakInfo;
import com.example.logistics.dto.bean.VisitsAvgInfo;
import lombok.Data;

@Data
public class LoadDto {

    private PeakInfo peakInfo;

    private VisitsAvgInfo avgInfo;

    private Highcharts highcharts;
}
