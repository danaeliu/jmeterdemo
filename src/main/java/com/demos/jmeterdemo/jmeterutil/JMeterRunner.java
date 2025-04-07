package com.demos.jmeterdemo.jmeterutil;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class JMeterRunner {
    public static String runJMeter(String jmeterHome,String jmxFilePath,String reportDir) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        // JMeter安装目录
//        String jmeterHome = "D:/AWork/apache-jmeter-5.4.1"; // 请替换为你的JMeter安装路径
        // 确保路径中的jmeter是可执行文件
        String jmeterExecutable = "jmeter.bat";
        // JMeter脚本文件路径
//        String jmxFilePath = "D:/AWork/Watsons/jmter/autommp.jmx"; // 请替换为你的JMeter脚本文件路径
//        String baseName = jmxFilePath.substring(0, jmxFilePath.lastIndexOf(".jmx"));
        // HTML报告生成目录
        String reporthtmlDir = reportDir+timestamp; // 请替换为你希望生成HTML报告的目录
        String jtlFilePath = reporthtmlDir +  "/result.jtl";
        System.out.println(jtlFilePath);

        // 构建JMeter命令行参数
        ProcessBuilder pb = new ProcessBuilder(
                jmeterHome + "/bin/" + jmeterExecutable, // 使用正确的可执行文件名
                "-n", "-t", jmxFilePath, "-l", jtlFilePath, "-e", "-o", reporthtmlDir
        );

        // 设置JMeter的bin目录为工作目录
        pb.directory(new File(jmeterHome + "/bin"));

        try {
            // 启动JMeter进程
            Process process = pb.start();

            // 读取JMeter进程的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待JMeter进程完成
            int exitCode = process.waitFor();
            System.out.println("JMeter process exited with code: " + exitCode);

            if (exitCode == 0) {
                System.out.println("HTML report generated successfully. Open " + reportDir + "/index.html in your browser.");
                // 在此处可以添加发送邮件通知的代码
            } else {
                System.err.println("Failed to generate HTML report.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return jtlFilePath;
    }

    public static boolean checkAllTestsPassed(String jtlFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(jtlFilePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            boolean hasRecord = false;
            for (CSVRecord record : csvParser) {
                hasRecord = true;
                String successValue = record.get("success"); // 确保列名正确
                boolean isSuccess = Boolean.parseBoolean(successValue);
                if (!isSuccess) {
                    return false;
                }
            }
            // 如果没有记录，视为失败
            return hasRecord;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) throws IOException, CsvException {
        // JMeter安装目录
        String jmeterHome = "D:/AWork/apache-jmeter-5.4.1"; // 请替换为你的JMeter安装路径
        // JMeter脚本文件路径
        String jmxFilePath = "D:/AWork/Watsons/jmter/autommp.jmx"; // 请替换为你的JMeter脚本文件路径
        String reportDir = "D:/AWork/Watsons/jmter/report/";
        String jtlFilePath = runJMeter(jmeterHome, jmxFilePath,reportDir);
        System.out.println(jtlFilePath);
//         JtlReponseErrorEntity String= analyzeJtlFile(jtlFilePath);
//        Gson gson = new Gson();
//        System.out.println( gson.toJson(String));
    }
    public static JtlReponseErrorEntity analyzeJtlFile(String jtlFilePath) throws IOException, CsvException {
//        String  = "D:/AWork/Watsons/jmter/report/20250220_110224/result.jtl";
        ObjectMapper mapper = new ObjectMapper();
        JtlReponseErrorEntity jtlReponseErrorEntity = new JtlReponseErrorEntity();
        int errorCount = 0;
        int successCount = 0;
        List<JtlErrorEntity> jtlErrorEntityList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(jtlFilePath))) {
            // 跳过 CSV 头部（如 "timeStamp,elapsed,label,..."）
            List<String[]> rows = reader.readAll();
            boolean isHeaderSkipped = false;
            jtlReponseErrorEntity.setTotal(rows.size());
            for (String[] row : rows) {
                if (!isHeaderSkipped) {
                    isHeaderSkipped = true;
                    continue;
                }

                // 解析关键字段（根据 CSV 列顺序调整索引）
                String timestamp = row[0];       // 时间戳
                String elapsed = row[1];         // 耗时（毫秒）
                String label = row[2];           // 请求名称
                String responseCode = row[3];
                String responseMessage = row[4];  // 响应码
                String success = row[5];  // 是否成功

                // 判断是否失败（可根据需求扩展条件）
                if ("false".equals(success) || !"200".equals(responseCode)) {
                    errorCount ++;
                    JtlErrorEntity jtlErrorEntity = new JtlErrorEntity();
                    jtlErrorEntity.setRequesturl(label);
                    jtlErrorEntity.setResponsemsg(responseMessage);

                    System.out.println("失败请求详情:");
                    System.out.println("请求名称: " + label);
                    System.out.println("错误信息 " + responseMessage);
                    System.out.println("耗时: " + elapsed + " ms");
                    System.out.println("时间戳: " + timestamp);
                    System.out.println("----------------------");
                    jtlErrorEntityList.add(jtlErrorEntity);
                }
                successCount++;
            }
            jtlReponseErrorEntity.setFailtotal(errorCount);
            jtlReponseErrorEntity.setSuccesstotal(successCount);

            jtlReponseErrorEntity.setData(jtlErrorEntityList);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
//        String json = mapper.writeValueAsString(jtlReponseErrorEntity);

        return jtlReponseErrorEntity;
    }


}