<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/pages/common/taglibs.jsp" %> 
    <script type="text/javascript">
	    function validateQuery()
	    {
	    	var mobile = $("#mobile").val();

	       	if (!isEmpty(mobile)) {
	       		var bool = /^[0-9]{0,11}$/.test(mobile);
		       	if (bool == false) {
		           $("#tip_mobile").html("请输入正确的手机号码。");
		           $("#tip_mobile").show();
		           return false;
		       	}
	       	}	
	       	
	       	return true;
	    }
	    
	    $(document).ready(function(){
			$("#queryForList").click(function(){
				/**$("[id^='tip_']").hide();
				
		       	var tmp_bool = validateQuery();
		        if (tmp_bool != true) {
		            return false;
		        }*/
		        
		        $("#list-div").load(
					"${ctx}/pages/admin/pri/meeting/accessList.action",
					{
						"meetingAccessLog.meeting.id":$("#meetingId").val(),
						"meetingAccessLog.isDistinct":$("#isDistinct").val(),
						"meetingAccessLog.startTime":$("#startTime").val(),
						"meetingAccessLog.endTime":$("#endTime").val(),
						"totalPage":$("#totalPage").val(),
						"currentPage":$("#currentPage").val()
					}
				);
			});
		});
	    
	    jQuery(document).keypress(function(e){
	    	if(e.which == 13 ) {
	    		var act = document.activeElement.id;
	    		switch(act){
	    			case 'meetingId':
	    			//case 'mobile':
	    			case 'isDistinct':
	    			case 'startTime':
	    			case 'endTime':$('#queryForList').click();break;
	    			case 'jumpPage':jumpTo();break;
	    		}
	    	} 
	    })
	</script>

	<div class="page_tools page_serach">
	<form id="mainForm" action="${ctx}/pages/admin/pri/meeting/accessList.action">
		<input type="hidden" id="totalPage" name="totalPage" value="${pager.pageCount}"/>
        <input type="hidden" name="currentPage" id="currentPage" value="${pager.currentPage}"/>
		<table width="100%">
		<tr>
	      	<th>会 议 ID：</th>
	        <td><input type="text" id="meetingId" name="meetingAccessLog.meeting.id"
                                   value="${meetingAccessLog.meeting.id }" maxlength="11"/>
            <font id="tip_meetingId" style="display: none;" color="red"></font></td>
	        <th></th>
	        <td>
	        	<select class="inp_1" id="isDistinct" name="meetingAccessLog.isDistinct">
					<option value="0" <c:if test="${meetingAccessLog.isDistinct eq 0 }"> selected </c:if>>用户可以重复</option>
					<option value="1" <c:if test="${meetingAccessLog.isDistinct eq 1 }"> selected </c:if>>去掉用户重复</option>
				</select>
			</td> 
		</tr>
		<tr>
            <th>开始时间：</th>
	        <td><input type="text" id="startTime" name="meetingAccessLog.startTime"
                                   value="${meetingAccessLog.startTimeS }" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
            <font id="tip_startTime" style="display: none;" color="red"></font></td>
            <th>结束时间：</th>
	        <td><input type="text" id="endTime" name="meetingAccessLog.endTime"
                                   value="${meetingAccessLog.endTimeS }" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
            <font id="tip_endTime" style="display: none;" color="red"></font></td>
	        <td><a href="#" id="queryForList" class="btn_common btn_true">搜 索</a></td>
		</tr>
	    </table>
	</form>
	</div>

	<div>
		<table class="page_datalist">
	    	<thead>
	        	<tr>
	       
	            	<th width="10%">会议ID</th>
	                <th width="50%">会议名称</th>
	                <th width="20%" >登录方式</th>
	                <th width="20%" >登录次数</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                <c:choose>
					<c:when test="${not empty pager.pageRecords}">
					    <c:forEach var="loginLog" items="${pager.pageRecords}" varStatus="status">
					    <tr <c:if test="${status.count % 2 eq 0}"> class="even"</c:if>>
                            <td>${loginLog[3] }</td>
                            <td>${loginLog[0] }</td>
							<td>
								<c:choose>
	                               	<c:when test="${loginLog[1] eq 1}">web</c:when>
	                               	<c:when test="${loginLog[1] eq 2}">wap</c:when>
	                               	<c:when test="${loginLog[1] eq 3}">客户端</c:when>
	                               	<c:when test="${loginLog[1] eq 4}">touch</c:when>
                                	<c:otherwise> </c:otherwise>
                               	</c:choose>
                            </td>
							<td>${loginLog[2] }</td>
                        </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="datarow">
                            <td colspan="6">&nbsp;&nbsp;&nbsp;没有会议访问信息.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	            </tr>
	        </tbody>
	    </table>
	    <table class="page_datalist">
	    <thead><tr>
			<td ></td>
        	<th>总访问： <c:if test="${ totalSum == null}"> 0 </c:if>${ totalSum }</th>
			<th>web： <c:if test="${ webSum == null}"> 0 </c:if>${ webSum }</th>
			<th>wap： <c:if test="${ wapSum == null}"> 0 </c:if>${ wapSum }</th>
			<th>客户端： <c:if test="${ clientSum == null}"> 0 </c:if>${ clientSum }</th>
	    </tr></thead>
	    </table>
	    <%@ include file="/pages/common/page.jsp" %>
	</div>
