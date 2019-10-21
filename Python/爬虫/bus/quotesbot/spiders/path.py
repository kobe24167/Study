# -*- coding: utf-8 -*-
import scrapy
import re
from sqlalchemy import Column, String, create_engine, Integer
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# 创建对象的基类:
Base = declarative_base()
class City(Base):
    # 表的名字:
    __tablename__ = 'city'
    # 表的结构:
    # id = Column(Integer(), primary_key=True)
    en = Column(String(255))
    name = Column(String(255), primary_key=True)
    path = Column(String(255))

class PathCSSSpider(scrapy.Spider):
    engine = create_engine('mysql+mysqlconnector://root:Wincor2008@localhost:3306/bus')
    # 创建DBSession类型:
    DBSession = sessionmaker(bind=engine)
    session = DBSession()
    citys = session.query(City).order_by(City.en).offset(2039).all()
    cityLen = len(citys)
    name = "path"
    start_urls = [
        'http://m.checi.org/city/abagaqi/',
    ]
    page_num = 0
    def parse(self, response):
        for s in response.xpath('//a[@class="am-btn am-btn-secondary am-radius am-btn-block"]').getall():
            print(PathCSSSpider.page_num)
            path = s[s.index("href=\"")+6:s.index("/\">")+1]
            name = s[s.index(">")+1:s.index("<", 2)]
            yield {
                'to': name,
                'path': path,
                'start':PathCSSSpider.citys[PathCSSSpider.page_num].name
            }
        PathCSSSpider.page_num += 1
        if PathCSSSpider.page_num < PathCSSSpider.cityLen:
            next_page_url = 'http://m.checi.org'+PathCSSSpider.citys[PathCSSSpider.page_num].path
            yield scrapy.Request(response.urljoin(next_page_url))

