package com.demos.jmeterdemo.zentao;


import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 作者
* @version 创建时间：2019年3月20日 下午4:27:36
* 类说明
*/
@Service
public class ZentaoQyapi {
	/**
	 * （一） 待处理线上报障
	 */


//	public  void sendmsg(String key,String starttime,String endtime) throws ParseException {
//		Qyapi qyapi=new Qyapi();
////		String botUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=8494aac0-b719-4fe5-aeda-71eccf1e5e69" ;
//		//pchouse-test
//		String botUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key="+key ;
//		WeChatBotUtils weChatBot = new WeChatBotUtils(botUrl, true);
//		ZentaoApi zeng=new ZentaoApi();
//		Map<String,List<ZentaoResult>> resultall=zeng.getBugs(starttime,endtime);
//		StringBuffer sb=new StringBuffer();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date time1 = sdf.parse(starttime);
//		Date time2 = sdf.parse(endtime);
//		sb.append("本周("+sdf.format(time1)+"至"+sdf.format(time2)+")待处理问题：详情： \n");
//		for(Map.Entry<String, List<ZentaoResult>> entry:resultall.entrySet()){
//			List<ZentaoResult> s1=  entry.getValue();
//			if (s1.size()>0){
//				sb.append(entry.getKey()+"：详情：\n");
////				System.out.println(entry.getKey()+":");
//				for (ZentaoResult z:s1){
//					sb.append("["+z.getBugtitle()+"]("+z.getBugurl()+")  ,处理人："+z.getRealname()+"\n");
//					System.out.println(z.getBugid()+z.getBugtitle());
//				}
//			}
//
//		}
//		sb.append("@所有人");
//
//		try {
//			weChatBot.sendMarKDownMsg(sb.toString());
//
//		}catch (Exception e) {
//		    e.printStackTrace();
//		}
//
//	}

//	public static void main(String[] args) throws Exception {
//
//
//	}


}