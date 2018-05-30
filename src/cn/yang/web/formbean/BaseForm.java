package cn.yang.web.formbean;


import org.apache.struts.action.ActionForm;

public class BaseForm extends ActionForm {

	private static final long serialVersionUID = -8470630401658130367L;
	private int page;

	public int getPage() {
		if(page<=0) page=1;
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
