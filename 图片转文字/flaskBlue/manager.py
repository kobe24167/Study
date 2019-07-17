# -*- coding: utf-8 -*-

from flask_script import Manager

from App import create_app
from App.ConversionRecord import conversionRecord
from App.UploadFile import uploadFile
from App.Operation import operation

app = create_app()  # 创建app
app.register_blueprint(conversionRecord, url_prefix='/conversionRecord')  # 注册蓝图
app.register_blueprint(uploadFile, url_prefix='/uploadFile')  # 注册蓝图
app.register_blueprint(operation, url_prefix='/operation')  # 注册蓝图
manager = Manager(app)  # 通过app创建manager对象

if __name__ == '__mian__':
    manager.run()  # 运行服务器