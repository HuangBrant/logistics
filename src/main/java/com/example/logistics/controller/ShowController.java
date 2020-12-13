package com.example.logistics.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.logistics.dto.*;
import com.example.logistics.service.CommodityService;
import com.example.logistics.service.VisitsService;
import com.example.logistics.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@Slf4j
public class ShowController {

    @Autowired
    VisitsService visitsService;
    @Autowired
    CommodityService commodityService;

    @RequestMapping(value = "/visits/getPageDate",method = RequestMethod.GET)
    public VisitsDto getVisits(@RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime, HttpServletResponse response,HttpServletRequest request){
        try {
            Date start =null;
            Date end = null;
            String url = request.getHeader("Origin");
            if (!StringUtils.isEmpty(url)) {
                String val = response.getHeader("Access-Control-Allow-Origin");
                if (StringUtils.isEmpty(val)) {
                    response.addHeader("Access-Control-Allow-Origin", url);
                    response.addHeader("Access-Control-Allow-Credentials", "true");
                }
            }
            if (null!=startTime) {
                start = TimeUtil.toDate(startTime, "yyyy-MM-dd");
                Cookie cookie1 = new Cookie("startTime",startTime);
                cookie1.setMaxAge(-1);
                cookie1.setPath("/");
                response.addCookie(cookie1);
            }
            if (null!=endTime) {
                end = TimeUtil.toDate(endTime, "yyyy-MM-dd");
                Cookie cookie2 = new Cookie("endTime", startTime);
                response.addCookie(cookie2);
            }
            VisitsDto visitsDto = visitsService.getVisits(start, end);

            return visitsDto;
        }catch (Exception e){
            log.info(""+e);
            return null;
        }
    }

    @RequestMapping(value = "/wuliufuzai/getPageDate",method = RequestMethod.GET)
    public BasePage getLoad(HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            Date startTime = null;
            Date endTime = null;
            if (null!=cookies && cookies.length>0) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("startTime")) {
                        startTime = TimeUtil.toDate(cookies[i].getValue(), "yyyy-MM-dd");
                    }
                    if (cookies[i].getName().equals("endTime")) {
                        endTime = TimeUtil.toDate(cookies[i].getValue(), "yyyy-MM-dd");
                    }
                }
            }
            LoadDto load = visitsService.getLoad(startTime, endTime);
            BasePage basePage = new BasePage();
            basePage.setPageData(load);
            return basePage;
        }catch (Exception e){
            log.info(""+e);
            return null;
        }
    }

    @RequestMapping(value = "/wuliuliang/getPageDate",method = RequestMethod.GET)
    public BasePage getWuliuliang(HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            Date startTime = null;
            Date endTime = null;
            if (null!=cookies && cookies.length>0) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("startTime")) {
                        startTime = TimeUtil.toDate(cookies[i].getValue(), "yyyy-MM-dd");
                    }
                    if (cookies[i].getName().equals("endTime")) {
                        endTime = TimeUtil.toDate(cookies[i].getValue(), "yyyy-MM-dd");
                    }
                }
            }
            FlowDto flow = commodityService.getFlow(startTime, endTime);
            BasePage basePage = new BasePage();
            basePage.setPageData(flow);
            return basePage;
        }catch (Exception e){
            log.info(""+e);
            return null;
        }
    }

    @RequestMapping(value = "/showTables/getPageDate",method = RequestMethod.GET)
    public BasePage getShow(HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            Date startTime = null;
            Date endTime = null;
            if (null!=cookies && cookies.length>0) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("startTime")) {
                        startTime = TimeUtil.toDate(cookies[i].getValue(), "yyyy-MM-dd");
                    }
                    if (cookies[i].getName().equals("endTime")) {
                        endTime = TimeUtil.toDate(cookies[i].getValue(), "yyyy-MM-dd");
                    }
                }
            }
            CommodityDto show = commodityService.getShow(startTime, endTime);
            BasePage basePage = new BasePage();
            basePage.setPageData(show);
            return basePage;
        }catch (Exception e){
            log.info(""+e);
            return null;
        }
    }
}
