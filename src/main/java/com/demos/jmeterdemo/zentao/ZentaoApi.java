package com.demos.jmeterdemo.zentao;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demos.jmeterdemo.twoutil.SendRuquest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import jdk.nashorn.internal.ir.BlockLexicalContext;
import okhttp3.Cookie;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZentaoApi {
    private static SendRuquest sendRuquest = new SendRuquest();
    private static  OkHttpExample okHt=new OkHttpExample();

    public static String getSessionId() {
        //调用禅道接口获取sessionID
        String session = sendRuquest.sendGet("http://zentao.pc.com.cn/api-getSessionID.json",null,null);

        // json解析器-GSON
        JsonParser parse = new JsonParser();

        //将接口返回的字符串解析成json格式
        JsonObject jsonSession = (JsonObject) parse.parse(session);

        //将json格式中的data解析出来
        JsonObject jsonObj = (JsonObject) parse.parse(jsonSession.get("data").getAsJsonPrimitive().getAsString());

        //获取key为sessionID的值
        String sessionID = jsonObj.get("sessionID").toString().replaceAll("\"","");
        return sessionID;
    }
    public static Boolean login(){
        boolean flag=false;
        String sessionId=getSessionId();
        String url="http://zentao.pc.com.cn/user-login-"+sessionId+".json?account=liujuan&password=4rfv%TGB";
        String result = okHt.sendGet(url);
//        System.out.println(result);
        if (result.contains("success")){
            flag=true;
            System.out.println("登陆成功");
        }else {
            System.out.println("登陆失败");
        }
        return flag;
    }

    public static String getCookies(){
        String zentaosid=null;
        String sessionId=getSessionId();
        String url="http://zentao.pc.com.cn/user-login-"+sessionId+".json?account=liujuan&password=4rfv%TGB";
        zentaosid = okHt.getCookie(url);
        return zentaosid;
    }

    public static List<Projects> getprojectsinfo(){
        List<Projects> allinfo=new ArrayList<>();
        List<Integer> mmpexecution=new ArrayList<>();
        mmpexecution.add(71);
        mmpexecution.add(72);
        mmpexecution.add(73);
        List<Integer> ooexecution=new ArrayList<>();
        ooexecution.add(36);
        ooexecution.add(37);
        ooexecution.add(38);
        List<Integer> efunexecution=new ArrayList<>();
        efunexecution.add(39);
        efunexecution.add(40);
        efunexecution.add(41);
        List<Integer> cbexecution=new ArrayList<>();
        cbexecution.add(42);
        cbexecution.add(43);
        cbexecution.add(44);
        List<Integer> nclickexecution=new ArrayList<>();
        nclickexecution.add(45);
        nclickexecution.add(46);
        nclickexecution.add(47);
        List<Integer> wsbexecution=new ArrayList<>();
        wsbexecution.add(139);
        wsbexecution.add(140);
        wsbexecution.add(141);

        List<Integer> giftexecution=new ArrayList<>();
        giftexecution.add(136);
        giftexecution.add(137);
        giftexecution.add(138);

        allinfo.add(new Projects("mmp",14,mmpexecution));
        allinfo.add(new Projects("o2o",15,ooexecution));
        allinfo.add(new Projects("efun",16,efunexecution));
        allinfo.add(new Projects("CB",17,cbexecution));
        allinfo.add(new Projects("nclick",18,nclickexecution));
        allinfo.add(new Projects("wsb",33,wsbexecution));
        allinfo.add(new Projects("gift",19,giftexecution));

        return allinfo;

    }
//    public  Map<String,List<ZentaoResult>> getBugs(String starttime,String endtime){
//        Map<String,List<ZentaoResult>> resultall=new HashMap<>();
//        List<Projects> projectsinfo=getprojectsinfo();
//        List<String> bugs=new ArrayList<>();
//        DayUtil dayUtil =new DayUtil();
//        String zentaosid = getCookies();
//        for (Projects project:projectsinfo){
//            List<ZentaoResult> ZentaoResults=new ArrayList<>();
//
//            String url="http://zentao.pc.com.cn/api.php/v1/products/"+project.getProjectId()+"/bugs?status=all&limit=200&zentaosid="+zentaosid;
//            String result = okHt.sendGet(url);
//            JSONObject resobj = JSON.parseObject(result);
//            JSONArray bugsobjs = resobj.getJSONArray("bugs");
//            if (bugsobjs.size()>0){
//                for (int i=0;i<bugsobjs.size();i++){
//                    JSONObject bugobj = bugsobjs.getJSONObject(i);
//                    String openedDate = bugobj.getString("openedDate");
//
//                    if (dayUtil.isinDay(openedDate,starttime,endtime)) {
//                        String status = bugobj.getString("status");
//                        if (status.equals("active")){
//                            String bugtitle = bugobj.getString("title");
//
//                            if (bugtitle.contains("【线上告警") || bugtitle.contains("【线上报障") || bugtitle.contains("【每日巡检")){
//                                System.out.println("11111111111111");
//                                String bugid = bugobj.getString("id");
//                                System.out.println("id==="+bugid);
//                                if(bugobj.getString("assignedTo")==null){
//                                    System.out.println("id不合规范==="+bugid);
//                                }else {
//                                    String realname = bugobj.getJSONObject("assignedTo").getString("realname");
//                                    String statusname = bugobj.getString("statusName");
//                                    String bugurl = "http://zentao.pc.com.cn/bug-view-"+bugid+".html";
//                                    ZentaoResult zentaoResult=new ZentaoResult(bugid,bugtitle,status,realname,statusname,openedDate,bugurl);
//
//                                    ZentaoResults.add(zentaoResult);
//                                }
//
//                            }
//
//                        }
//                    }else {
//                        System.out.println("非本周bug不统计");
//                    }
//                }
//
//
//            }
//            resultall.put(project.getProjectName(),ZentaoResults);
//
//
//        }
//
//
//
//        return resultall;
//
//    }
    public   String getBugs1(String starttime,String endtime){
        StringBuffer bugs=new StringBuffer();
        bugs.append("ID,标题,严重程序,优先级,状态,处理人,创建人,创建时间,解决时间,解决方案 \n");
        String zentaosid = getCookies();
        //线上告警
//        String gaojing_url="http://zentao.pc.com.cn/api.php/v1/products/53/bugs?status=all&limit=50&zentaosid="+zentaosid;
//        String bug_url="http://zentao.pc.com.cn/api.php/v1/products/50/bugs?status=all&limit=100&zentaosid="+zentaosid;

        DayUtil dayUtil =new DayUtil();
        List<ZentaoResult> ZentaoResults=new ArrayList<>();
        List<Integer> project_ids=new ArrayList<>();
        project_ids.add(50);
        project_ids.add(53);
        for (Integer id:project_ids){
            String url="http://zentao.pc.com.cn/api.php/v1/products/"+id+"/bugs?status=all&limit=50&zentaosid="+zentaosid;
            String result = okHt.sendGet(url);

            JSONObject resobj = JSON.parseObject(result);

            JSONArray bugsobjs = resobj.getJSONArray("bugs");
            if (bugsobjs.size()>0){
                for (int i=0;i<bugsobjs.size();i++){
                    JSONObject bugobj = bugsobjs.getJSONObject(i);
                    String openedDate = bugobj.getString("openedDate");
                    if (dayUtil.isinDay(openedDate,starttime,endtime)) {
                        String bugid = bugobj.getString("id");
//                        System.out.println(bugid);
                        String bugtitle = bugobj.getString("title");
                        String severity = bugobj.getString("severity");
                        String pri = bugobj.getString("pri");
                        String qarealname = bugobj.getJSONObject("openedBy").getString("realname");
                        String statusName = bugobj.getString("statusName");
                        String devrealname ="";
                        String resolvedDate ="";
                        String resolution ="";
                        if (statusName.equals("激活")){
                            devrealname = bugobj.getJSONObject("assignedTo").getString("realname");
                        }else {
                            devrealname = bugobj.getJSONObject("resolvedBy").getString("realname");
                            resolvedDate = bugobj.getString("resolvedDate");
                            resolution = bugobj.getString("resolution");
                        }

                        String bugurl = "http://zentao.pc.com.cn/bug-view-"+bugid+".html";
                        ZentaoResult zentaoResult=new ZentaoResult(bugid,bugtitle,severity,pri,statusName,qarealname,devrealname,openedDate,resolvedDate,resolution);
                        ZentaoResults.add(zentaoResult);
                        bugs.append(bugid+","+bugtitle+","+severity+","+pri+","+statusName+","+qarealname+","+devrealname+","+openedDate+","+resolvedDate+","+resolution+"\n");
                    }
                        }
                    }else {
                        System.out.println("非本周bug不统计");
                    }
                }
            bugs.append("O2O项目本周共新增："+ZentaoResults.size()+"个问题！");



        return bugs.toString();


    }
    public static void main(String[] args) throws IOException, InterruptedException {
        SendRuquest s=new SendRuquest();
        String resultstring =  s.sendGet("http://msc-o2o-uat.was.ink/mbeuat/apis/sys/common/get_captcha?_=a37240b.c343fb.2514","",null);
//        System.out.println(resultstring);
        JSONObject jsonObject=JSON.parseObject(resultstring);
        String captchabase64 = jsonObject.getJSONObject("result").getString("captcha");
        System.out.println(captchabase64);

//        // 假设base64编码的图片已经存储在一个字符串中
//        String base64Image = "你的Base64编码字符串";
//
//        // 将Base64字符串解码为图片文件
//        String tempImageFile = "D:\\AWork\\Watsons\\O2O\\2.0.14\\result.bmp"; // 假设是PNG格式
//        // 这里需要实现Base64字符串解码为图片的代码，具体实现依赖于你的环境
//
//        // 调用Tesseract进行OCR
//        String tesseractPath = "C:\\Program Files\\Tesseract-OCR\\tesseract.exe"; // Tesseract安装路径
//        String tempImagePath = new File(tempImageFile).getAbsolutePath();
//        String ocrOutputPath = "output";
//
//        ProcessBuilder pb = new ProcessBuilder(tesseractPath, tempImagePath, ocrOutputPath);
//        Process process = pb.start();
//        process.waitFor();
//
//        // 读取OCR输出结果
//        String ocrResult = new String(Files.readAllBytes(Paths.get(ocrOutputPath, "page.txt")));
//        System.out.println(ocrResult);
//
//        // 清理临时文件
//        new File(tempImageFile).delete();
//        new File(ocrOutputPath, "page.txt").delete();

        String key = UUID.fastUUID().toString(true);
        System.out.println(key);



    }



}
