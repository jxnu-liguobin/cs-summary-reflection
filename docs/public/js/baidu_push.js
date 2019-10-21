(function () {
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https') {
        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
    } else {
        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
    }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
})();

var _hmt = _hmt || [];
(function () {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?dd381827cc51b62781961a344a8c16ea";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
    var g = document.createElement("script");
    g.src = "https://ww.googletagmanager.com/gtag/js?id=UA-147390701-1";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(g, s);
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'UA-147390701-1');
})();
//百度爬虫被屏蔽了，码云免费版不支持自定义域名
