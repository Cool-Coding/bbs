package cn.yang.util;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yang.domain.Forum;
import cn.yang.domain.User;
import cn.yang.domain.util.MemberUtils;
import cn.yang.service.ForumService;

public class ModeratorIdentifyUtil {

	private static ForumService forumService;
	
	static{
	 ApplicationContext factory;factory=new ClassPathXmlApplicationContext("beans.xml");
	 forumService=(ForumService) factory.getBean("forumServiceImpl");
	}
	public static boolean checkModeratorForForum(User user,Forum forum){
		
		//如果当前用户是超给管理员，则返回真
		if(MemberUtils.isSuperman(user)) return true;
		
		List<Forum> forums=forumService.getAllParentForum(forum.getId());
		for(Forum foru:forums){
			Set<User> users=foru.getUsers();
			for(User moderator:users){
				if(moderator.getLoginName().equals(user.getLoginName())){
					return true;
				}
			}
		}
		return false;
	}
}
