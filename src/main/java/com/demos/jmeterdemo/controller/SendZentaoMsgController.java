package com.demos.jmeterdemo.controller;


import com.demos.jmeterdemo.zentao.ZentaoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Map;

@RestController
public class SendZentaoMsgController {
    @Resource
    private ZentaoApi zentaoApi;
//    @RequestMapping("/sendmsg/{key}")
//    public String security_send(@PathVariable("key") String key){
//        zentaoQyapi.sendmsg(key);
//        return "发送成功";
//    }

    @PostMapping("/sendmsg")
    public String decode(@RequestBody Map<String,String> params) throws ParseException {
        String starttime = params.get("starttime");
        String endtime = params.get("endtime");
        String sb = zentaoApi.getBugs1(starttime,endtime);

        return sb;
    }
}
