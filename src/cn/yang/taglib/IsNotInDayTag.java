package cn.yang.taglib;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class IsNotInDayTag extends TagSupport {

	private static final long serialVersionUID = 8982355819454706156L;
	private Date date;
	private int type;//0--当天 1--7天之内的 2--7天之外的
	@SuppressWarnings("deprecation")
	@Override
	public int doStartTag() throws JspException {
      
		if(date==null) return SKIP_BODY;
		
		Date today=new Date();
		today.setHours(23); 
		today.setMinutes(59); 
		today.setSeconds(59); 
		
		long diff = today.getTime() - date.getTime(); 
		if(diff<0)diff=0; 
		long days = diff/(1000*60*60*24); 
		
		// 如果是在今天之内，就跳过
		if(type==0 && days==0)return SKIP_BODY;
		if(type==1 && days>0 && days<=7)return SKIP_BODY;
		if(type==2 && days>7)return SKIP_BODY;

		
		// 不在今天之内就执行标签体内容
			return EVAL_BODY_INCLUDE;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
