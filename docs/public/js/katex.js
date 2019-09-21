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
    stylesheet: attr(script, "stylesheet", "//cdn.jsdelivr.net/npm/katex/dist/katex.min.css"),
    js: attr(script, "js", "//cdn.jsdelivr.net/npm/katex/dist/katex.min.js"),
    auto_render: attr(script, "auto_render", "//cdn.jsdelivr.net/npm/katex/dist/contrib/auto-render.min.js"),
    delimiters: attr(script, "delimiters", "true"),
  };
  loadStyle(config.stylesheet);
  document.write('<script src="' + config.js + '"></script>');
  document.write('<script src="' + config.auto_render + '"></script>');
  if (config.delimiters === "true") {
    document.addEventListener("DOMContentLoaded", function () {
      renderMathInElement(document.body, {
        delimiters: [
          { left: "$$", right: "$$", display: true },
          { left: "$", right: "$", display: false }
        ]
      });
    });
  }
})();
