package cn.yang.domain;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-28
 * 此类表示的是帖子的类型，共有两类帖子：一类为主题帖，第二类为投票帖
 */

public enum PostType {

	SUBJECT{
		@Override
		public String getName() {
			return "普通帖";
		}
		
	},NOTICE{
		@Override
		public String getName() {
			return "公告";
		}
	};
	public abstract String getName();
}
