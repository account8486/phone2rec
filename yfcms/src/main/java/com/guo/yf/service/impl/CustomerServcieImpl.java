package com.guo.yf.service.impl;

import com.guo.yf.dao.CustomerDao;
import com.guo.yf.model.customer.Customer;
import com.guo.yf.service.CustomerService;
import com.wondertek.meeting.service.impl.BaseServiceImpl;

public class CustomerServcieImpl extends BaseServiceImpl<Customer, String>
		implements CustomerService {
	
	CustomerDao customerDao;
	public CustomerDao getCustomerDao() {
		return customerDao;
	}
	
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
		this.basicDao=customerDao;
	}

}
