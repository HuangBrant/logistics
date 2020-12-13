package com.example.logistics.dto;

import com.example.logistics.dto.bean.FlowHighcharts;
import com.example.logistics.dto.bean.GoodsInfos;
import lombok.Data;

import java.util.List;

@Data
public class FlowDto {
    private List<GoodsInfos> goodsInfos;
    private FlowHighcharts flowHighcharts;
}
