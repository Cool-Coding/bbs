package cn.yang.web.action.forum;

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
import cn.yang.domain.User;
import cn.yang.service.ForumService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.ForumForm;

@Controller("/control/forum/list")
@Permission(resource = "System", action = "Manage")
public class ForumAction extends Action {

	@Resource(name="forumServiceImpl")
	private ForumService forumService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//每页显示的记录数(默认为10条)
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.VIEW_PAGE_RECORDS));
		/*分页数据存放PageView中*/
		PageView<Forum> pageView=null;
		//查询结果存放于QueryResult中
		QueryResult<Forum> forums=null;
		
		ForumForm formbean=(ForumForm)form;
		//得到当前页码
		int currentPage=formbean.getPage();
		//==============查询===================
		StringBuffer wherejpql=new StringBuffer("1=1");
		List<Object> params=new ArrayList<Object>();
		if("true".equals(formbean.getQuery())){
			//按版块名称进行模糊查询
			if(formbean.getName()!=null) {
				wherejpql.append(" and o.name like ?").append(params.size()+1);
				params.add("%"+formbean.getName().trim()+"%");
			}
			//按版主进行模糊查询
			if(formbean.getModerator()!=null) {
				wherejpql.append(" and ?").append(params.size()+1).append("  member of o.users");
				params.add(new User(formbean.getModerator().trim()));
			}
		}
		//==============排序=====================
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("visible","desc");
		orderby.put("name","asc");
		//==============子版块列表(若请求是查询发出，则页面不分级显示)==============
	  if(!"true".equals(formbean.getQuery())){
		if(formbean.getParent().getId()!=null && formbean.getParent().getId()>0){
			wherejpql.append(" and o.parent.id=?").append(params.size()+1);
			params.add(formbean.getParent().getId());
		}else{
			wherejpql.append(" and o.parent is null");
		}
	  }
	  
	  /*导航*/
	  /*得到当前版块的所有父版块*/
	  List<Forum> parents=null;
	  if(formbean.getParent().getId()!=null && formbean.getParent().getId()>0) parents=forumService.getAllParentForum(formbean.getParent().getId());
	  //将得到的上级版块放于request作用域中，以供页面访问
	  request.setAttribute("parents",parents);
	  //结束
	  
	  pageView=new PageView<Forum>(viewPageRecords,currentPage);
	  //================================请求版块的简单列表============================
		if(formbean.getSimpleForumList()){
			  /*添加一个查询可见版块的条件*/
			  wherejpql.append(" and o.visible=true");
			  
			  /*获取满足条件的所有数据*/
			  forums=forumService.getScrollData(wherejpql.toString(),params.toArray(),orderby);
			  
			  int viewPageRecordsCols=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.VIEW_SIMPLEFORUM_PAGE_RECORDS_COLS));
			  request.setAttribute("recordsCols",viewPageRecordsCols);
		  }else{
			  forums=forumService.getScrollData(pageView.getFirstIndex(),pageView.getViewPageRecords(),wherejpql.toString(),params.toArray(),orderby);
		  }
		pageView.setQueryResult(forums);
		
		request.setAttribute("pageView",pageView);
		
		if(formbean.getSimpleForumList()){
		   if(formbean.getOther()) return mapping.findForward("simpleList_for_other");
			return mapping.findForward("simpleList");
		}else return mapping.findForward("list");
	}
}
