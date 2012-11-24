<%@ page language="java" import="java.util.*,bean.*,dao.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="common.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link href="${appPath}/images/common.css" rel="stylesheet"
			type="text/css" />
		<title>湿度监管</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<style>
html,body,h3,td {
	color: #fff;
	font-family: '宋体', Arial, Helvetica;
	font-weight: bold;
}

body {
	background: #000;
	padding: 0.5em 1em 0.5em 1em;
}


</style>
		<style type="text/css">
#dxsdcuo{
	position: absolute;
	left: 100px;
	top: 100px;
	z-index: 1;
	color: red;
	background-color: #30F;
}
#sejsdcuo{
	position: absolute;
	left: 200px;
	top: 100px;
	z-index: 1;
	color: red;
	background-color: #30F;
}
#sexsdcuo{
	position: absolute;
	left: 300px;
	top: 100px;
	z-index: 1;
	color: red;
	background-color: #30F;
}
#ss1sdcuo{
	position: absolute;
	left: 400px;
	top: 100px;
	z-index: 1;
	color: red;
	background-color: #30F;
}
#ss2sdcuo{
	position: absolute;
	left: 500px;
	top: 100px;
	z-index: 1;
	color: red;
	background-color: #30F;
}
#desdcuo{
	position: absolute;
	left: 600px;
	top: 100px;
	z-index: 1;
	color: red;
	background-color: #30F;
}
#essdcuo{
	position: absolute;
	left: 700px;
	top: 100px;
	z-index: 1;
	color: red;
	background-color: #30F;
}
</style>
		<script type="text/javascript" src="${appPath}/images/jquery.min.js"></script>
		<!-- 地下 -->
		<script type="text/javascript">
		   function dxsdshow(){
		     document.all.dxsdcuo.style.display="block";
		   }
		   function dxsdhidden(){
		     document.all.dxsdcuo.style.display="none";
		   }
		</script>
		<!-- 十二旧 -->
		<script type="text/javascript">
		   function sejsdshow(){
		     document.all.sejsdcuo.style.display="block";
		   }
		   function sejsdhidden(){
		     document.all.sejsdcuo.style.display="none";
		   }
		</script>
		<!-- 十二新 -->
		<script type="text/javascript">
		   function sexsdshow(){
		     document.all.sexsdcuo.style.display="block";
		   }
		   function sexsdhidden(){
		     document.all.sexsdcuo.style.display="none";
		   }
		</script>
		<!-- 十三楼 -->
		<script type="text/javascript">
		   function ss1sdshow(){
		     document.all.ss1sdcuo.style.display="block";
		   }
		   function ss1sdhidden(){
		     document.all.ss1sdcuo.style.display="none";
		   }
		</script>
		<!-- 十四楼 -->
		<script type="text/javascript">
		   function ss2sdshow(){
		     document.all.ss2sdcuo.style.display="block";
		   }
		   function ss2sdhidden(){
		     document.all.ss2sdcuo.style.display="none";
		   }
		</script>
		<!-- 第二办 -->
		<script type="text/javascript">
		   function desdshow(){
		     document.all.desdcuo.style.display="block";
		   }
		   function desdhidden(){
		     document.all.desdcuo.style.display="none";
		   }
		</script>
		<!--二十楼 -->
		<script type="text/javascript">
		   function essdshow(){
		     document.all.essdcuo.style.display="block";
		   }
		   function essdhidden(){
		     document.all.essdcuo.style.display="none";
		   }
		</script>
	</head>
	<body>
		<div id="dxsdcuo" style="display: none">
		<c:forEach items="${whcuoList}" var="dxcuosd">
							<c:if test="${dxcuosd.devname eq '地下一楼温湿度监测' }">
                                ${dxcuosd.ch} ${dxcuosd.agentname} ${dxcuosd.devname }<br />
        </c:if>
		</c:forEach>
		</div>
		
		<div id="sejsdcuo" style="display: none">
		<c:forEach items="${whcuoList}" var="sejsdcuo">
							<c:if test="${sejsdcuo.agentname eq '安徽电力芜湖路12楼(旧)机房' }">
                                ${sejsdcuo.ch} ${sejsdcuo.agentname} ${sejsdcuo.devname }<br />
        </c:if>
		</c:forEach>
		</div>
		
		<div id="sexsdcuo" style="display: none">
		<c:forEach items="${whcuoList}" var="sexsdcuo">
							<c:if test="${sexsdcuo.agentname eq '安徽电力芜湖路12楼(新)机房' }">
                                ${sexsdcuo.ch} ${sexsdcuo.agentname} ${sexsdcuo.devname }<br />
        </c:if>
		</c:forEach>
		</div>
		
		<div id="ss1sdcuo" style="display: none">
		<c:forEach items="${whcuoList}" var="ss1sdcuo">
							<c:if test="${ss1sdcuo.devname eq '十三层温湿度监测' }">
                                ${ss1sdcuo.ch} ${ss1sdcuo.agentname} ${ss1sdcuo.devname }<br />
        </c:if>
		</c:forEach>
		</div>
		
		<div id="ss2sdcuo" style="display: none">
		<c:forEach items="${whcuoList}" var="ss2sdcuo">
							<c:if test="${ss2sdcuo.devname eq '十四层温湿度监测' }">
                                ${ss2sdcuo.ch} ${ss2sdcuo.agentname} ${ss2sdcuo.devname }<br />
        </c:if>
		</c:forEach>
		</div>
		
		<div id="desdcuo" style="display: none">
		<c:forEach items="${hscuoList}" var="desdcuo">
							<c:if test="${desdcuo.agentname eq '安徽电力黄山路第二办公楼' }">
                                ${desdcuo.ch} ${desdcuo.agentname} ${desdcuo.devname }<br />
        </c:if>
		</c:forEach>
		</div>
		
		<div id="essdcuo" style="display: none">
		<c:forEach items="${hscuoList}" var="essdcuo">
							<c:if test="${essdcuo.agentname eq '安徽电力黄山路20楼机房' }">
                                ${essdcuo.ch} ${essdcuo.agentname} ${essdcuo.devname }<br />
        </c:if>
		</c:forEach>
		</div>
		<div>
			<table width="855" height="254" border="1">
				<tr>
					<td width="74">
						&nbsp;
					</td>
					<td colspan="5">
						<div align="center">
							芜湖路
						</div>
					</td>
					<td colspan="2">
						<div align="center">
							黄山路
						</div>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td width="71">
						地下一层
					</td>
					<td width="104">
						十二楼（旧）
					</td>
					<td width="110">
						十二楼（新）
					</td>
					<td width="99">
						十三楼
					</td>
					<td width="92">
						十四楼
					</td>
					<td width="115">
						第二办公室
					</td>
					<td width="138">
						二十楼
					</td>
				</tr>
				<tr>
					<td>
						湿度
					</td>
					<td>
						<!-- 地下一层湿度 -->
						<c:forEach items="${whcuoList}" var="dxcuosd">
							<c:if test="${dxcuosd.devname eq '地下一楼温湿度监测' }">
                               <c:if test="${dxcuosd.flag eq '1'}">
									<c:set var='no1' value="${dxcuosd.flag}"></c:set>
								</c:if>
								<c:if test="${dxcuosd.flag eq '0'}">
									<c:set var="yes1" value="${dxcuosd.flag}"></c:set>
								</c:if>
                            </c:if>
						</c:forEach>
						<c:set var="g1" value="${yes1+no1}"></c:set>
						<c:if test="${g1>0}">
							<img src="images/red34.gif" onmouseover="dxsdshow()"
								onmouseout="dxsdhidden()"/>
						</c:if>
						<c:if test="${g1<=0}">
							<img src="images/green34.gif" />
						</c:if>
						
					</td>

					<td>
						<!-- 十二楼（旧）湿度 -->
						<c:forEach items="${whcuoList}" var="sejcuosd">
							<c:if test="${sejcuosd.agentname eq '安徽电力芜湖路12楼(旧)机房' }">
                               <c:if test="${sejcuosd.flag eq '1'}">
									<c:set var='no2' value="${sejcuosd.flag}"></c:set>
								</c:if>
								<c:if test="${sejcuosd.flag eq '0'}">
									<c:set var="yes2" value="${sejcuosd.flag}"></c:set>
								</c:if>
                            </c:if>
						</c:forEach>
						<c:set var="g2" value="${yes2+no2}"></c:set>
						<c:if test="${g2>0}">
							<img src="images/red34.gif" onmouseover="sejsdshow()"
								onmouseout="sejsdhidden()"/>
						</c:if>
						<c:if test="${g2<=0}">
							<img src="images/green34.gif" />
						</c:if>
					</td>
					
					<td>
						<!-- 十二楼（新）湿度 -->
						<c:forEach items="${whcuoList}" var="sexcuosd">
							<c:if test="${sexcuosd.agentname eq '安徽电力芜湖路12楼(新)机房' }">
                               <c:if test="${sexcuosd.flag eq '1'}">
									<c:set var='no3' value="${sexcuosd.flag}"></c:set>
								</c:if>
								<c:if test="${sexcuosd.flag eq '0'}">
									<c:set var="yes3" value="${sexcuosd.flag}"></c:set>
								</c:if>
                            </c:if>
						</c:forEach>
						<c:set var="g3" value="${yes3+no3}"></c:set>
						<c:if test="${g3>0}">
							<img src="images/red34.gif" onmouseover="sexsdshow()"
								onmouseout="sexsdhidden()"/>
						</c:if>
						<c:if test="${g3<=0}">
							<img src="images/green34.gif" />
						</c:if>
					</td>
					<td>
						<!--十三楼湿度  -->
						
						<c:forEach items="${whcuoList}" var="ss1cuosd">
							<c:if test="${ss1cuosd.devname eq '十三层温湿度监测' }">
                               <c:if test="${ss1cuosd.flag eq '1'}">
									<c:set var='no4' value="${ss1cuosd.flag}"></c:set>
								</c:if>
								<c:if test="${ss1cuosd.flag eq '0'}">
									<c:set var="yes4" value="${ss1cuosd.flag}"></c:set>
								</c:if>
                            </c:if>
						</c:forEach>
						<c:set var="g4" value="${yes4+no4}"></c:set>
						<c:if test="${g4>0}">
							<img src="images/red34.gif" onmouseover="ss1sdshow()"
								onmouseout="ss1sdhidden()"/>
						</c:if>
						<c:if test="${g4<=0}">
							<img src="images/green34.gif" />
						</c:if>
					</td>
					<td>
						<!-- 十四楼湿度 -->
						<c:forEach items="${whcuoList}" var="ss2cuosd">
							<c:if test="${ss2cuosd.devname eq '十四层温湿度监测' }">
                               <c:if test="${ss2cuosd.flag eq '1'}">
									<c:set var='no5' value="${ss2cuosd.flag}"></c:set>
								</c:if>
								<c:if test="${ss2cuosd.flag eq '0'}">
									<c:set var="yes5" value="${ss2cuosd.flag}"></c:set>
								</c:if>
                            </c:if>
						</c:forEach>
						<c:set var="g5" value="${yes5+no5}"></c:set>
						<c:if test="${g5>0}">
							<img src="images/red34.gif" onmouseover="ss2sdshow()"
								onmouseout="ss2sdhidden()"/>
						</c:if>
						<c:if test="${g5<=0}">
							<img src="images/green34.gif" />
						</c:if>
					</td>
					<td>
						<!-- 第二办公室湿度 -->
						<c:forEach items="${hscuoList}" var="decuosd">
							<c:if test="${decuosd.agentname eq '安徽电力黄山路第二办公楼' }">
                               <c:if test="${decuosd.flag eq '1'}">
									<c:set var='no6' value="${decuosd.flag}"></c:set>
								</c:if>
								<c:if test="${decuosd.flag eq '0'}">
									<c:set var="yes6" value="${decuosd.flag}"></c:set>
								</c:if>
                            </c:if>
						</c:forEach>
						<c:set var="g6" value="${yes6+no6}"></c:set>
						<c:if test="${g6>0}">
							<img src="images/red34.gif" onmouseover="desdshow()"
								onmouseout="desdhidden()"/>
						</c:if>
						<c:if test="${g6<=0}">
							<img src="images/green34.gif" />
						</c:if>
					</td>
					<td>
						<!-- 二十楼湿度 -->
						<c:forEach items="${hscuoList}" var="escuosd">
							<c:if test="${escuosd.agentname eq '安徽电力黄山路20楼机房' }">
                               <c:if test="${escuosd.flag eq '1'}">
									<c:set var='no7' value="${escuosd.flag}"></c:set>
								</c:if>
								<c:if test="${escuosd.flag eq '0'}">
									<c:set var="yes7" value="${escuosd.flag}"></c:set>
								</c:if>
                            </c:if>
						</c:forEach>
						<c:set var="g7" value="${yes7+no7}"></c:set>
						<c:if test="${g7>0}">
							<img src="images/red34.gif" onmouseover="essdshow()"
								onmouseout="essdhidden()"/>
						</c:if>
						<c:if test="${g7<=0}">
							<img src="images/green34.gif" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						温度
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
				</tr>
				<tr>
					<td>
						空调
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
				</tr>
				<tr>
					<td>
						漏水
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						<img src="images/green34.gif" width="34" height="34">
					</td>
				</tr>
				<tr>
					<td height="38">
						消防
					</td>
					<td>
						&nbsp;
					</td>
					<td colspan="2">
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td colspan="2">
						<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
					<img src="images/green34.gif" width="34" height="34">
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>

		</div>
	</body>

</html>
