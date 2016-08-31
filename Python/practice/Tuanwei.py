import urllib2

my_url = 'http://mp.weixin.qq.com/s?__biz=MzAwNDIxMTEwOQ==&mid=2652746631&idx=1&sn=51a95bae80bdfb4b7db2939205b21c82&scene=0#wechat_redirect'
for i in range(100):
    req = urllib2.Request(my_url)
    response = urllib2.urlopen(req)