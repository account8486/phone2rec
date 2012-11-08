/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wirelesscity.action;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.service.JdbcService;
import com.wirelesscity.tools.ftp.FtpService;

/**
 * 
 */
@Conversion()
public class IndexAction extends BaseAction {
    
    private Date now = new Date(System.currentTimeMillis());
    
    @TypeConversion(converter = "com.wirelesscity.action.DateConverter")
    public Date getDateNow() { return now; }
    
    FtpService ftpService;
    JdbcService jdbcService;
    
    public FtpService getFtpService() {
		return ftpService;
	}

	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}
	
	

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	
	
	
	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public String execute() throws Exception {
        //now = new Date(System.currentTimeMillis());
       // ftpService.testUpload();
		
		StringBuffer hql=new StringBuffer();
		hql.append(" SELECT * FROM USER_WCITY WHERE ROWNUM<10  ");
		/*
		SqlRowSet  rs=jdbcService.getResultSet(hql.toString());
		int i=0;
		while(rs.next()){
			log.debug(i+":"+rs.getString("USERNAME"));
			i++;
		}*/
		
		jdbcService.getList(hql.toString());
        
        return SUCCESS;
    }
    
    
    public String toIndex() throws Exception {
        now = new Date(System.currentTimeMillis());
        log.debug(String.valueOf(now));
        return SUCCESS;
    }
}
