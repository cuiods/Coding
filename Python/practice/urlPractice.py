# coding=utf-8
import urllib
import urllib2

url = 'http://www.someserver.com/register.cgi'

values = {'name': 'WHY',
          'location': 'SDU',
          'language': 'Python'}

data = urllib.urlencode(values)  # 编码工作
req = urllib2.Request(url, data)  # 发送请求同时传data表单
response = urllib2.urlopen(req)  # 接受反馈的信息
the_page = response.read()  # 读取反馈的内容

data = {}
data['name'] = 'WHY'
data['location'] = 'SDU'
data['language'] = 'Python'

url_values = urllib.urlencode(data)
print url_values

# name = Somebody + Here & language = Python & location = Northampton
url = 'http://www.example.com/example.cgi'
full_url = url + '?' + url_values

data = urllib2.urlopen(full_url)
