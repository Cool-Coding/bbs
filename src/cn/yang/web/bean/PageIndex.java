package cn.yang.web.bean;

public class PageIndex {

	private long startIndex;
	private long endIndex;
	
	
	public PageIndex(long startIndex,long endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}


	public long getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}


	public long getEndIndex() {
		return endIndex;
	}


	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	
	
}
