<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/portal/common/header.jsp" %>
<style type="text/css">
	.detail2_info {
	    -moz-border-bottom-colors: none;
	    -moz-border-image: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	    border-color: #004FB8 #CCCCCC #CCCCCC;
	    border-right: 1px solid #CCCCCC;
	    border-style: solid;
	    border-width: 2px 1px 1px;
	    height: 460px;
	    margin-bottom: 15px;
	    position: relative;
	    width: 948px;
    }
    .detail2_info_name {
	    border-bottom: 1px solid #CCCCCC;
	    height: 40px;
	    margin: 0 13px 10px;
	    overflow: hidden;
	    padding-bottom: 5px;
	    padding-top: 10px;
	    width: 730px;
	}
	
    .detail2_info_name .n {
	    font: bold 18px/22px "microsoft yahei",simsun;
	    padding-left: 22px;
	}
	
	.detail2_info_name h3 {
	    overflow: hidden;
	    width: 610px;
	}
	.detail2_info_address {
	    color: #666666;
	    margin: 0 14px 0 36px;
	    overflow: hidden;
	    width: 720px;
	}
	
	.layoutfix {
	    display: block;
	    overflow: hidden;
	}
	.pic_img {
	    height: 340px;
	    overflow: hidden;
	    width: 500px;
	    padding: 5px;
	}
	.detail2_intro {
	    background-color: #FFFFFF;
	    border: 1px solid #CCCCCC;
	    border-radius: 5px 5px 5px 5px;
	    margin-bottom: 20px;
	    padding: 14px;
	    width: 922px;
	}
	.detail2_intro_txt {
	    line-height: 24px;
	}
	.detail_extralist {
	    clear: both;
	    margin: 0 0 10px;
	}
</style>
	<div class="w960">
        <div class="cbox"><div class="title"><h5 id="caption">${param.menu_name}</h5></div></div>
	</div>
	
	<div class="container w960" style="min-height:300px;">
		<c:choose>
		<c:when test="${not empty hotelList}">
		<c:forEach var="hotelRecord" items="${hotelList}" varStatus="status">
			<div class="detail2_info">
				<div class="detail2_info_name">
					<h3 class="n hotel_goldmedal">${hotelRecord.name }</h3>
				</div>
				
				<ul class="detail2_info_address layoutfix">
				    <li>地址： ${hotelRecord.address }
					</li>
				    <li id="ctl00_MainContentPlaceHolder_liOpenTime">信息：${hotelRecord.baseInfo }
					</li>
				    <li id="ctl00_MainContentPlaceHolder_liSurroundings">周边：${hotelRecord.peripheralInfo }
				    </li> 
				 </ul>
				 
				<c:choose>
				<c:when test="${not empty hotelRecord.images}">
				<c:forEach var="imgRecord" items="${hotelRecord.images}" varStatus="status">
				<c:if test="${status.count == 1 }">
				<table width="100%">
					<tbody>
				        <tr>
				        	<td width="130">
					        </td>
					        <td width="40">
					        	<a><img onclick="changeImg('${hotelRecord.id}','0')" style="cursor:pointer" src="${ctx}/images/portal/last_pic.png"></a>
					        </td>
					        <td width="500">
								<div id="bImg" class="pic_img">
							 		<img id="hotelImg_${hotelRecord.id}" cur_index="1" width="480" height="320" title="${imgRecord.title }" src="${serverUrl}${imgRecord.address }" >
								</div>
					        </td>
					        <td width="250">
					        	<a><img onclick="changeImg('${hotelRecord.id}','1')" style="cursor:pointer" src="${ctx}/images/portal/next_pic.png"/></a>
					        </td>
				        </tr>
				    </tbody>
				</table>
				</c:if>
				</c:forEach>
				</c:when>
				</c:choose>
			</div>
			
			<div class="detail2_intro">  
				<div class="detail2_title">
				    <h3>酒店简介</h3>
				</div>
				<div class="detail_intro_box">
				    <div style="height:48px; overflow: hidden;" id="htlDes" class="detail2_intro_txt">
				        <span>　　${hotelRecord.introduction }</span>
				    </div>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" class="detail_extralist">
				    <tbody>
				        <tr>
				            <th width="60px">
				                <dfn>联系方式</dfn></th>
				            <td width="800px">
				                <span>${hotelRecord.linking }</span>
				            </td>
				        </tr>
				        <tr>
							<th>
				                <dfn>服务项目</dfn></th>
							<td>
				                <span>${hotelRecord.service }</span>
				            </td>
						</tr>
						<tr>
							<th>
				                <dfn>客房设施</dfn></th>
							<td>
				                <span>${hotelRecord.facilities }</span>
				            </td>
						</tr>
						<tr>
							<th>
				                <dfn>附加信息</dfn></th>
							<td>
				                <span>${hotelRecord.additionalInfo }</span>
				            </td>
						</tr>
				    </tbody>
				</table>
			</div>
		</c:forEach>
		</c:when>
		<c:otherwise>
			<tr class="datarow">
                <td colspan="6">&nbsp;&nbsp;暂未发布酒店信息。</td>
            </tr>
		</c:otherwise>
		</c:choose>
		
		<div class="clear"></div>
	</div>
<script type="text/javascript">
	
	function changeImg(hotelId,type) {
		var hotelImgName= "#hotelImg_" + hotelId;
		var imgIndex = $(hotelImgName).attr("cur_index");
		if (type == 0)
		{
			imgIndex = parseInt(imgIndex) - 1;
		}else {
			imgIndex = parseInt(imgIndex) + 1;
		}
		
		var url = "${ctx}/portal/pri/hotel/getHotelImage.action";
		var data = {
			"hotelId": hotelId,
			"imgIndex": imgIndex
		};
		
		$.post(url, data, changeImgCallback);
	}
	
	// 回调
	function changeImgCallback(data) {
		if(data.result == "true"){
			var hotelImgName = "#hotelImg_" + data.hotelId;
			$(hotelImgName).attr("title",data.title);
			$(hotelImgName).attr("src", "${serverUrl}" + data.address);
			$(hotelImgName).attr("cur_index", data.imgIndex);
		}
	}
</script>
<%@ include file="/pages/portal/common/footer.html" %>