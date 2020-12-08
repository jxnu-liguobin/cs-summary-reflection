---
layout: page
title: 归档
---
{% assign count = 1 %}
{% for post in site.posts reversed %}
    {% assign year = post.date | date: '%Y' %}
    {% assign nyear = post.next.date | date: '%Y' %}
    {% if year != nyear %}
        {% assign count = count | append: ', ' %}
        {% assign counts = counts | append: count %}
        {% assign count = 1 %}
    {% else %}
        {% assign count = count | plus: 1 %}
    {% endif %}
{% endfor %}

{% assign counts = counts | split: ', ' | reverse %}
{% assign i = 0 %}
{% assign currentDay = '01-01' %}

{% for post in site.posts %}
    {% assign year = post.date | date: '%Y' %}
    {% assign nyear = post.next.date | date: '%Y' %}
    {% if year != nyear %}  
      
#### {{ post.date | date: '%Y' }}（{{ counts[i] }}）

{:.archive-title}
        {% assign i = i | plus: 1 %}
    {% endif %}
{% assign currentDay = '01-01' %}
{% if currentDay == post.date | date: '%m-%d' %}
- <small>[{{ post.title }}]({{ post.url }} "{{ post.title }}"){:.archive-item-link}</small>
    {% if post.description %}
    - <small>{{ post.description }}</small>
    {% endif %}
{% else %}
{% assign currentDay = post.date | date: '%m-%d' %}
- {{ post.date | date: '%m-%d' }}
- <small>[{{ post.title }}]({{ post.url }} "{{ post.title }}"){:.archive-item-link}</small>
{% if post.description %}
    - <small>{{ post.description }}</small>
{% endif %}
{% endif %} 
{% endfor %}