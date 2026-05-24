@echo off
chcp 65001 >nul
echo ==========================================
echo   闲置交换平台 - 一键启动脚本
echo ==========================================

REM 检查 MySQL 是否运行
mysqladmin ping -h localhost -u root >nul 2>&1
if errorlevel 1 (
    echo [错误] MySQL 服务未运行，请先启动 MySQL
    pause
    exit /b 1
)

REM 初始化数据库
echo [1/4] 正在初始化数据库...
mysql -u root -p < docs\db\swap-platform.sql

REM 启动后端
echo [2/4] 正在启动后端服务...
start "Swap-Platform-Backend" cmd /c "mvnw.cmd spring-boot:run"

REM 等待后端启动
timeout /t 15 /nobreak >nul

REM 安装前端依赖
echo [3/4] 正在安装前端依赖...
cd swap-platform-ui
call npm.cmd install

REM 启动前端
echo [4/4] 正在启动前端服务...
start "Swap-Platform-Frontend" cmd /c "npm.cmd run dev"

echo.
echo ==========================================
echo   启动完成！
echo   后端文档：http://localhost:8912/doc.html
echo   前端页面：http://localhost:5173
echo ==========================================
pause
