#mysql数据库
```
字符集统一为utf8
alter database study character set utf8;
alter table conversion_record default character set utf8;
alter table conversion_record change value value longtext character set utf8;
```
#使用tesseract-ocr文字识别
```
安装
apt-get install tesseract-ocr
apt-get install tesseract-ocr-eng tesseract-ocr-chi-sim
文字识别字库路径
/usr/share/tesseract-ocr/tessdata
下载英文库
wget https://github.com/tesseract-ocr/tessdata/raw/master/eng.traineddata
配置环境变量/etc/profile
export TESSDATA_PREFIX=/usr/share/tesseract-ocr/tessdata
测试
tesseract --list-langs
tesseract test.png out -l eng
```
#python插件
```
安装pytesseract
pip install pytesseract
```
#python flask框架后台
```
pip install flask
pip install flask-sqlalchemy
[更多flask插件](https://github.com/humiaozuzu/awesome-flask)
flask工程路径
/home/code/flaskBlue/
flask启动
export FLASK_APP=manager.py
python3 -m flask run -p 8080 --host 0.0.0.0
后台启动，退出命令行不停服务器（nohup COMMAND &）
nohup python3 -m flask run -p 8080 --host 0.0.0.0 &
exit
```
#基于react的ant design控件
```
npm打包
npm run build
页面部署路径
/usr/share/nginx/html
插件
npm install --save react react-copy-to-clipboard
```



