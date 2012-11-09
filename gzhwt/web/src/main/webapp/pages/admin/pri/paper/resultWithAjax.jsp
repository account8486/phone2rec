<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<div style="font-size: 15px;font-weight: bolder;">${question.question }</div>
<div id="lookVote"></div>
		
<script type="text/javascript">
	var size="${size}";
	var counts="${counts}";
	var contents="${contents}"
	counts=counts.slice(0,counts.length-1);
	contents=contents.slice(0,contents.length-1);
	counts=counts.split(",");
	contents=contents.split(",");
	var array=[]
	var colors=['#39c','#f00','#000','#E38','#b57','#888','#d95','#ad5']
	for(var i=0;i<size;i++){
		var data={"name":contents[i],"data":counts[i],"color":colors[i]};
		array.push(data);
	}
	lookVote(array,${linkPaper.count});
    
</script>
