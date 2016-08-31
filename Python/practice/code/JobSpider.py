# coding=utf-8
import urllib
import urllib2
import cookielib
import re
import string

'''
e.g:
    spider = JobSpider()
    spider.search_logic("数据",'data')
创建类后调用search_logic方法，第一个参数为搜索的关键词，第二个参数为保存文件名前缀
完整跑完的时间非常长，
'''


class JobSpider:

    # 设置初始参数
    def __init__(self):
        self.originUrl = 'http://www.51job.com'
        self.headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) \
        Chrome/52.0.2743.82 Safari/537.36',
                   'Referer': self.originUrl}
        self.cookieJar = cookielib.LWPCookieJar()  # 初始化一个CookieJar来处理Cookie的信息
        self.opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(self.cookieJar))
        self.searchHead = 'http://search.51job.com/list/000000,000000,0000,00,9,99,'
        self.searchEnd = ',2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare='
        self.pageHead = 'http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=000000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword='
        self.pageMiddle = '&keywordtype=2&curr_page='
        self.pageEnd = '&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9'

    '''
    总体方法调用逻辑
    key_word 搜索关键词
    file_name 文件名前缀
    is_chinese 搜索关键词是否为中文
    '''
    def search_logic(self,key_word, file_name, is_chinese=True):
        # 获取搜索结果页数
        total_page = self.get_total_page(self.do_initial_search(key_word,is_chinese))
        # 以追加方法打开文件，不缓冲防止内存溢出
        file_obj = open(file_name+'.txt','w+',0)
        # 一页一页读取
        for i in range(1, total_page+1):
            # 获取第i页
            page = self.page_result(key_word, i, is_chinese)
            items = self.find_list(page)
            # 解析第i页的所有结果
            for j in range(len(items)):
                item = items[j]
                # 找到第i页第j个结果的工作描述
                des_str = self.find_description(item[0])
                item.append(des_str)
                # 写入文件
                file_obj.write(item[0]+' '+item[1]+' '+item[2]+' '+item[3]+'\n')
                print 'about '+str(round((((i-1)*50+j)*1.0/(total_page*50))*100,3)) + '% completed...'
        # 关闭文件
        file_obj.close()

    '''
    初始搜索结果获取cookie
    '''
    def do_initial_search(self, key_word, is_chinese=True):
        code = key_word
        # 如果是中文 51job设置的是两次url编码
        if is_chinese:
            code = urllib.quote(urllib.quote(key_word))
        search_url = self.searchHead + code + self.searchEnd
        request = urllib2.Request(search_url)
        return urllib2.urlopen(request).read().decode('gbk')

    '''
    获取第page_number页的内容
    keyword 搜索关键词
    is_chinese 搜索关键词是否为中文
    '''
    def page_result(self, keyword, page_number=1, is_chinese=True):
        code = keyword
        # 在分页的时候51job又把中文变成了一次url编码
        if is_chinese:
            code = urllib.quote(keyword)
        page_url = self.pageHead+code+self.pageMiddle+str(page_number)+self.pageEnd
        result = urllib2.urlopen(urllib2.Request(page_url))
        # 从html头可以知道51job是gbk编码
        return result.read().decode('gbk')

    '''
    获取总页数
    page：已经获取的完整一页列表的内容
    '''
    def get_total_page(self, page):
        items = re.findall('<span class="td">(.*?)</span>.*?<input id="jump_page" class="mytxt" type="text"',page,re.S)
        # 中文解码
        item = urllib.unquote(items[0])
        return string.atoi(item[1:-4])

    '''
    获取一页的工作列表信息
    page：已经取出的一页列表
    '''
    def find_list(self, page):
        items = re.findall('<p class="t1 ">.*?<span.*?<a.*?href="(.*?)" onmousedown="">(.*?)</a>.*?<span class="t2"><a.*?>(.*?)</a>',page,re.S)
        result_items = []
        for item in items:
            result_item = []
            for word in item:
                word = word.strip().encode('utf-8').replace('\n','')
                result_item.append(word)
            result_items.append(result_item)
        return result_items

    '''
    根据取出的url获得该工作的描述信息
    '''
    def find_description(self,des_url):
        page = urllib2.urlopen(urllib2.Request(des_url)).read().decode('gbk')
        items = re.findall('<div class="tBorderTop_box">.*?<div class="bmsg job_msg inbox">.*?</span>(.*?)<div',page,re.S)
        result =  items[0].encode('utf-8').strip().replace('\t','').replace('\n','')
        # 删除描述信息中的html标签
        dr = re.compile(r'<[^>]+>', re.S)
        result = dr.sub('',result)
        return result


if __name__ == "__main__":
    spider = JobSpider()
    spider.search_logic("数据",'data')

