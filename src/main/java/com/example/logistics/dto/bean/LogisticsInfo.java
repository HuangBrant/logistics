package com.example.logistics.dto.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LogisticsInfo {
    private Integer timeStr;
    private Integer sentSpeed;
    private Integer receiveSpeed;
    private Integer totalPackage;
    private Integer sentPackage;
    private Integer receivePackage;
    private BigDecimal totalAmount;
    private BigDecimal sentAmount;
    private BigDecimal receiveAmount;
}
