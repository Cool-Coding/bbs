package cn.yang.web.action.front.reply;

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

import cn.yang.bean.QueryResult;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Forum;
import cn.yang.domain.Reply;
import cn.yang.domain.Theme;
import cn.yang.service.ReplyService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.ReplyForm;
import cn.yang.web.formbean.ThemeForm;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-4
 * 
 */
@Controller("/reply/list")
public class FrontReplyAction extends Action {
    
	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wherejpql=null;
		List<Object> params=new ArrayList<Object>();
		//每页显示的记录数(默认为10条)
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_REPLYCOUNT_PERPAGE));
		//每页显示的页码数
		int viewPageCount=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_COUNT));
		
		/*分页数据存放PageView中*/
		PageView<Reply> pageView=null;
		//查询结果存放于QueryResult中
		QueryResult<Reply> replies=null;
		
		ReplyForm formbean=(ReplyForm)form;
		Integer themeId=formbean.getThemeId();
		if(themeId==null || themeId<=0){
          return null;
		}
        wherejpql="o.theme.id=? and o.visible=true";
	  	params.add(themeId);
		
		//得到当前页码
		int currentPage=formbean.getPage();
		//==============排序=====================
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("createTime","asc");
	    pageView=new PageView<Reply>(viewPageRecords,currentPage);
		pageView.setViewPageCount(viewPageCount);
	    replies=replyService.getScrollData(pageView.getFirstIndex(),pageView.getViewPageRecords(),wherejpql.toString(),params.toArray(),orderby);
	    pageView.setQueryResult(replies);
		request.setAttribute("pageView",pageView);
		return mapping.findForward("list");
	}

	
}
