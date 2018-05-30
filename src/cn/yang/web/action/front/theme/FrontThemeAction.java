package cn.yang.web.action.front.theme;

import java.util.ArrayList;
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
import cn.yang.constant.ControlCenterConstants;
import cn.yang.domain.Forum;
import cn.yang.domain.Reply;
import cn.yang.domain.Theme;
import cn.yang.service.ForumService;
import cn.yang.service.ThemeService;
import cn.yang.util.PropertiesUtil;
import cn.yang.web.bean.PageView;
import cn.yang.web.formbean.ThemeForm;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 *2011-4-4
 * 
 */
@Controller("/get/forum/theme")
public class FrontThemeAction extends Action {

	@Resource(name="forumServiceImpl")
	private ForumService forumService;

    @Resource(name="themeServiceImpl") 
	private ThemeService themeService;
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThemeForm formbean=(ThemeForm)form;
		Integer forumId=formbean.getForumId();
		
		//每页显示的记录数(默认为10条)
		int viewPageRecords=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_RECORDS));
		//每页显示的页码数
		int viewPageCount=Integer.parseInt(PropertiesUtil.get(ControlCenterConstants.FRONT_VIEW_PAGE_COUNT));
		PageView<Theme> pageView=null;
		//查询结果存放于QueryResult中
		QueryResult<Theme> themes=null;
		//得到当前页码
		int currentPage=formbean.getPage();
		pageView=new PageView<Theme>(viewPageRecords,currentPage);
		pageView.setViewPageCount(viewPageCount);
		
		//如果是要求查询即query标志为true
		if(formbean.getQuery()!=null && formbean.getQuery().booleanValue()){
			String wherejpql=null;
			List<Object> params=new ArrayList<Object>();
			Integer category=formbean.getCategory();
			String keywords=formbean.getKeywords();
			wherejpql=(category==0 ? "o.title like ?":"o.user.loginName like ?");
			params.add("%"+keywords+"%");		
			themes=forumService.searchThemes(pageView.getFirstIndex(), viewPageRecords, wherejpql,params.toArray());
		}else{
		  /*导航*/
		  /*得到当前帖子所在版块的所有父版块*/
		  List<Forum> parents=null;
		  Forum currentForum=null;
		  if(forumId==null || forumId<=0){
			  try{
			  forumId=Integer.parseInt((String) request.getAttribute("forumId"));
			  }catch(NumberFormatException e){
				  forumId=null;
			  }
		  }
		 if(forumId!=null && forumId>0) { 
			  parents=forumService.getAllParentForum(forumId);
			  currentForum=parents.get(0);
		  }else throw new RuntimeException("前台显示帖子列表时，没有forumId属性异常");
		  //将得到的上级版块放于request作用域中，以供页面访问
		  request.setAttribute("parents",parents);
		  
		  request.setAttribute("currentForum", currentForum);
		  //结束
		  
		Set<Forum> childForums=forumService.getLastChildForums(forumId);
		themes=forumService.getThemesByForumIds(pageView.getFirstIndex(), viewPageRecords, childForums);
		}

		//FIXME 如果themes.getResultList()得到null，则for(Theme theme:themes.getResultlist())会抛出空指针异常
		List<Theme> listThemes=themes.getResultlist();
		if(listThemes!=null && listThemes.size()>0){
			for(Theme theme:listThemes){
				Reply reply=themeService.getLastReply(theme);
				theme.setLastReply(reply);
			}
		}
		pageView.setQueryResult(themes);
		request.setAttribute("pageView", pageView);
		if(formbean.getQuery()!=null && formbean.getQuery().booleanValue()){
			return mapping.findForward("queryResult");
		}
		return mapping.findForward("list");
	}

	
}
