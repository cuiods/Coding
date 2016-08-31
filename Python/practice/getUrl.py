from urllib2 import Request, urlopen, URLError, HTTPError

# ordUrl = Request('http://rrurl.cn/b1UZuP')
# rsponse = urlopen(ordUrl)
# print rsponse.geturl()

old_url = 'http://www.oschina.net/'
req = Request(old_url)
response = urlopen(req)
print 'Info():'
print response.info()
