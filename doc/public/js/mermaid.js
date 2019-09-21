(function () {
  function loadStyle(url) {
    var link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = url;
    var head = document.getElementsByTagName('head')[0];
    head.appendChild(link);
  }
  function attr(node, attr, default_value) {
    return Number(node.getAttribute(attr)) || default_value;
  }
  // get user config
  var scripts = document.getElementsByTagName('script'),
    script = scripts[scripts.length - 1]; // 当前加载的script
  config = {
    stylesheet: attr(script, "stylesheet", "//cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.css"),
    js: attr(script, "js", "//cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"),
    markdown_expand: attr(script, "markdown_expand", "true"),
  };
  loadStyle(config.stylesheet);
  document.write('<script src="' + config.js + '"></script>');
  if (config.markdown_expand === "true") {
    for (var i = 0, x = document.getElementsByClassName("language-mermaid"); i < x.length; i++)
      x[i].setAttribute('class', 'mermaid');
    window.addEventListener("load", function (event) {
      mermaid.init();
    });
  }
})();
