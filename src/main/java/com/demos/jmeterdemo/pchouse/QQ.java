package com.demos.jmeterdemo.pchouse;

import com.demos.jmeterdemo.twoutil.WeChatBotUtils;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.MediaSize;
import java.util.List;
@Service
public class QQ {
    /*public static void main(String[] args) {
        QQ qq=new QQ();
        qq.sendmsg("51027841","1151027841001001415","3220bd0d-7415-475c-8116-0a7ac8deba5e");

    }*/
    public void sendmsg(String workspace,String iteration_id,String key){
        String botUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + key;
        WeChatBotUtils weChatBot = new WeChatBotUtils(botUrl, true);
        BusIness busIness=new BusIness();
        List<String> info = busIness.getAllBugs(workspace,iteration_id);
        StringBuffer sb=new StringBuffer();
        sb.append("【缺陷统计】\n 项目ID:"+"<font color=\"warning\">" +workspace+ "</font>"+", 迭代ID："+"<font color=\"warning\">" +iteration_id+"</font>"+"，【");
        sb.append("[缺陷地址查看](https://www.tapd.cn/"+workspace+"/prong/iterations/view/"+iteration_id+"#tab=Bugs) 】\n");
        for(String msg:info){
            sb.append("<font color=\"info\">" + msg + "</font>" +"\n");
        }
        try {
            weChatBot.sendMarKDownMsg(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        String botUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=3220bd0d-7415-475c-8116-0a7ac8deba5e";
        WeChatBotUtils weChatBot = new WeChatBotUtils(botUrl, true);
        StringBuffer sb=new StringBuffer();
        sb.append("<font color=\"info\">[线上报障_PGC]</font> \n");
        sb.append("<font color=\"warning\">404列表：</font>\n");
        sb.append("[https://pcncc.pconline.com.cn/appInformationApi/get24News?pageSize=20&pageNo=1](https://pcncc.pconline.com.cn/appInformationApi/get24News?pageSize=20&pageNo=1)\n");
        sb.append("<font color=\"warning\">503列表：</font>\n");
        sb.append("[https://pcncc.pconline.com.cn/appInformationApi/get24News?pageSize=20&pageNo=1](https://pcncc.pconline.com.cn/appInformationApi/get24News?pageSize=20&pageNo=1)\n");
        weChatBot.sendMarKDownMsg(sb.toString());
    }
}
