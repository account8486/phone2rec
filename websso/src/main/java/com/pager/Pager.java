package com.pager;

import java.util.List;

public class Pager {

	private int pageSize;
	private int currentPage;
	private int total;
	private int pageCount;

	// Whether it has previous page
	private boolean hasPreviousPage;

	// Whether it has next page
	private boolean hasNextPage;

	// Whether it has only one page
	private boolean onlyOnePage;

	// The records of page designed
	private List pageRecords;
	
	
	
	public Pager(){
		
	}

	/**
	 * 默认构造方法.
	 * 
	 * @param start
	 *            本页数据在数据库中的起始位置
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param pageSize
	 *            本页容量
	 * @param data
	 *            本页包含的数据
	 */
	public Pager(int currentPage, int totalSize, int pageSize, List data) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.total = totalSize;
		this.pageRecords = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isOnlyOnePage() {
		return onlyOnePage;
	}

	public void setOnlyOnePage(boolean onlyOnePage) {
		this.onlyOnePage = onlyOnePage;
	}

	public List getPageRecords() {
		return pageRecords;
	}

	public void setPageRecords(List pageRecords) {
		this.pageRecords = pageRecords;
	}

}
