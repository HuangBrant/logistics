package com.example.logistics.controller;

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

@CrossOrigin
@Controller
@Slf4j
public class ShowController {

    @Autowired
    VisitsService visitsService;
    @Autowired
    CommodityService commodityService;

    @RequestMapping(value = "/visits/getPageDate",method = RequestMethod.GET)
    @ResponseBody
    public VisitsDto getVisits(@RequestParam(required = false) Date startTime,
                               @RequestParam(required = false) Date endTime, HttpServletResponse response){
        try {
            VisitsDto visitsDto = visitsService.getVisits(startTime, endTime);
            if (null!=startTime) {
                String start = TimeUtil.toString(startTime, "yyyyMMddHHmmss");
                Cookie cookie1 = new Cookie("startTime",start);
                response.addCookie(cookie1);
            }
            if (null!=endTime) {
                String end = TimeUtil.toString(endTime, "yyyyMMddHHmmss");
                Cookie cookie2 = new Cookie("endTime", end);
                response.addCookie(cookie2);
            }
            return visitsDto;
        }catch (Exception e){
            log.info(""+e);
            return null;
        }
    }

    @RequestMapping(value = "/wuliufuzai/getPageDate",method = RequestMethod.GET)
    @ResponseBody
    public BasePage getLoad(HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            Date startTime = null;
            Date endTime = null;
            if (null!=cookies && cookies.length>0) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("startTime")) {
                        startTime = TimeUtil.toDate(cookies[i].getValue(), "yyyyMMddHHmmss");
                    }
                    if (cookies[i].getName().equals("endTime")) {
                        endTime = TimeUtil.toDate(cookies[i].getValue(), "yyyyMMddHHmmss");
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
    @ResponseBody
    public BasePage getWuliuliang(HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            Date startTime = null;
            Date endTime = null;
            if (null!=cookies && cookies.length>0) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("startTime")) {
                        startTime = TimeUtil.toDate(cookies[i].getValue(), "yyyyMMddHHmmss");
                    }
                    if (cookies[i].getName().equals("endTime")) {
                        endTime = TimeUtil.toDate(cookies[i].getValue(), "yyyyMMddHHmmss");
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
    @ResponseBody
    public BasePage getShow(HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            Date startTime = null;
            Date endTime = null;
            if (null!=cookies && cookies.length>0) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("startTime")) {
                        startTime = TimeUtil.toDate(cookies[i].getValue(), "yyyyMMddHHmmss");
                    }
                    if (cookies[i].getName().equals("endTime")) {
                        endTime = TimeUtil.toDate(cookies[i].getValue(), "yyyyMMddHHmmss");
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
