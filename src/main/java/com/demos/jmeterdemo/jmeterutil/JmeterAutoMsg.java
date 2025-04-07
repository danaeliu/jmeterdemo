package com.demos.jmeterdemo.jmeterutil;

import com.demos.jmeterdemo.twoutil.ResultEntity;
import com.demos.jmeterdemo.twoutil.TapdApiBusiness;
import com.demos.jmeterdemo.twoutil.WeChatBotUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class JmeterAutoMsg {
	private final Gson gson;

	public JmeterAutoMsg(Gson gson) {
		this.gson = gson;
	}

	/**
	 * （一） 待处理线上报障
	 */
	public  void mmpDayCheckResultSendMsg(String key,String jmeterHome,String jmxFilePath,String reportDir) throws IOException, CsvException {
		/*3220bd0d-7415-475c-8116-0a7ac8deba5e*/
		String botUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + key;
		WeChatBotUtils weChatBot = new WeChatBotUtils(botUrl, true);
		JMeterRunner jmeterRunner = new JMeterRunner();
//		String markdownMsg = "";
//		// JMeter安装目录
//		String jmeterHome = "D:/AWork/apache-jmeter-5.4.1"; // 请替换为你的JMeter安装路径
//		// JMeter脚本文件路径
//		String jmxFilePath = "D:/AWork/Watsons/jmter/autommp.jmx"; // 请替换为你的JMeter脚本文件路径
//		String reportDir = "D:/AWork/Watsons/jmter/report/";
		String jtlFilePath = jmeterRunner.runJMeter(jmeterHome, jmxFilePath, reportDir);
		JtlReponseErrorEntity jtlReponseErrorEntity = jmeterRunner.analyzeJtlFile(jtlFilePath);
		int totalcount = jtlReponseErrorEntity.getTotal();
		int successtotal = jtlReponseErrorEntity.getSuccesstotal();
		int failtotal = jtlReponseErrorEntity.getFailtotal();
		StringBuilder markdownMsg = new StringBuilder();

		List<JtlErrorEntity> data = jtlReponseErrorEntity.getData();
		ObjectMapper mapper = new ObjectMapper();
		//如果全部成功
		if (failtotal == 0) {
			markdownMsg.append("### [MMP-PRD接口每日巡检]: "+"<font color=\"green\">测试通过</font>\n")
					.append("共巡检" +"<font color=\"blue\">"+ totalcount + "</font>个接口");

		} else {
			markdownMsg .append("### [MMP-PRD接口每日巡检]: "+"<font color=\"red\">测试不通过</font>\n")
					.append("共巡检" +"<font color=\"blue\">"+ totalcount + "</font>个接口\n")
					.append("失败：<font color=\"red\">" + failtotal + "个</font> \n")
					.append("<font color=\"red\">详情 "+ "</font> \n")
					.append( " ```json \n" +mapper.writeValueAsString(data));
		}
		try {
			weChatBot.sendMarKDownMsg(markdownMsg.toString());
		} catch (Exception e) {
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
