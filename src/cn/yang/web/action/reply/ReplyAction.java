package cn.yang.web.action.reply;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.annotation.Permission;
import cn.yang.bean.QueryResult;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Reply;
import cn.yang.service.ReplyService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.ReplyForm;

@Controller("/control/reply/list")
@Permission(resource = "System", action = "Manage")
public class ReplyAction extends Action {
    
	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wherejpql=null;
		List<Object> params=new ArrayList<Object>();
		//每页显示的记录数(默认为10条)
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.REPLYCOUNT_PERPAGE));
		/*分页数据存放PageView中*/
		PageView<Reply> pageView=null;
		//查询结果存放于QueryResult中
		QueryResult<Reply> replies=null;
		
		ReplyForm formbean=(ReplyForm)form;
		Integer themeId=formbean.getThemeId();
		if(themeId==null || themeId<=0){
          return null;
		}
		//是否将不可见的回帖显示出来
		String showInvisibleReply=PropertiesUtil.get(ControlCenterConstants.SHOW_INVISIBLE_REPLY);
		if("true".equals(showInvisibleReply)) wherejpql="o.theme.id=?";
		else wherejpql="o.theme.id=? and o.visible=true";
		
		params.add(themeId);
		
		//得到当前页码
		int currentPage=formbean.getPage();
		//==============排序=====================
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("visible","desc");
		orderby.put("createTime","asc");
	    pageView=new PageView<Reply>(viewPageRecords,currentPage);
	    replies=replyService.getScrollData(pageView.getFirstIndex(),pageView.getViewPageRecords(),wherejpql.toString(),params.toArray(),orderby);
	    pageView.setQueryResult(replies);
		request.setAttribute("pageView",pageView);
		return mapping.findForward("list");
	}

	
}
