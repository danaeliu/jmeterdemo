package com.demos.jmeterdemo.twoutil;

import org.springframework.stereotype.Service;

import javax.activation.MailcapCommandMap;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 作者
* @version 创建时间：2019年3月20日 下午4:27:36
* 类说明
*/
@Service
public class Qyapi {
	/**
	 * （一） 待处理线上报障
	 */
	public  void senmsgByday(String key,int beforeDays) {
		/*3220bd0d-7415-475c-8116-0a7ac8deba5e*/
		String botUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + key;
		WeChatBotUtils weChatBot = new WeChatBotUtils(botUrl, true);
		TapdApiBusiness tapdApi = new TapdApiBusiness();
		List<ResultEntity> cmap = tapdApi.getbugsByday("68331031", "1168331031001000392",beforeDays);
		List<ResultEntity> qudaomap = tapdApi.getbugsByday("61792590", "1161792590001000446",beforeDays);
		List<ResultEntity> bbsmap = tapdApi.getbugsByday("30892271", "1130892271001000738",beforeDays);
		StringBuffer cname=new StringBuffer();
		StringBuffer qudaoname=new StringBuffer();
		StringBuffer bbsname=new StringBuffer();
		//处理1个大项目空间
		if(cmap.size()>0){
			Map<String, List<ResultEntity>> corderGroupMap = cmap.stream().collect(Collectors.groupingBy(ResultEntity::getProjectName));
			Set cset = corderGroupMap.keySet();
			for (Object o : cset) {
				cname.append("    ><font color=\"info\">[线上报障_" + o + "]</font>" + corderGroupMap.get(o).size() + "个\n");
				cname.append("详情查看:\n");
				for(ResultEntity one:corderGroupMap.get(o)){
					System.out.println(one.getUrl());
					cname.append(corderGroupMap.get(o).indexOf(one)+1 +") "+one.getUrl()+" \n");
				}
			}
		}
		if (qudaomap.size()>0){
			//处理2个大项目空间
			Map<String, List<ResultEntity>> qudaoorderGroupMap = qudaomap.stream().collect(Collectors.groupingBy(ResultEntity::getProjectName));
			Set qudaoset = qudaoorderGroupMap.keySet();
			for (Object o : qudaoset) {
				qudaoname.append("    ><font color=\"info\">[线上报障_" + o + "]</font>" + qudaoorderGroupMap.get(o).size() + "个\n");
				qudaoname.append("详情查看:\n");
				for(ResultEntity one:qudaoorderGroupMap.get(o)){
					System.out.println(qudaoorderGroupMap.get(o).indexOf(one));
					qudaoname.append(qudaoorderGroupMap.get(o).indexOf(one)+1 +") "+one.getUrl()+" \n");
				}
			}
		}
		if (bbsmap.size()>0){
			//处理1个大项目空间
			Map<String, List<ResultEntity>> bbsorderGroupMap = bbsmap.stream().collect(Collectors.groupingBy(ResultEntity::getProjectName));

			Set bbsset = bbsorderGroupMap.keySet();
			for (Object o : bbsset) {
				bbsname.append("    ><font color=\"info\">[线上报障_" + o + "]</font>" + bbsorderGroupMap.get(o).size() + "个\n");
				bbsname.append("详情查看:\n");
				for(ResultEntity one:bbsorderGroupMap.get(o)){
					bbsname.append(bbsorderGroupMap.get(o).indexOf(one)+1 +") "+one.getUrl()+" \n");
				}
			}
		}

		try {
			int totalcount=cmap.size()+qudaomap.size()+bbsmap.size();
			if (totalcount>0){
				String markdownMsg =
						"[线上报障]近"+(beforeDays-1)+"天内,优先级高的未安排修复或者5天内需修复的故障共有"+totalcount+"个未处理的故障：\n" +cname.toString()
								+qudaoname.toString() +bbsname.toString();
				weChatBot.sendMarKDownMsg(markdownMsg);
			}

		}catch (Exception e){
			e.printStackTrace();
		}

	}
	public  void senmsgByweek(String key,int beforeDays) {
		/*3220bd0d-7415-475c-8116-0a7ac8deba5e*/
		String botUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + key;
		WeChatBotUtils weChatBot = new WeChatBotUtils(botUrl, true);
		TapdApiBusiness tapdApi = new TapdApiBusiness();
		List<ResultEntity> cmap = tapdApi.getbugsByweek("68331031", "1168331031001000392",beforeDays);
		List<ResultEntity> qudaomap = tapdApi.getbugsByweek("61792590", "1161792590001000446",beforeDays);
		List<ResultEntity> bbsmap = tapdApi.getbugsByweek("30892271", "1130892271001000738",beforeDays);
		StringBuffer cname=new StringBuffer();
		StringBuffer qudaoname=new StringBuffer();
		StringBuffer bbsname=new StringBuffer();

		if(cmap.size()>0){
			//处理1个大项目空间
			Map<String, List<ResultEntity>> corderGroupMap = cmap.stream().collect(Collectors.groupingBy(ResultEntity::getProjectName));
			Set cset = corderGroupMap.keySet();
			for (Object o : cset) {
				cname.append("    ><font color=\"info\">[线上报障_" + o + "]</font>" + corderGroupMap.get(o).size() + "个\n");
				cname.append("详情查看:\n");
				for(ResultEntity one:corderGroupMap.get(o)){
					cname.append(corderGroupMap.get(o).indexOf(one)+1 +") "+one.getUrl()+" \n");
				}
			}
		}
		if(qudaomap.size()>0){
			//处理2个大项目空间
			Map<String, List<ResultEntity>> qudaoorderGroupMap = qudaomap.stream().collect(Collectors.groupingBy(ResultEntity::getProjectName));
			Set qudaoset = qudaoorderGroupMap.keySet();
			for (Object o : qudaoset) {
				System.out.println(o+":");
				qudaoname.append("    ><font color=\"info\">[线上报障_" + o + "]</font>" + qudaoorderGroupMap.get(o).size() + "个\n");
				qudaoname.append("详情查看:\n");
				for(ResultEntity one:qudaoorderGroupMap.get(o)){
					qudaoname.append(qudaoorderGroupMap.get(o).indexOf(one)+1  +") "+one.getUrl()+" \n");
				}
			}
		}
		if(bbsmap.size()>0){
			//处理1个大项目空间
			Map<String, List<ResultEntity>> bbsorderGroupMap = bbsmap.stream().collect(Collectors.groupingBy(ResultEntity::getProjectName));
			Set bbsset = bbsorderGroupMap.keySet();
			for (Object o : bbsset) {
				System.out.println(o+":");
				bbsname.append("    ><font color=\"info\">[线上报障_" + o + "]</font>" + bbsorderGroupMap.get(o).size() + "个\n");
				bbsname.append("详情查看:\n");
				for(ResultEntity one:bbsorderGroupMap.get(o)){
					System.out.println(bbsorderGroupMap.get(o).indexOf(one));
					bbsname.append(bbsorderGroupMap.get(o).indexOf(one)+1 +") "+one.getUrl()+" \n");
				}
			}
		}

		try {
			int totalcount=cmap.size()+qudaomap.size()+bbsmap.size();
			if (totalcount>0){
				String markdownMsg =
						"[线上报障]近"+(beforeDays-1)+"天共有"+totalcount+"个已处理但未回复报障人员：\n" +cname.toString()
								+qudaoname.toString()+bbsname.toString();
				weChatBot.sendMarKDownMsg(markdownMsg);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {
		Qyapi qyapi=new Qyapi();
		qyapi.senmsgByday("3220bd0d-7415-475c-8116-0a7ac8deba5e",31);
		*//*qyapi.senmsgByweek("3220bd0d-7415-475c-8116-0a7ac8deba5e",31);
		TapdApiBusiness tapdApi = new TapdApiBusiness();
		List<ResultEntity> bbsmap = tapdApi.getbugsByweek("30892271", "1130892271001000738",11);*//*

	}
*/


}