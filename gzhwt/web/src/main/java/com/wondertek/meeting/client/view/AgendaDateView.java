/**
 * 
 */
package com.wondertek.meeting.client.view;

import java.util.List;

/**
 * @author rain
 * 
 */
public class AgendaDateView {

	/** 日期 */
	private String date;

	private List<AgendaView> agendaViews;

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the agendaViews
	 */
	public List<AgendaView> getAgendaViews() {
		return agendaViews;
	}

	/**
	 * @param agendaViews
	 *            the agendaViews to set
	 */
	public void setAgendaViews(List<AgendaView> agendaViews) {
		this.agendaViews = agendaViews;
	}

}
