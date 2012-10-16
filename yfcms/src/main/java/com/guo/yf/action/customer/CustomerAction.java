package com.guo.yf.action.customer;

import com.guo.yf.service.CustomerService;
import com.wondertek.meeting.action.base.BaseAction;

public class CustomerAction extends BaseAction {

	private static final long serialVersionUID = 5511639621068426569L;
	CustomerService customerService;
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public static void main(String[] args) {

		
	}

}
