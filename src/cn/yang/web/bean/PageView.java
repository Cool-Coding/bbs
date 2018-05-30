package cn.yang.web.bean;

import java.util.List;

import cn.yang.util.PropertiesUtil;
import cn.yang.web.bean.PageIndex;
import cn.yang.web.util.WebUtil;
import cn.yang.bean.QueryResult;
import cn.yang.constant.ControlCenterConstants;

public class PageView<T> {
	/*分页数据*/
	private List<T> records;
	/*页码开始索引和结束索引*/
	private PageIndex pageIndex;
	/*总页数*/
	private int totalPage=1;
	/*每页显示的记录数*/
	private int viewPageRecords;
	/*显示的页码数*/
	private int viewPageCount=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.VIEW_PAGE_COUNT));
	/*当前页*/
	private int currentPage=1;
	/*总记录数*/
	private long totalRecord;
	/*开始查询第几个记录*/
	private int firstIndex;
	
	
	public PageView(int viewPageRecords,int currentpage) {
		this.viewPageRecords = viewPageRecords;
		this.currentPage = currentpage;
		this.firstIndex=(currentpage-1)*viewPageRecords;
		
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public PageIndex getPageIndex() {
		return pageIndex;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public int getViewPageRecords() {
		return viewPageRecords;
	}
	public long getCurrentPage() {
		return currentPage;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalrecord) {
		this.totalRecord = totalrecord;
	}
	public List<T> getRecords() {
		return records;
	}
	
	public int getViewPageCount() {
		return viewPageCount;
	}
	public void setViewPageCount(int viewPageCount) {
		this.viewPageCount = viewPageCount;
	}
	/**
	 * 此方法是留给外界调用的入口函数，接受QueryResutl对象，进而得到总记录数和分页数据。
	 * @param queryResult
	 */
	public void setQueryResult(QueryResult<T> queryResult){
		setTotalRecord(queryResult.getTotalrecord());
		setRecords(queryResult.getResultlist());
	}
	/**
	 * 接受分页数据，进而得到总页数、起始页、终止页
	 * notice:在调用此方法之前要设置总记录数
	 * @param list
	 */
	public void setRecords(List<T> list) {
		this.records = list;
		this.totalPage=(int) (totalRecord%viewPageRecords==0?totalRecord/viewPageRecords:totalRecord/viewPageRecords+1);
		if(totalPage==0) totalPage=1;
		this.pageIndex=WebUtil.getPageIndex(viewPageCount,currentPage,totalPage);
	}
}
