package com.guo.yf.action.graphic;

import java.awt.Font;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;

import com.guo.yf.service.ChannelService;
import com.wondertek.meeting.action.base.BaseAction;

public class ChannelGraphicAction extends BaseAction {

	private static final long serialVersionUID = -6177415275212566194L;
	ChannelService channelService;

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	/**
	 * 生成柱状图
	 */
	public String genChannelNumBarChart() {
		try {
			@SuppressWarnings("unchecked")
			List<Object[]> lst = channelService.getArticleNumByChannel();

			int lstSize = lst.size();

			double[][] data = new double[lstSize][1];
			String[] rowKeys = new String[lstSize];
			String[] columnKeys = new String[]{""};
			int i = 0;
			for (Object[] obj : lst) {
				log.debug(obj[0] + "," + obj[1]);
		
				data[i] = new double[] { Double.parseDouble(String
						.valueOf(obj[1])) };
				
				rowKeys[i] = String.valueOf(obj[0]);
				i++;
			}

			CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
					rowKeys, columnKeys, data);
			JFreeChart chart = ChartFactory.createBarChart3D("栏目文章统计图", "栏目",
					"文章数", dataset, PlotOrientation.VERTICAL, true, false,
					false);

			// 通用程序
			TextTitle textTitle = chart.getTitle();
			textTitle.setFont(new Font("黑体", Font.BOLD, 15));
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
			domainAxis.setTickLabelFont(new Font("黑体", Font.BOLD, 15));
			ValueAxis valueAxis = plot.getRangeAxis();
			valueAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
			chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));

			String filename = ServletUtilities.saveChartAsPNG(chart, 600, 400,
					null, this.getSession());
			String graphURL = this.getRequest().getContextPath()
					+ "/DisplayChart?filename=" + filename;

			log.debug("graphURL:" + graphURL);
			
			this.setAttribute("graphURL", graphURL);
			this.setAttribute("filename", filename);

			return "channelNumBarChart";
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "channelNumBarChart";
	}
	
	/**
	 * 生成饼状图
	 * @return
	 */
	public String genChannelNumPieChart() {
		try{
			@SuppressWarnings("unchecked")
			List<Object[]> lst = channelService.getArticleNumByChannel();
			//设置数据集 
			DefaultPieDataset dataset = new DefaultPieDataset();
			
			int i = 0;
			for (Object[] obj : lst) {
				log.debug(obj[0] + "," + obj[1]);
				dataset.setValue(String.valueOf(obj[0]),
						Double.parseDouble(String.valueOf(obj[1])));
				i++;
			}
			//通过工厂类生成JFreeChart对象 
			JFreeChart chart = ChartFactory.createPieChart3D("栏目文章发布分布图",
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
					null, this.getSession());
			String graphURL = this.getRequest().getContextPath()
					+ "/DisplayChart?filename=" + filename;
			
			this.setAttribute("graphURL", graphURL);
			this.setAttribute("filename", filename);
			
			return "channelNumPieChart";
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return "channelNumPieChart";
	}

}
