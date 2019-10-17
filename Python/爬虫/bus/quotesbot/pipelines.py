# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

from sqlalchemy import Column, String, create_engine, Integer
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# 创建对象的基类:
Base = declarative_base()

# 定义User对象:
class City(Base):
    # 表的名字:
    __tablename__ = 'city'
    # 表的结构:
    # id = Column(Integer(), primary_key=True)
    en = Column(String(255))
    name = Column(String(255), primary_key=True)
    path = Column(String(255))

# 初始化数据库连接:
engine = create_engine('mysql+mysqlconnector://root:Wincor2008@localhost:3306/bus')
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)

class CityPipeline(object):

    def process_item(self, item, spider):
        session = DBSession()
        # 创建新User对象:
        city = City(en=item['en'],name=item['name'],path=item['path'])
        # 添加到session:
        session.add(city)
        # 提交即保存到数据库:
        session.commit()
        # 关闭session:
        session.close()

        return item

class Station(Base):
    # 表的名字:
    __tablename__ = 'station'
    # 表的结构:
    en = Column(String(255))
    name = Column(String(255), primary_key=True)
    path = Column(String(255))
    city = Column(String(255))

class StationPipeline(object):

    def process_item(self, item, spider):
        session = DBSession()
        # 创建新User对象:
        station = Station(en=item['en'],name=item['name'],path=item['path'],city=item['city'])
        # 添加到session:
        session.add(station)
        # 提交即保存到数据库:
        session.commit()
        # 关闭session:
        session.close()

        return item
