<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
<script type="text/javascript" src="${ctx }/js/touch/tbtouch.js"></script>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">酒店信息</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
<style type="text/css">
	.detail2_info {
	    -moz-border-bottom-colors: none;
	    -moz-border-image: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	    margin-left:10px;
	    position: relative;
    }
    .detail2_info_name {
	    margin: 0 13px 0px 0px;
	    overflow: hidden;
	    padding-bottom: 5px;
	    padding-top: 10px;
	}
	
    .detail2_info_name .n {
	    font: bold 18px/22px "microsoft yahei",simsun;
	    padding-left: 10px;
	}
	
	.detail2_info_name h3 {
	    overflow: hidden;
	}
	.detail2_info_address {
	    color: #666666;
	    margin: 0 14px 0 10px;
	    overflow: hidden;
	}
	
	.layoutfix {
	    display: block;
	    overflow: hidden;
	}
	.pic_img {
	    height: 200px;
	    overflow: hidden;
	    width: 300px;
	    padding: 5px;
	}
	.detail2_intro {
	    background-color: #FFFFFF;
	    border-radius: 5px 5px 5px 5px;
	    margin-left:10px;
	    margin-bottom: 10px;
	    padding: 0px 14px 0px 14px;
	}
	.detail2_intro_txt {
	    line-height: 24px;
	}
	.detail_extralist {
	    clear: both;
	}
</style>

<div class="tab_c" style="display:block;">
	<div class="boxa">
		<c:choose>
		<c:when test="${not empty hotelList}">
		<c:forEach var="hotelRecord" items="${hotelList}" varStatus="sta">
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
				 
				<c:if test="${not empty hotelRecord.images }">
				<div class="pic-widget-slide-v2" id="J_IndexSlider_${sta.count }">
					<div class="common-slide">
						<div class="list">
							<ul style="width: 1200px; left: 0px;" class="wrap">
							<c:forEach items="${hotelRecord.images}" var="imgRecord" varStatus="status">
								<c:if test="${status.count <= 5 }">
									<li style="left: 0px;" class="one-img">
										<img id="hotelImg_${hotelRecord.id}" width="300" height="100" title="${imgRecord.title }" src="${serverUrl}${imgRecord.address }" >
									</li>
								</c:if>
							</c:forEach>
							</ul>
						</div>
						<div class="mtbslide-pagination">
							<div id="prevBtn" class="prev pg-btn"><a>上一页</a></div> 
							<div class="trigs"> 
								<ul>
									<c:forEach items="${hotelRecord.images}" var="news" varStatus="status">
										<c:if test="${status.count <= 5 }">
											<li id="li${status.count }" <c:if test="${status.count == 1}"> class="cur"</c:if>>${status.count }</li>
										</c:if>
									</c:forEach>
								</ul> 
							</div> 
							<div id="nextBtn" class="next pg-btn"><a>下一页</a></div> 
						</div>
					</div>
				</div>
				</c:if>
			</div>
			
			<div class="detail2_intro">  
				<div class="detail2_title">
				    <h3>酒店简介</h3>
				</div>
				<div class="detail_intro_box">
				    <div style=" overflow: hidden;" id="htlDes" class="detail2_intro_txt">
				        <span>　　${hotelRecord.introduction }</span>
				    </div>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" class="detail_extralist">
				    <tbody>
				        <tr>
				            <th width="70px">
				                <dfn>联系方式</dfn></th>
				            <td width="300px">
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
</div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	.common-slide {
	    height: 140px;
	    margin: 0 auto;
	    padding: 10px;
	    position: relative;
	    width: 300px;
	}
	.common-slide .list {
	    height: 100px;
	    overflow: hidden;
	    position: relative;
	    width: 300px;
	}
	.common-slide .list ul {
	    position: absolute;
	}
	.common-slide .list li {
	    float: left;
	    height: 100px;
	    left: 0;
	    line-height: 100px;
	    position: relative;
	    width: 300px;
	}
	
	.common-slide .list li.one-img a {
	    height: 100px;
	    width: 300px;
	}
</style>
<script>
	$('.pic-widget-slide-v2').each(function(){
		$(this).slideLayer_v2({
			wrapEl : '.common-slide .list',
			slideEl : '.common-slide .list .wrap',
			childEl : '.common-slide .list li',
			counter : '.common-slide .trigs'
		});
	});
</script>