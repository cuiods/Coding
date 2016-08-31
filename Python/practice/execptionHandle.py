from urllib2 import Request, urlopen, URLError, HTTPError
import urllib2


headers = {
    'User-Agent':'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6',
    'Referer':'http://www.oschina.net/'
}
req = urllib2.Request(
    url = 'http://www.oschina.net/',
    headers = headers
)

try:

    response = urlopen(req)

except URLError, e:

    if hasattr(e, 'code'):

        print 'The server couldn\'t fulfill the request.'

        print 'Error code: ', e.code

    elif hasattr(e, 'reason'):

        print 'We failed to reach a server.'

        print 'Reason: ', e.reason


else:
    print 'No exception was raised.'
    print response.read()
    # everything is fine
