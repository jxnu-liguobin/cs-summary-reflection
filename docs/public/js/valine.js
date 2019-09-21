---
  layout: null
---
  (function () {
    document.write('<div id="valine"></div>');
    var loadJs = (function () {
      var script = document.createElement('script');
      if (script.readyState) {
        return function (url) {
          return new Promise(function (res, rej) {
            script = document.createElement('script');
            script.src = url;
            document.body.appendChild(script);
            script.onreadystatechange = function () {
              if (script.readyState == "loaded" ||
                script.readyState == "complete") {
                script.onreadystatechange = null; //解除引用
                res();
              }
            };
          })
        }
      } else {
        return function (url) {
          return new Promise(function (res, rej) {
            script = document.createElement('script');
            script.src = url;
            document.body.appendChild(script);
            script.onload = function () {
              res();
            };
          })
        }
      }
    })();
    loadJs('{{ site.valine.src }}')
      .then(function () {
        new Valine({
          appId: '{{ site.valine.appId }}',
          appKey: '{{ site.valine.appKey }}',
          avatar: '{{ site.valine.avatar }}',
          placeholder: '{{ site.valine.placeholder }}',
          notify: '{{ site.valine.notify }}',
          verify: '{{ site.valine.verify }}',
          highlight: '{{ site.valine.highlight }}',
          avatarForce: '{{ site.valine.avatarForce }}',
          visitor: '{{ site.valine.visitor }}',
          recordIP: '{{ site.valine.recordIP }}',
          el: '#valine'
        });
      });
  })();
