<%@ page contentType="text/html;charset=gb2312"%>

<%@ page import="org.jfree.chart.ChartFactory, 
org.jfree.chart.JFreeChart, 
org.jfree.chart.plot.PlotOrientation, 
org.jfree.chart.servlet.ServletUtilities, 
org.jfree.data.category.DefaultCategoryDataset,
java.awt.*,
org.jfree.chart.plot.*,
org.jfree.chart.axis.*,
org.jfree.chart.title.TextTitle"%>

<% 
DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
dataset.addValue(61, "Guangzhou中文", "Pig中文中文中华任命共和国中国共产党");
dataset.addValue(220, "Guangzhou中文", "Beef中文");
dataset.addValue(530, "Guangzhou中文", "Chicken中文");
dataset.addValue(340, "Guangzhou中文", "Fish中文");



JFreeChart chart = ChartFactory.createBarChart3D("肉中文中文中华任命共和国中国共产党", 
"type中文中华任命共和国中国共产党", 
"amount11中文", 
dataset, 
PlotOrientation.VERTICAL, 
false, 
false, 
false); 

TextTitle textTitle = chart.getTitle();
textTitle.setFont(new Font("黑体",Font.BOLD,15));

CategoryPlot plot=chart.getCategoryPlot();
CategoryAxis domainAxis=plot.getDomainAxis();
domainAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
domainAxis.setTickLabelFont(new Font("黑体",Font.BOLD,15));
ValueAxis valueAxis=plot.getRangeAxis();
valueAxis.setLabelFont(new Font("黑体",Font.BOLD,15));

//chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,15));

String filename = ServletUtilities.saveChartAsPNG(chart, 800, 400, null, session); 


String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename; 
System.out.println("graphURL:"+graphURL);

%> 

<img src="<%= graphURL %>" width=800 height=400 border=0 usemap="#<%= filename %>"> 

