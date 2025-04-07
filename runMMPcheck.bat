@echo off
setlocal enabledelayedexpansion

:: 配置参数
set JAR_PATH=D:\AWork\Watsons\jmter\mmp\jmeterdemo-0.0.1-SNAPSHOT.jar
set SERVICE_PORT=8082
set RUN_FILE=D:\AWork\Watsons\jmter\mmp\runlog\springbootlog
set LOG_FILE=D:\AWork\Watsons\jmter\mmp\runlog\responselog
echo ==============================================
echo 服务启动脚本 (v1.1) - %date% %time%
echo ==============================================

:: 1. 检查端口占用情况
netstat -ano | findstr :%SERVICE_PORT%| findstr LISTENING >nul
if errorlevel 1 (
    echo 端口 %SERVICE_PORT% 未被占用，可直接启动
) else (
    echo 端口 %SERVICE_PORT% 已被占用，正在终止冲突进程...
    for /f "tokens=5" %%i in ('netstat -ano ^| findstr :%SERVICE_PORT%^| findstr LISTENING') do (
        taskkill /PID %%i /F > %RUN_FILE% 2>&1
        if errorlevel 1 (
            echo 无法终止进程 %%i，请手动检查！
            exit /b 1
        )
    )
)

:: 2. 启动服务并记录日志
start /b cmd /c "java -jar %JAR_PATH% --server.port=%SERVICE_PORT% > %RUN_FILE% 2>&1"
echo 服务启动中... (请稍候)

:: 3. 循环验证服务状态（最多等待 30 秒）
set TIMEOUT=3
for /l %%t in (1,1,%TIMEOUT%) do (
    echo 正在验证服务状态... (%%t/%TIMEOUT%)
    timeout /t 2 >nul
    
    :: 发送 GET 请求验证服务
    curl -v --fail http://127.0.0.1:%SERVICE_PORT%/oktest > %LOG_FILE% 2>&1
	curl --connect-timeout 5 -s http://127.0.0.1:%SERVICE_PORT%/oktest > %LOG_FILE% 2>&1
    if errorlevel 1 (
        echo 服务启动失败，请检查日志文件：%RUN_FILE%
        exit /b 1
    ) else (
        echo 服务已就绪！
        break
    )
)

:: 4. 检查目标服务网络连通性（新增步骤）
echo ==============================================
echo 正在检查目标服务网络连通性 (10.44.2.227:8080)...
curl --connect-timeout 5 -s -o nul http://10.44.2.227:8080
if errorlevel 1 (
    echo 错误：无法连接到目标服务 10.44.2.227:8080！
    echo 请检查以下内容：
    echo 1. 目标服务是否正常运行
    echo 2. 本地网络是否通畅
    echo 3. 防火墙是否放行8080端口
    del payload.json
    exit /b 1
)
echo 网络连通性验证通过！
echo ==============================================

:: 5. 生成临时 JSON body入参文件（避免转义错误）
echo { > payload.json
echo    "key": "7c5ab994-12e5-4aa4-afb4-3dc81cf1ed27", >> payload.json
echo    "jmeterHome": "D:\\AWork\\apache-jmeter-5.4.1", >> payload.json
echo    "jmxFilePath": "D:\\AWork\\Watsons\\jmter\\mmp\\autommp.jmx", >> payload.json
echo    "reportDir": "D:\\AWork\\Watsons\\jmter\\mmp\\report\\" >> payload.json
echo } >> payload.json

:: 6. 输出 JSON 文件内容（调试用）
echo 生成的 JSON 内容如下：
type payload.json
echo ==============================================

:: 7. 发送请求并记录日志
curl -v -H "Content-Type: application/json" -d "@payload.json" http://127.0.0.1:%SERVICE_PORT%/mmpDayCheck/ > %LOG_FILE% 2>&1
del payload.json

:: 8. 检查结果
findstr /i "HTTP/1.1 200" %LOG_FILE% >nul
if %errorlevel% equ 0 (
    echo 请求成功！
) else (
    echo 请求失败，请检查日志 %LOG_FILE%。
)


:: 9. 输出最终结果
type %LOG_FILE%
pause >nul
exit /b 0