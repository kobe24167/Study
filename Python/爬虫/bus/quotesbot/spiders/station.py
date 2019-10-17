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

class StationCSSSpider(scrapy.Spider):
    engine = create_engine('mysql+mysqlconnector://root:Wincor2008@localhost:3306/bus')
    # 创建DBSession类型:
    DBSession = sessionmaker(bind=engine)
    session = DBSession()
    citys = session.query(City).all()
    cityLen = len(citys)
    print(cityLen)
    name = "station"
    start_urls = [
        'http://m.checi.org/city/laifeng/',
    ]
    page_num = 0
    def parse(self, response):
        for s in response.xpath('//a[@class="am-btn am-btn-primary am-radius am-btn-block"]').getall():
            print(s)
            path = s[s.index("href=\"")+6:s.index("/\">")+1]
            name = s[s.index(">")+1:s.index("<", 2)]
            yield {
                'name': name,
                'path': path,
                'en': "",
                'city':StationCSSSpider.citys[StationCSSSpider.page_num].en
            }
        StationCSSSpider.page_num += 1
        if StationCSSSpider.page_num < StationCSSSpider.cityLen:
            next_page_url = 'http://m.checi.org'+StationCSSSpider.citys[StationCSSSpider.page_num].path
            yield scrapy.Request(response.urljoin(next_page_url))

