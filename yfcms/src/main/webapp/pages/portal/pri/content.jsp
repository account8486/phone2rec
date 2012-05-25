<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ include file="/pages/portal/common/header.jsp" %>
<style>
.cont strong{font-style:normal;font-weight:bold;}
</style> 
       <div class="w960">
           <div class="cbox">
                  <div class="title"><h5 id="caption">${content.contentTitle }</h5></div>
            </div>

            <div class="cont" style="min-height: 200px;">
            	${content.content }
            </div>
        </div>
           	
<%@ include file="/pages/portal/common/footer.html" %>