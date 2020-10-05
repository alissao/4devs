package com.olivia.devs.controllers;

import com.olivia.devs.crawler.BaseCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseController {

    @Autowired
    private BaseCrawler crawler;

    @ResponseBody
    @RequestMapping("/")
    public String localTxt(HttpServletResponse response) {
        String fileName = "nickCpfVersion" + Math.random() + ".txt";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        return crawler.getRandomNickCpf(fileName).toString();
    }


}
