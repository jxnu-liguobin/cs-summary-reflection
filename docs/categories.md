---
layout: page
title: 分类
---
{% for category in site.categories reversed %}

##### {{ category[0] }}

{% for post in category[1] %}
- [{{ post.title }}]({{ post.url }}), <small>{{ post.date | date_to_string }}</small>
{% if post.description %}
    - <small>{{ post.description }}</small>
{% endif %}

{% endfor %}

{% endfor %}
