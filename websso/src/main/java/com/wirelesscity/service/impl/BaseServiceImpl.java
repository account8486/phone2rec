package com.wirelesscity.service.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wirelesscity.service.BaseService;

public class BaseServiceImpl<T, PK extends Serializable> implements
		BaseService<T, PK> {

	public Logger log = LoggerFactory.getLogger(this.getClass());
}
