普通java无框架变json：
      导包：JSON文件夹
      把domain层实例化，然后 用代码    JSONArray x=JSONArray.fromobject(实例化之后的domain);
                                        有一个方法是.toString(); 可以让X变成java格式的字符串
三级联动复选框代码： 
        jsp层
  <head>
    <title>省份-城市-区域三级联动【Struts2 + JSON版】</title>
    <script type="text/javascript" src="js/ajax.js"></script>
  </head>
  <body>
	
	<select id="provinceID" style="width:111px">
		<option>选择省份</option>
		<option>湖北</option>
		<option>湖南</option>
		<option>广东</option>
	</select>
	
	<select id="cityID" style="width:111px">
		<option>选择城市</option>
	</select>
	
	<select id="areaID" style="width:111px">
		<option>选择区域</option>
	</select>
	
	
	<!-- 省份->城市 -->
	<script type="text/javascript">
		document.getElementById("provinceID").onchange = function(){
			// 清空城市下拉框
			var cityElement = document.getElementById("cityID");
			cityElement.options.length = 1;
			// 清空区域下拉框
			var areaElement = document.getElementById("areaID");
			areaElement.options.length = 1;
			
			var province = this[this.selectedIndex].innerHTML;
			if("选择省份" != province){
				// NO1)
				var ajax = createAJAX();
				// NO2)
				var method = "POST";
				var url = "${pageContext.request.contextPath}/findCityByProvinceRequest?time="+new Date().getTime();
				ajax.open(method,url);
				// NO3)
				ajax.setRequestHeader("content-type","application/x-www-form-urlencoded")
				// NO4)
				var content = "bean.province=" + province;
				ajax.send(content);
				
				// -------------------------------------------等待
				
				// NO5
				ajax.onreadystatechange = function(){
					if(ajax.readyState == 4){
						if(ajax.status == 200){
							// NO6)返回JAVA格式的JSON文本
							var jsonJAVA = ajax.responseText;
							
							// p所代表的是java格式的json文本，是不能直接被js执行的
							// 解决方案：将java格式的json文本，转成js格式的json文本
							// 如何做：用js提供的一个函数搞定
							var jsonJS = eval("("+jsonJAVA+")");
							
							var array = jsonJS.cityList;
							var size = array.length;
							for(var i=0;i<size;i++){
								var city = array[i];
								var option = document.createElement("option");
								option.innerHTML = city;
								cityElement.appendChild(option);
							}
						}
					}			
				}
			}
		}
	</script>

	
	<!-- 城市->区域 -->
	<script type="text/javascript">
		document.getElementById("cityID").onchange = function(){
			var areaElement = document.getElementById("areaID");
			areaElement.options.length = 1;
			var city = this[this.selectedIndex].innerHTML;
			if("选择城市" != city){
				// NO1)
				var ajax = createAJAX();
				// NO2)
				var method = "POST";
				var url = "${pageContext.request.contextPath}/findAreaByCityRequest?time="+new Date().getTime();
				ajax.open(method,url);
				// NO3)
				ajax.setRequestHeader("content-type","application/x-www-form-urlencoded")
				// NO4)
				var content = "bean.city=" + city;
				ajax.send(content);
				
				// ------------------------------------------等待
				
				// NO5)
				ajax.onreadystatechange = function(){
					
					if(ajax.readyState == 4){
						if(ajax.status == 200){	
							// NO6)
							var jsonJAVA = ajax.responseText;
							var jsonJS = eval("("+jsonJAVA+")");
							var array = jsonJS.areaList;
							var size = array.length;
							for(var i=0;i<size;i++){
								var area = array[i];
								var option = document.createElement("option");
								option.innerHTML = area;
								areaElement.appendChild(option);
							}
						}
					}
				}
				
			}
		}
	</script>
	
  </body>


  bean层：
  public class Bean {
	private String province;
	private String city;
	public Bean(){}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
