---
  layout: null
---
  {% if site.PrismJS.enable %}
function loadStyle(url) {
  var link = document.createElement('link');
  link.rel = 'stylesheet';
  link.href = url;
  var head = document.getElementsByTagName('head')[0];
  head.appendChild(link);
}
(function () {
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
  loadJs('{{ site.PrismJS.src }}')
    .then(function () {
      loadStyle('{{ site.PrismJS.stylesheet }}');
      {% if site.PrismJS.plugins.line_numbers.enable %}
      loadStyle('{{ site.PrismJS.plugins.line_numbers.stylesheet }}');
      document.addEventListener("DOMContentLoaded", function () {
        for (var i = 0, x = document.getElementsByTagName("pre"); i < x.length; i++)
          x[i].classList.add('line-numbers');
      });
      loadJs('{{ site.PrismJS.plugins.line_numbers.src }}');
      {% endif %}

      {% if site.PrismJS.plugins.autoloader.enable %}
      loadJs('{{ site.PrismJS.plugins.autoloader.src }}').then(function () {
        Prism.plugins.autoloader.languages_path = '{{ site.PrismJS.plugins.autoloader.languages_path }}';
      })
      {% endif %}

      {% if site.PrismJS.plugins.toolbar.enable %}
      loadJs('{{ site.PrismJS.plugins.toolbar.src }}')
        .then(function () {
          loadStyle('{{ site.PrismJS.plugins.toolbar.stylesheet }}');
          {% if site.PrismJS.plugins.toolbar.select_code.enable %}
          Prism.plugins.toolbar.registerButton('select-code', function (env) {
            var button = document.createElement('button');
            button.innerHTML = '{{ site.PrismJS.plugins.toolbar.select_code.alert }}' + env.language;
            button.addEventListener('click', function () {
              // Source: http://stackoverflow.com/a/11128179/2757940
              if (document.body.createTextRange) { // ms
                var range = document.body.createTextRange();
                range.moveToElementText(env.element);
                range.select();
              } else if (window.getSelection) { // moz, opera, webkit
                var selection = window.getSelection();
                var range = document.createRange();
                range.selectNodeContents(env.element);
                selection.removeAllRanges();
                selection.addRange(range);
              }
            });
            return button;
          });
          {% endif %}
        })
      {% endif %}
    })
})();
{% endif %}
