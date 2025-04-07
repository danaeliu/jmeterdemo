package com.demos.jmeterdemo.controller;

import com.demos.jmeterdemo.jmeterutil.JmeterAutoMsg;
import com.demos.jmeterdemo.jmeterutil.JtlErrorEntity;
import com.demos.jmeterdemo.jmeterutil.SendMsgResponseEntity;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class JmeterController {
    @Autowired
    private JmeterAutoMsg jmeterAutoMsg;
    @PostMapping("/mmpDayCheck/")
    public String createUser(@RequestBody SendMsgResponseEntity sendMsgResponseEntity) throws IOException, CsvException {
        // 处理业务逻辑（例如保存到数据库）
        jmeterAutoMsg.mmpDayCheckResultSendMsg(sendMsgResponseEntity.getKey(),sendMsgResponseEntity.getJmeterHome(),sendMsgResponseEntity.getJmxFilePath(),sendMsgResponseEntity.getReportDir());

        return "执行成功";
    }
    @PostMapping("/postcheck/")
    public String postcheck(@RequestBody JtlErrorEntity jtlErrorEntity)  {
        return "测试一下POST-JSON执行成功";
    }
    @RequestMapping("/oktest")
    public String oktest(){

        return "测试成功";
    }
}
