<%@ page contentType="text/html;charset=utf-8"%>
<%@ page
	import="org.jfree.chart.*, 
org.jfree.chart.plot.PiePlot, 
org.jfree.data.general.DefaultPieDataset, 
org.jfree.chart.servlet.ServletUtilities, 
org.jfree.chart.title.TextTitle,
org.jfree.chart.plot.*,
java.awt.*"%>
<%
	//设置数据集 
	DefaultPieDataset dataset = new DefaultPieDataset();
	dataset.setValue("初中高级程序员", 8);
	dataset.setValue("项目经理", 8);
	dataset.setValue("系统分析师", 8);
	dataset.setValue("软件架构师", 8);
	dataset.setValue("其他111", 8);
	//通过工厂类生成JFreeChart对象 
	JFreeChart chart = ChartFactory.createPieChart3D("IT行业职业分布图",
			dataset, true, false, false);
	
	TextTitle textTitle = chart.getTitle();
	textTitle.setFont(new Font("黑体",Font.BOLD,15));
	
	PiePlot pieplot = (PiePlot) chart.getPlot();
	pieplot.setLabelFont(new Font("宋体", 0, 12));
	
	//没有数据的时候显示的内容 
	pieplot.setNoDataMessage("无数据显示");
	pieplot.setCircular(false);
	pieplot.setLabelGap(0.02D);
	
	chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,15));
	//pieplot.getLegendItems().
	//XYPlot  xyPlot=(XYPlot)chart.getPlot();
	//xyPlot.getRangeAxis().setLabelFont(new Font("黑体",Font.BOLD,15));
	
	String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300,
			null, session);
	String graphURL = request.getContextPath()
			+ "/DisplayChart?filename=" + filename;
%>
<img src="<%=graphURL%>" width=500 height=300 border=0
	usemap="#<%= filename %>">
