# coding=utf-8
import urllib
import urllib2
import cookielib
import re
import string


class JwSpider:
    # 申明相关的属性
    def __init__(self):
        self.loginUrl = 'http://jwas2.nju.edu.cn:8080/jiaowu/login.do'  # 登录的url
        self.resultUrl = 'http://jwas2.nju.edu.cn:8080/jiaowu/student/studentinfo/achievementinfo.do?method=searchTermList&termCode='  # 显示成绩的url
        self.cookieJar = cookielib.LWPCookieJar()  # 初始化一个CookieJar来处理Cookie的信息
        self.opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(self.cookieJar))
        self.weights = []  # 存储权重，也就是学分
        self.points = []  # 存储分数，也就是成绩
        self.types = []   # 存储类型
        urllib2.install_opener(self.opener)
        self.initial_login()

    def initial_login(self):
        h = urllib2.urlopen("http://jwas2.nju.edu.cn:8080/jiaowu")
        # 初始化链接并且获取cookie
        headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) \
        Chrome/52.0.2743.82 Safari/537.36',
                   'Referer': 'http://jwas2.nju.edu.cn:8080/jiaowu/'}
        userName = raw_input("请输入用户名：")
        passWord = raw_input("请输入密码：")
        postData = urllib.urlencode(
            {'userName': userName, 'password': passWord, 'returnUrl': 'null'})  # POST的数据
        myRequest = urllib2.Request(url=self.loginUrl, data=postData, headers=headers)  # 自定义一个请求
        result = urllib2.urlopen(myRequest)  # 访问登录页面，获取到必须的cookie的值

    def get_scores(self):
        year = ['20141','20142','20151','20152']
        for term in year:
            print "===========================\n学期："+term
            thisWeight = []
            thisScore = []
            thisType = []
            myPage = urllib2.urlopen(self.resultUrl+term)
            pageData = myPage.read()
            myItems = re.findall('<tr align="left".*?class=("TABLE_TR_01"|"TABLE_TR_02").*?>.*?<td.*?<td.*?<td.*?>(.*?)</td>.*?<td.*?<td.*?>(.*?)</td>.*?<td.*?>(.*?)</td>.*?<td.*?<ul.*?>(.*?)</ul>.*?</tr>', pageData, re.S)
            for item in myItems:
                str = item[4]
                type = item[2]
                type = type.strip()
                self.weights.append(item[3])
                thisWeight.append(item[3])
                self.points.append(str.strip())
                thisScore.append(str.strip())
                self.types.append(type)
                thisType.append(type)
                print "课程:"+item[1]+" 类型："+ type +" 学分:"+item[3]+" 分数:"+str.strip()
            print term+"学期的学分绩是："+self.calculate_date(thisScore,thisWeight)
        print "====================================================="
        print "总学分绩："+self.calculate_date(self.points, self.weights)

    @staticmethod
    def calculate_date(points, weights):
        point = 0.0
        weight = 0.0
        for i in range(len(points)):
            point += string.atof(points[i]) * string.atof(weights[i])
            weight += string.atof(weights[i])
        return str(point / (weight*20))

spider = JwSpider()
spider.get_scores()