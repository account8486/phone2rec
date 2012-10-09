<%@ page contentType="text/html;charset=GBK"%>

<%@ page import="org.jfree.chart.ChartFactory, 
org.jfree.chart.JFreeChart, 
org.jfree.chart.plot.PlotOrientation, 
org.jfree.chart.servlet.ServletUtilities, 
org.jfree.data.category.CategoryDataset, 
org.jfree.chart.ChartFactory, 
org.jfree.chart.JFreeChart, 
org.jfree.chart.plot.PlotOrientation, 
org.jfree.chart.servlet.ServletUtilities, 
org.jfree.data.category.DefaultCategoryDataset,
java.awt.*,
org.jfree.chart.plot.*,
org.jfree.chart.axis.*,
org.jfree.chart.title.TextTitle,
org.jfree.data.general.DatasetUtilities"%>

<% 
double[][] data = new double[][] {{1310}, {720}, {1130}, {440}, {440}, {440}, {440}, {440}, {440}}; 
String[] rowKeys = { "pig²âÊÔ", "beef²âÊÔ", "chicken²âÊÔ", "fish²âÊÔ", "fish11²âÊÔ" , "fish33²âÊÔ", "fish22²âÊÔ", "fish44²âÊÔ", "fish55²âÊÔ"};
String[] columnKeys = {""}; 
CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data); 
JFreeChart chart = ChartFactory.createBarChart3D("¹ãÖÝÈâÀàÏúÁ¿Í³¼ÆÍ¼", 
"type²âÊÔ", 
"amount²âÊÔ", 
dataset, 
PlotOrientation.VERTICAL, 
true, 
false, 
false); 


TextTitle textTitle = chart.getTitle();
textTitle.setFont(new Font("ºÚÌå",Font.BOLD,15));
CategoryPlot plot=chart.getCategoryPlot();
CategoryAxis domainAxis=plot.getDomainAxis();
domainAxis.setLabelFont(new Font("ºÚÌå",Font.BOLD,15));
domainAxis.setTickLabelFont(new Font("ºÚÌå",Font.BOLD,15));
ValueAxis valueAxis=plot.getRangeAxis();
valueAxis.setLabelFont(new Font("ºÚÌå",Font.BOLD,15));
chart.getLegend().setItemFont(new Font("ºÚÌå",Font.BOLD,15));


String filename = ServletUtilities.saveChartAsPNG(chart, 600, 400, null, session); 
String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename; 
%> 
<img src="<%= graphURL %>" width=600 height=400 border=0 usemap="#<%= filename %>"> 

