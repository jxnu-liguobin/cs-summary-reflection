	通过ID获取属性：		$("#ID")
	通过标签吗获取属性：	$("标签名")
	通过CLASS获取属性：	$(".样式名")   // class==样式名
	通过type获取  		$(":type名")
	
	获取所有的checkbox的元素  	$(":checkbox")     
			被选中的 			$(":checkbox:checked")    
			未选中的 			$(":checkbox:not(:checked)") 
	
	查找所有文本框   	$(":text")      
			密码框 $(":password")    
			单选按钮 $(":radio")    
			图片域 $(":images")   
			重置按钮 $(":reset") ...
			
	获取标签里的所有后代标签  
			$("form input")空格隔开      
			$("form input:first").text()  获取后代第一个标签的内容    last最后一个  odd奇数行 even偶数行
	        $("form input:first-child").text()    获取所有form标签的input标签的第一个元素 
	        $("form input:nth-child(?)").text()    获取所有form标签的input标签的第?个元素   ?可是odd、even、3n+1等
			$("ul li:only-child") 在ul中查找是唯一子元素的li元素内容
			
	获取标签里的所有子代标签  				$("form>input")空格隔开
	获取标签同一级后面的第一个标签    		$("form+input")
	获取标签同一级后面的所有标签     		$("form~input")
	获取索引号的内容  					$("table te:eq(1)").text()
	查找索引大于的内容   eq 改成 gt       小于：  gt 改成lt
	查找页面内所有标签为<h1><h2><h3>的标签  $(":header")
	查找包含文本"john"的div元素个数     	$("div:contains('john')")
	查找所有P元素为空的元素     			$("p:empty")
	查找所有含有子元素或者文本的P元素个数      $("p:parent")     // 空格或者不加元素不算在内
	查找所有含有ID属性的div元素    		$("div[id]")      
	查找ID有值的元素						$("div[id='**']")      
	属性值以什么开头						$("div[id^='**']")    以什么结尾 ^改成$    包含：*=
	设置元素隐藏  						$("tr:hidden")    可见:$(tr:visible)
	
	
	
	
	 方法：
	 创建一个标签  var $div=$("<div id='2015'>哈哈哈</div>")
	 把一个jquery加到一个元素里面
	 （后置）   jquery.append(jquery)
	 （前置）   jquery.prepend(jquery)
	 
	 同级---后置     	after()
	       前置      before()
	 获取属性值        attr()   // 括号里string
	 size()  		元素个数      
	 addClass() 	加已经定义好的一个CSS样式
	 css()  		加key-value形成的CSS样式
	 text（）  		获取标签之间的内容，能用于XML，强调的是文本内容，有子标签只会打印子标签的内容
	 attr()   		为元素设置MAP值，比如check中   .attr（"checked","true"）代表将这个值选中
	 each()   		遍历 ，里面可以价function     function中this代表遍历的东西
	 html()  		获取标签之间的内容	，不能用于XML，强调的是标签的内容，标签内有子标签也会显示出来
	 val()   		获取标签的值
	 remove()  		删除自己以及其后代节点
	 val()：			获取value属性的值
	 val("")：		设置value属性值为""空串，相当于清空
	 text()：		获取HTML或XML标签之间的值
	 text("")：		设置HTML或XML标签之间的值为""空串 
	 clone()：		只复制样式，不复制行为
	 clone(true)：	既复制样式，又复制行为
	 replaceWith()： 替代原来的节点
	 removeAttr()：	删除已存在的属性
	 addClass()：	增加已存在的样式
	 removeClass()： 删除已存在的样式
	 hasClass()：	判断标签是否有指定的样式，true表示有样式，false表示无样式
	 toggleClass()：	如果标签有样式就删除，否则增加样式
	 offset()：		获取对象的left和top坐标
	 offset({top:100,left:200})：将对象直接定位到指定的left和top坐标
	 
	 width()：			获取对象的宽
	    width(300)：		设置对象的宽
	    height()	：	获取对象的高
	    height(500)：	设置对象的高
	 children()：		只查询子节点，不含后代节点
	    next()：			下一下兄弟节点
	    prev()：			上一下兄弟节点
	    siblings()：		上下兄弟节点
	 show()：			显示对象
	    hide()：			隐藏对象
	 fadeIn()：			淡入显示对象
	    fadeOut()：		淡出隐藏对象
	 slideUp()：			向上滑动
	    slideDown()：	向下滑动
	    slideToggle()：	上下切换滑动，速度快点
	
	 serialize()   		序列化表单内容为字符串,用于Ajax请求。格式：var data = $(form).serialize();
	 serializeArray()   序列化表单元素(类似'.serialize()'方法)返回JSON数据结构数据。注意,此方法返回的是JSON对象而非JSON字符串。需要使用插件或者第三方库进行字符串化操作。
	 
	
	window.onload：在浏览器加载web页面时触发，可以写多次onload事件，但后者覆盖前者
	ready：在浏览器加载web页面时触发，可以写多次ready事件，不会后者覆盖前者，依次从上向下执行，我们常用$(函数)简化
	       ready和onload同时存在时，二者都会触发执行，ready快于onload
	       
	change：当内容改变时触发
	focus：焦点获取
	select：选中所有的文本值
	keyup/keydown/keypress：演示在IE和Firefox中获取event对象的不同
	mousemove：在指定区域中不断移动触发
	mouseover：鼠标移入时触发
	mouseout：鼠标移出时触发
	submit：在提交表单时触发，true表示提交到后台，false表示不提交到后台
	click：单击触发
	dblclick：双击触发
	blur：焦点失去
	
	
