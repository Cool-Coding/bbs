package cn.yang.bean;

import cn.yang.domain.Theme;

public class ThemeContainer {
  
	private Integer counts;//帖子总数
	
	private Theme newestTheme;//最新帖子

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public Theme getNewestTheme() {
		return newestTheme;
	}

	public void setNewestTheme(Theme newestTheme) {
		this.newestTheme = newestTheme;
	}
	
	
}   
