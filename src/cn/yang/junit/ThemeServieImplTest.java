package cn.yang.junit;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yang.domain.Article;
import cn.yang.domain.Forum;
import cn.yang.domain.Theme;
import cn.yang.service.ForumService;
import cn.yang.service.ThemeService;

public class ThemeServieImplTest {
   
	private static ThemeService themeService;
	private static ForumService forumService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext beans=new ClassPathXmlApplicationContext("beans.xml");
		themeService=(ThemeService)beans.getBean("themeServiceImpl");
		forumService=(ForumService) beans.getBean("forumServiceImpl");
	}

	@Test
	public void testSave() {
		for(int i=0;i<20;i++){
			Theme theme=new Theme();
			//theme.setAuthor("ygy"+i);
			theme.setTitle("你今天还好吗?");
			theme.setContent("xxxxxxxx");
			theme.setForum(new Forum(2));
			theme.setUpdateTime(new Date());
			themeService.save(theme);
		}
	}
	
	@Test
	public void testMoveThemesToForum(){
		themeService.moveThemesToForum(new Serializable[]{1,2},1);
	}
	@Test
	public void testGetThemesByForumIds(){
		Set<Forum> forums=new HashSet<Forum>();
		forums.add(new Forum(7));
		forums.add(new Forum(8));
		System.out.println(forumService.getThemesByForumIds(-1, -1,forums).getResultlist());
	}
}
