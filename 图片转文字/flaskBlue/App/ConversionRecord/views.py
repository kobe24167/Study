# -*- coding: utf-8 -*-

from App.ConversionRecord import conversionRecord  # 获取蓝图

from App.ConversionRecord.models import *  # 获取数据库模型对象和SQLAlchemy对象db，注意不可使用App模块中的db

from flask import request
import uuid

@conversionRecord.route('/')  # 设置路由
def index():  # 执行的方法
    return 'This Page Is Index'

@conversionRecord.route('/query')
def getConversionRecords():
    result = ConversionRecord.query.all()
    return json.dumps(result, cls=ConversionEncoder)

@conversionRecord.route('/add',methods=["GET","POST"])
def addConversionRecord():
    if request.method == "POST":
        # 以POST方式传参数，通过form取值
        # 如果Key之不存在，报错KeyError，返回400的页面
        path = request.form['path']
        user = request.form['user']
        print(user, ": ", path)
    else:
        # 以GET方式传参数，通过args取值
        path = request.args['path']
        user = request.args['user']
    cr = ConversionRecord(id=str(uuid.uuid1()), path=path, user=user)
    db.session.add(cr)
    db.session.commit()
    return 'success'

def addConversion(filepath,mime_type,user):
    cr = ConversionRecord(id=str(uuid.uuid1()), path=filepath, user=user, file_type=mime_type)
    cr.state = 0
    db.session.add(cr)
    db.session.commit()
    return 'success'