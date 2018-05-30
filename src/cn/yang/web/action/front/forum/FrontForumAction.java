package cn.yang.web.action.front.forum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.yang.bean.QueryResult;
import cn.yang.bean.ThemeContainer;
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Forum;
import cn.yang.domain.Theme;
import cn.yang.service.ForumService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.ForumForm;

@Controller("/index")
public class FrontForumAction extends Action {

	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        
        
		//每页显示的记录数(默认为10条)
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_RECORDS));
		//每页显示的页码数
		int viewPageCount=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_COUNT));
		/*分页数据存放PageView中*/
		PageView<Forum> pageView=null;
		//查询结果存放于QueryResult中
		QueryResult<Forum> forums=null;
		
		ForumForm formbean=(ForumForm)form;
		//得到当前页码
		int currentPage=formbean.getPage();
		//==============查询===================
		StringBuffer wherejpql=new StringBuffer("o.visible=true");
		List<Object> params=new ArrayList<Object>();

		if(formbean.getParent().getId()!=null && formbean.getParent().getId()>0){
			wherejpql.append(" and o.parent.id=?").append(params.size()+1);
			params.add(formbean.getParent().getId());
		}else{
			wherejpql.append(" and o.parent is null");
		}
		
		//==============排序=====================
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("weight","desc");
		orderby.put("name","asc");
  
	  pageView=new PageView<Forum>(viewPageRecords,currentPage);
	  pageView.setViewPageCount(viewPageCount);
	  if(formbean.getSimpleForumList()){
			  /*获取满足条件的所有数据*/
			  forums=forumService.getScrollData(wherejpql.toString(),params.toArray(),orderby);
		}else
	  forums=forumService.getScrollData(pageView.getFirstIndex(),pageView.getViewPageRecords(),wherejpql.toString(),params.toArray(),orderby);
	 
      //FIXME 此处是针对前台三级版块的情况--得到版块下所有帖子与最新帖子
	  List<Forum> list_forums=forums.getResultlist();
	  ThemeContainer container=null;
	  if(list_forums!=null && list_forums.size()>0){
		  for(Forum forum:list_forums){
			  Set<Forum> children=forum.getChildren();
			  for(Forum forum1:children){
					Integer forumId=forum1.getId();
					Set<Forum> forum2=forumService.getLastChildForums(forumId);
					container=forumService.getThemeCountOfLastForms(forum2);
					List<Theme> themes=forumService.getThemesInCurrentDay(forum2);
					if(container!=null){
						forum1.setAllThemesCount(container.getCounts());
						forum1.setLastTheme(container.getNewestTheme());
					}
					if(themes!=null && themes.size()>0){
						forum1.setCurrentDay_Themes(themes);
					}
			  }
		  }
	  }
	  pageView.setQueryResult(forums);
	  
	  /*导航*/
	  /*得到当前版块的所有父版块*/
	  List<Forum> parents=null;
	  if(formbean.getParent().getId()!=null && formbean.getParent().getId()>0) parents=forumService.getAllParentForum(formbean.getParent().getId());
	  //将得到的上级版块放于request作用域中，以供页面访问
	  request.setAttribute("parents",parents);
	  //结束
	  
	  request.setAttribute("pageView",pageView);
		if(formbean.getSimpleForumList()){
			  int viewPageRecordsCols=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.VIEW_SIMPLEFORUM_PAGE_RECORDS_COLS));
			  request.setAttribute("recordsCols",viewPageRecordsCols);
			  return mapping.findForward("simpleList_for_other");
			}else return mapping.findForward("list");
	}
}
