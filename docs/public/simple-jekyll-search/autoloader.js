---
layout: null
---
  document.write('<input id="search-input" placeholder="{{ site.sidebar.simple_jekyll_search.placeholder }}" />');
document.write('<div id="results-container"></div>');
document.write('<script src="{{ site.sidebar.simple_jekyll_search.src }}"></script>');
function loadStyle(url) {
  var link = document.createElement('link');
  link.rel = 'stylesheet';
  link.href = url;
  var head = document.getElementsByTagName('head')[0];
  head.appendChild(link);
}
loadStyle('{{ site.sidebar.simple_jekyll_search.stylesheet }}');
document.addEventListener("DOMContentLoaded", function () {
  SimpleJekyllSearch({
    json: '{{ site.sidebar.simple_jekyll_search.json }}',
    searchInput: document.getElementById('search-input'),
    resultsContainer: document.getElementById('results-container')
  });
});
