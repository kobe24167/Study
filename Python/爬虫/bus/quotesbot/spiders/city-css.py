# -*- coding: utf-8 -*-
import scrapy
import re

class CityCSSSpider(scrapy.Spider):
    name = "city"
    alphabet = [chr(i) for i in range(97,123)]
    start_urls = [
        'http://m.checi.org/shikebiao/a/',
    ]
    page_num = 0
    def parse(self, response):
        for city in response.xpath('//a[@class="am-btn am-btn-primary am-radius am-btn-block"]').getall():
            print(city)
            yield {
                'name': city,
                'path': city,
                'en': city
            }
        CityCSSSpider.page_num += 1
        if CityCSSSpider.page_num < 26:
            next_page_url = 'http://m.checi.org/shikebiao/'+CityCSSSpider.alphabet[CityCSSSpider.page_num]+"/"
            yield scrapy.Request(response.urljoin(next_page_url))

