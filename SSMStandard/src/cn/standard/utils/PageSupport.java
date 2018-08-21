package cn.standard.utils;

public class PageSupport {
	//当前页码-来自于用户输入
	private int pageIndex = 1;
	
	//总数量（表）
	private int totalCount = 0;
	
	//页面容量
	private int pageSize = 0;
	
	//总页数-totalCount/pageSize（+1）
	private int totalPage = 1;


	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if(totalCount > 0){
			this.totalCount = totalCount;
			//设置总页数
			this.setTotalPageCountByRs();
		}
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize > 0){
			this.pageSize = pageSize;
		}
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPageCount) {
		this.totalPage = totalPageCount;
	}
	
	public void setTotalPageCountByRs(){
		if(this.totalCount % this.pageSize == 0){
			this.totalPage = this.totalCount / this.pageSize;
		}else if(this.totalCount % this.pageSize > 0){
			this.totalPage = this.totalCount / this.pageSize + 1;
		}else{
			this.totalPage = 0;
		}
	}
	
}