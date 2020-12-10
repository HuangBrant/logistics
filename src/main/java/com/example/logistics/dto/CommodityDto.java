package com.example.logistics.dto;

import com.example.logistics.entity.Goods;
import com.example.logistics.dto.bean.LogisticsInfo;
import lombok.Data;

import java.util.List;

@Data
public class CommodityDto {

    private List<TableStyles> tableStyles;
    private List<Goods> goods;
    private LogisticsInfo logisticsInfo;
}
