package cn.yang.web.action.theme;

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
import cn.yang.domain.Forum;
import cn.yang.domain.Theme;
import cn.yang.service.ForumService;
import cn.yang.service.ThemeService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.ForumForm;
import cn.yang.web.formbean.ThemeForm;

@Controller("/control/theme/list")
@Permission(resource = "System", action = "Manage")
public class ThemeAction extends Action {
	
    @Resource(name="themeServiceImpl") 
	private ThemeService themeService;
    
	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wherejpql=null;
		List<Forum> list_forum=null;
		//每页显示的记录数(默认为10条)
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.VIEW_PAGE_RECORDS));
		/*分页数据存放PageView中*/
		PageView<Theme> pageView=null;
		//查询结果存放于QueryResult中
		QueryResult<Theme> themes=null;
		
		ThemeForm formbean=(ThemeForm)form;
		Integer forumId=formbean.getForumId();
		if(forumId==null || forumId<=0){
		//===========取得版块表中没有子版块的版块==================
		wherejpql="o.parent is null";
		QueryResult<Forum> forums=forumService.getScrollData(0, 1, wherejpql, null);
		list_forum=forums.getResultlist();
		Forum forum=null;
			if(list_forum!=null && list_forum.size()>0){
			forum=list_forum.get(0);
			forumId=forumService.getLastChildForum(forum.getId()).getId();
			formbean.setForumId(forumId);//将查出来的forumId放入formbean中，供页面端调用
			}
		}
		if(forumId==null || forumId<0) forumId=0;
		List<Object> params=new ArrayList<Object>();
		//如果是要求查询即query标志为true
		if(formbean.getQuery()!=null && formbean.getQuery().booleanValue()){
			Integer category=formbean.getCategory();
			String keywords=formbean.getKeywords();
			wherejpql=(category==0 ? "o.title like ?":"o.user.loginName like ?");
			params.add("%"+keywords+"%");		
		}else{
			params.add(forumId);
			wherejpql="o.forum.id=?";
			request.setAttribute("currentForum",forumService.find(forumId));
		}
		//得到当前页码
		int currentPage=formbean.getPage();
		//==============排序=====================
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("visible","desc");
		orderby.put("top", "desc");
		orderby.put("updateTime","desc");
	    pageView=new PageView<Theme>(viewPageRecords,currentPage);
	    themes=themeService.getScrollData(pageView.getFirstIndex(),pageView.getViewPageRecords(),wherejpql.toString(),params.toArray(),orderby);
	    pageView.setQueryResult(themes);
		request.setAttribute("pageView",pageView);
		return mapping.findForward("list");
	}

}
