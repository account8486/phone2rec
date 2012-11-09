<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/pages/touch/common/header.jsp" %>
	<ul class="tab n03">
        <li class="m01 act"><a href="javascript://"><span class="i00">会场建议</span></a></li>
       	<li id="li_home"><a href="javascript://"><span class="i01">首页</span></a></li>
        <li class="m03" id="li_nav"><a href="javascript://"><span class="i02">导航</span></a></li>
    </ul>
    
    <style type="text/css">
	.non_info {
		padding:10px;
		border: 1px solid #C1C1C1; 
		margin-bottom:300px;
		font-size:16px;
	}
	
	.header_info {
		font-size:25px;
		border:1px solid #C1C1C1;
		margin:15px;
		padding:10px;
		background:#F0F0F0;
		text-align: center;
		font-weight: bolder;
	}
	
	.specialty_info {
		border:1px dashed #666;
		margin:15px;
	}
	
	.specialty_info .specialty_image {
		float:right;
		margin:10px;
	}
	
	.specialty_info .specialty_name {
		font-weight:bold;
		font-size:16px;
		padding:5px;
	}
	
	.specialty_info .specialty_memo {
		padding-left: 10px;
	}
	
	.clear {
		float: none;
		clear: both;
	}
</style>
    
     <div class="tab_c" style="display:block;">
		<div class="header_info">标题：${linkPaper.title}</div>
	
		<c:forEach var="quest" items="${pager.pageRecords}" varStatus="status">
		<div class="specialty_info">
			<div class="specialty_name"><span>${status.count}:${quest.question }</span></div>
			
			<!-- 简答题 -->
			<c:if test="${quest.type==3}">
				<div class="quest message_sub" type="textarea" style="padding: 5px;">
					<span class="ipt" style="margin: 10px;margin-left: 15px;">
					<textarea rows="2" cols="30" name="contents" style="width:95%; height:100px; border:1px solid #ccc; outline:0px; padding:3px; border-radius:3px;" >${myMap[quest.id]}</textarea>
					</span>
			 	</div>
			</c:if>
			<!-- 单选题-->
			<c:if test="${quest.type==1}">
			 <div class="quest specialty_memo">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 10px;">
							<input type="radio" ${myMap[quest.id]==item.id?"checked":"" } name="checks_${quest.id}"/>
							<span>${item.content }</span>
						</div>
				</c:forEach>
			 </div>
			 </c:if>
			 
			 <!-- 多选题-->
			<c:if test="${quest.type==2}">
			 <div class="quest specialty_memo">
				<c:forEach items="${quest.items }" var="item" varStatus="sta">
						<div style="margin: 10px;">
							<input type="checkbox" ${myMap[item.id-10000]==item.id?"checked":"" } />
							<span>${item.content }</span>
						</div>
				</c:forEach>
			 </div>
			 </c:if>
			<div class="clear"></div> 
		</div>
		</c:forEach>
    
		
	
    	
        <p style="padding:0px 10px;">
            	<a href="${ctx }/touch/pri/paper/paper_findEnablePaper.action?meetingId=${linkPaper.meeting.id }" class="login_btn2">返回问卷列表</a>
        </p>
        
    </div>
<%@ include file="/pages/touch/common/footer.jsp" %>
<style type="text/css">
	/*这里是页面私有样式*/
	a.login_btn { height:40px; display:block; margin:10px 0px; margin-top:0px; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:10px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#fff; background:url(${ctx}/images/touch/btn_bg.png) top center repeat-x; border:1px solid #005078;}
	a.login_btn2 { height:40px; display:block;  margin:10px 0px; margin-top:0px; line-height:40px; text-align:center; font-weight:bold; font-size:20px; letter-spacing:10px;  -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; color:#333; background:url(${ctx}/images/touch/box_t_bg.png) top center repeat-x; border-left:1px solid #ccc;  border-right:1px solid #ccc;}
</style>

<script>
	/*这里是页面私有脚本*/
	$(function(){
		//$('ul.msg_list li:even').addClass('even');
		Li_even('ul.msg_list','odd');
	});

</script>