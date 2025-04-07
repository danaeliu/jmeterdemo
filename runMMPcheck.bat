@echo off
setlocal enabledelayedexpansion

:: ���ò���
set JAR_PATH=D:\AWork\Watsons\jmter\mmp\jmeterdemo-0.0.1-SNAPSHOT.jar
set SERVICE_PORT=8082
set RUN_FILE=D:\AWork\Watsons\jmter\mmp\runlog\springbootlog
set LOG_FILE=D:\AWork\Watsons\jmter\mmp\runlog\responselog
echo ==============================================
echo ���������ű� (v1.1) - %date% %time%
echo ==============================================

:: 1. ���˿�ռ�����
netstat -ano | findstr :%SERVICE_PORT%| findstr LISTENING >nul
if errorlevel 1 (
    echo �˿� %SERVICE_PORT% δ��ռ�ã���ֱ������
) else (
    echo �˿� %SERVICE_PORT% �ѱ�ռ�ã�������ֹ��ͻ����...
    for /f "tokens=5" %%i in ('netstat -ano ^| findstr :%SERVICE_PORT%^| findstr LISTENING') do (
        taskkill /PID %%i /F > %RUN_FILE% 2>&1
        if errorlevel 1 (
            echo �޷���ֹ���� %%i�����ֶ���飡
            exit /b 1
        )
    )
)

:: 2. �������񲢼�¼��־
start /b cmd /c "java -jar %JAR_PATH% --server.port=%SERVICE_PORT% > %RUN_FILE% 2>&1"
echo ����������... (���Ժ�)

:: 3. ѭ����֤����״̬�����ȴ� 30 �룩
set TIMEOUT=3
for /l %%t in (1,1,%TIMEOUT%) do (
    echo ������֤����״̬... (%%t/%TIMEOUT%)
    timeout /t 2 >nul
    
    :: ���� GET ������֤����
    curl -v --fail http://127.0.0.1:%SERVICE_PORT%/oktest > %LOG_FILE% 2>&1
	curl --connect-timeout 5 -s http://127.0.0.1:%SERVICE_PORT%/oktest > %LOG_FILE% 2>&1
    if errorlevel 1 (
        echo ��������ʧ�ܣ�������־�ļ���%RUN_FILE%
        exit /b 1
    ) else (
        echo �����Ѿ�����
        break
    )
)

:: 4. ���Ŀ�����������ͨ�ԣ��������裩
echo ==============================================
echo ���ڼ��Ŀ�����������ͨ�� (10.44.2.227:8080)...
curl --connect-timeout 5 -s -o nul http://10.44.2.227:8080
if errorlevel 1 (
    echo �����޷����ӵ�Ŀ����� 10.44.2.227:8080��
    echo �����������ݣ�
    echo 1. Ŀ������Ƿ���������
    echo 2. ���������Ƿ�ͨ��
    echo 3. ����ǽ�Ƿ����8080�˿�
    del payload.json
    exit /b 1
)
echo ������ͨ����֤ͨ����
echo ==============================================

:: 5. ������ʱ JSON body����ļ�������ת�����
echo { > payload.json
echo    "key": "7c5ab994-12e5-4aa4-afb4-3dc81cf1ed27", >> payload.json
echo    "jmeterHome": "D:\\AWork\\apache-jmeter-5.4.1", >> payload.json
echo    "jmxFilePath": "D:\\AWork\\Watsons\\jmter\\mmp\\autommp.jmx", >> payload.json
echo    "reportDir": "D:\\AWork\\Watsons\\jmter\\mmp\\report\\" >> payload.json
echo } >> payload.json

:: 6. ��� JSON �ļ����ݣ������ã�
echo ���ɵ� JSON �������£�
type payload.json
echo ==============================================

:: 7. �������󲢼�¼��־
curl -v -H "Content-Type: application/json" -d "@payload.json" http://127.0.0.1:%SERVICE_PORT%/mmpDayCheck/ > %LOG_FILE% 2>&1
del payload.json

:: 8. �����
findstr /i "HTTP/1.1 200" %LOG_FILE% >nul
if %errorlevel% equ 0 (
    echo ����ɹ���
) else (
    echo ����ʧ�ܣ�������־ %LOG_FILE%��
)


:: 9. ������ս��
type %LOG_FILE%
pause >nul
exit /b 0