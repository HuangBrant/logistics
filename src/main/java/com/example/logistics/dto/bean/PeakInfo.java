package com.example.logistics.dto.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class PeakInfo {

    private Integer total;

    private Integer max;

    private Integer min;
}
