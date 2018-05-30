package cn.yang.web.util;

import cn.yang.web.bean.PageIndex;


public class WebUtil {
	/**
	 * 得到显示的页码范围
	 * @param viewPageCount 每页显示的记录条数
	 * @param currentPage 当前页
	 * @param totalpage 总页码数
	 * @return 包含起始页和终止页的对象
	 */
	 public static PageIndex getPageIndex(int viewPageCount,long currentPage,long totalpage){
		 long startindex=currentPage-(viewPageCount%2==0 ? viewPageCount/2-1 :viewPageCount/2);
		 long endindex=currentPage+viewPageCount/2;
		 if(startindex<1){
			 startindex=1;
			 endindex=totalpage>viewPageCount ? viewPageCount :totalpage;
		 }
		 if(endindex>totalpage){
			 endindex=totalpage;
			 startindex=totalpage-viewPageCount<0 ? 1 :totalpage-viewPageCount+1;
		 }
		 return new PageIndex(startindex,endindex);
	 }
}
