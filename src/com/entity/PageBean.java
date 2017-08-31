package com.entity;

import java.util.List;

public class PageBean<T> {
	
	 private int page;//当前页数
	    private int pageSize;//每页显示的记录数
	    private int totalCount;//总记录数
	    private int totalPage;//总页数
	    private List<T> lists;//每页的显示的数据

	    public PageBean() {
	        super();
	    }
	    public PageBean(int page, int pageSize) {
	        super();
	        this.page = page;
	        this.pageSize = pageSize;
	    }
	    

	    public int getPage() {
	        return page;
	    }

	    public void setPage(int page) {
	        this.page = page;
	    }

	    public int getPageSize() {
	        return pageSize;
	    }

	    public void setPageSize(int pageSize) {
	        this.pageSize = pageSize;
	    }

	    public int getTotalCount() {
	        return totalCount;
	    }

	    public void setTotalCount(int totalCount) {
	        this.totalCount = totalCount;
	    }

	    public int getTotalPage() {
	        return totalPage;
	    }

	    public void setTotalPage(int totalPage) {
	        this.totalPage = totalPage;
	    }

	    public List<T> getLists() {
	        return lists;
	    }

	    public void setLists(List<T> lists) {
	        this.lists = lists;
	    }

}
