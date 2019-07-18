# -*- coding: utf-8 -*-
import scrapy
import re

class CnnvdCSSSpider(scrapy.Spider):
    name = "cnnvd"
    start_urls = [
        'http://www.cnnvd.org.cn/web/vulnerability/querylist.tag',
    ]
    page_num = 0
    def parse(self, response):
        for item in response.css(".list_list li"):
            yield {
                'title': item.css(".fl").css('.a_title2').css('a::text').get().replace('\r\n                               \t\t  ','').replace('\t',''),
                'link': item.css(".fl").css('.a_title2').css('a::attr(href)').get(),
                'time': re.search('\d{4}-\d{2}-\d{2}', item.css(".fr").get()).group(),
                'level': item.css(".fr").css('img::attr(src)').get().replace('/web/images/jb_','').replace('.png','')
            }
        CnnvdCSSSpider.page_num += 1
        next_page_url = 'http://www.cnnvd.org.cn/web/vulnerability/querylist.tag?pageno='+str(CnnvdCSSSpider.page_num)
        if next_page_url is not None:
            yield scrapy.Request(response.urljoin(next_page_url))

