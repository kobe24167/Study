# -*- coding: utf-8 -*-
import scrapy
import re
from sqlalchemy import Column, String, create_engine, Integer
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# 创建对象的基类:
Base = declarative_base()
class Path(Base):
    # 表的名字:
    __tablename__ = 'path'
    # 表的结构:
    start = Column(String(255))
    to = Column(String(255))
    path = Column(String(255), primary_key=True)

class PathCSSSpider(scrapy.Spider):
    engine = create_engine('mysql+mysqlconnector://root:Wincor2008@localhost:3306/bus', pool_size=200, max_overflow=50)
    # 创建DBSession类型:
    DBSession = sessionmaker(bind=engine)
    session = DBSession()
    paths = session.query(Path).order_by(Path.path).offset(0).all()
    pathLen = len(paths)
    name = "timetable"
    start_urls = [
        'http://m.checi.org/city/abagaqi/baochang/',
    ]
    page_num = 0
    def parse(self, response):
        print("++++++++++++++++++++++")
        print(PathCSSSpider.page_num)
        for s in response.xpath('//div[@class="am-modal-dialog"]'):
            line_name = s.xpath('./div[@class="am-modal-hd"]/text()').extract_first()
            data = s.xpath('.//table[@class="am-table"]/tbody').extract_first()
            if line_name != None :
                yield {
                    'line_name': line_name,
                    'data': data,
                    'start':PathCSSSpider.paths[PathCSSSpider.page_num].start
                }

        PathCSSSpider.page_num += 1
        if PathCSSSpider.page_num < PathCSSSpider.pathLen:
            next_page_url = 'http://m.checi.org'+PathCSSSpider.paths[PathCSSSpider.page_num].path
            yield scrapy.Request(response.urljoin(next_page_url))

