from flask import Flask
from flask_bootstrap import Bootstrap
from flask_cors import *
import pymysql
from flask_sqlalchemy import SQLAlchemy
pymysql.install_as_MySQLdb()

db = SQLAlchemy()  # 初始化SQLAlchemy

def create_app():
    """ 创建app的方法 """
    app = Flask(__name__)  # 生成Flask对象
    app.config.from_pyfile('config.cfg')
    db.init_app(app=app)  # SQLAlchemy初始化App
    # db = SQLAlchemy(app)
    # 在这还可以设置好配置后， 初始化其他的模块
    CORS(app, supports_credentials=True)
    bootstrap = Bootstrap(app)
    return app  # 返回Flask对象app
