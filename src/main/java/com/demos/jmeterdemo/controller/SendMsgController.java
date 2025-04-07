package com.demos.jmeterdemo.controller;


import com.demos.jmeterdemo.pchouse.QQ;
import com.demos.jmeterdemo.twoutil.Qyapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {
    @Autowired
    private Qyapi qyapi;
    @Autowired
    private QQ qq;
    @RequestMapping("/sendmsg/{key}/{day}")
    public String security_send(@PathVariable("key") String key,@PathVariable("day") int day){
        qyapi.senmsgByday(key,day);
        qyapi.senmsgByweek(key,day);
        return "发送成功";
    }

    @RequestMapping("/housemsg/{workspaces_id}/{iteration_id}/{key}")
    public String house_send(@PathVariable("workspaces_id") String workspaces_id,@PathVariable("iteration_id") String iteration_id,@PathVariable("key") String key){
        qq.sendmsg(workspaces_id,iteration_id,key);
        return "发送成功";
    }
}
