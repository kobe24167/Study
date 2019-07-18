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
class Bug(Base):
    # 表的名字:
    __tablename__ = 'bug'
    # 表的结构:
    id = Column(Integer(), primary_key=True)
    title = Column(String(255))
    link = Column(String(255))
    time = Column(String(255))
    level = Column(Integer())

# 初始化数据库连接:
engine = create_engine('mysql+mysqlconnector://root:1234@localhost:3306/study')
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)

class QuotesbotPipeline(object):
    def process_item(self, item, spider):
        session = DBSession()
        # 创建新User对象:
        bug = Bug(title=item['title'],link=item['link'],time=item['time'],level=item['level'])
        # 添加到session:
        session.add(bug)
        # 提交即保存到数据库:
        session.commit()
        # 关闭session:
        session.close()

        return item
