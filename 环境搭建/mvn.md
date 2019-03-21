# Maven 打包

  * 打包base依赖
```
mvn clean install 编译+打包 至target目录 然后安装到本地仓库
对本地工程打包，引用的工程
```
  * 打包
```
mvn clean package
```

  * 写成脚本形式，举例
```
@echo on

echo clean path
set filename=%date:~0,4%%date:~5,2%%date:~8,2%
set buildPath=D:\build\%filename%
set codePath=D:\SVN
rd /s /q %buildPath%
md %buildPath%

echo begin build
cd /d %codePath%\Rehab\
@call package.bat
cd /d %codePath%\RehabManage\
@call package.bat

echo copy file to %buildPath%
copy /Y %codePath%\Rehab\target\Rehab.war %buildPath%\Rehab.war
copy /Y %codePath%\RehabManage\target\RehabManage.war %buildPath%\RehabManage.war
copy /Y %codePath%\Rehab\target\Rehab.war %buildPath%\ROOT.war
copy /Y %codePath%\RehabManage\target\RehabManage.war %buildPath%\Manage.war

echo replace application.properties
7z d %buildPath%\ROOT.war WEB-INF\classes\application.properties
7z d %buildPath%\Manage.war WEB-INF\classes\application.properties
7z a %buildPath%\ROOT.war D:\build\ROOT\WEB-INF
7z a %buildPath%\Manage.war D:\build\Manage\WEB-INF

echo Build is done
explorer.exe D:\build\%filename%
pause
```