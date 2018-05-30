package cn.yang.junit;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;



import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yang.bean.QueryResult;
import cn.yang.bean.ThemeContainer;
import cn.yang.domain.Forum;
import cn.yang.junit.jpatemplate.JPATemplate;
import cn.yang.service.ForumService;
import cn.yang.service.ThemeService;

public class ForumServiceImplTest {
   
	private static ApplicationContext factory;
	private static ForumService forumService;
	private static ThemeService themeService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory=new ClassPathXmlApplicationContext("beans.xml");
		 forumService=(ForumService) factory.getBean("forumServiceImpl");
		 themeService=(ThemeService) factory.getBean("themeServiceImpl");
	}

	@Test
	public void testSave() {
       JPATemplate template=new JPATemplate();
        
       for(int i=0;i<20;i++){
		Forum forum=new Forum("IT"+i);
		Forum child=new Forum("电脑"+i);
		child.setParent(forum);
		forum.addChild(child);
		template.save(forum);
        }
	}

	@Test
	public void testFindSerializable() {
		JPATemplate template=new JPATemplate();
		Forum forum=template.find(11);
		System.out.println(forum);
	}
	@Test
	public void testupdate() {
		JPATemplate template=new JPATemplate();
		Forum forum=template.find(11);
		forum.setName("电脑生活");
		forum.setVisible(false);
		template.update(forum);
		System.out.println(forum);
	}
	@Test
	public void testdelete() {
		JPATemplate template=new JPATemplate();
        template.delete(1);
	}
	@Test
	public void testScrollData() {
		JPATemplate template=new JPATemplate();
		String jpql="name like ?";
		Object[] params=new Object[]{"%1%"};
		
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("id", "desc");
		QueryResult<Forum> queryResult=template.scrollData(jpql, params,1,5,orderby);
        List<Forum> list=queryResult.getResultlist();
        for(Forum forum:list){
        	System.out.println(forum);
        }
	}
	@Test
	public void testSpringSave(){
		Forum forum=new Forum("校园生活");
		forumService.save(forum);
	}
	@Test
	public void testSpringFind(){

		String jpql="name like ?";
		Object[] params=new Object[]{"%1%"};
		
		LinkedHashMap<String,String> orderby=new LinkedHashMap<String,String>();
		orderby.put("id", "desc");
		QueryResult<Forum> queryResult=forumService.getScrollData(1,5,jpql, params,orderby);
        List<Forum> list=queryResult.getResultlist();
        for(Forum forum:list){
        	System.out.println(forum);
        }
	}
	@Test
	public void testspringupdate() {
		Forum forum=forumService.find(81);
		forum.setName("电脑生活");
		Forum childforum=new Forum(82,"电脑19");
		Set<Forum> set=forum.getChildren();
		for(Forum forum1:set){
			if(forum1.equals(childforum)){
				forum1.setName("电脑83");//若Forum中OneToMany设置了级联更新，则此举则会将数据更新到数据库
				//set.remove(forum1);//此举不能将数据库中forum1对应的子版块删除。
			}
		}
		//forum.removeChild(childforum);//数据库中对应的子版块不能删除
		forum.setVisible(false);
		forumService.update(forum);
	}
	@Test
	public void testDisableForum(){
		forumService.getAllChildForumIds(9);
	}
	@Test
	public void testGetAllParent(){
		List<Forum> parents=forumService.getAllParentForum(25);
		for(Forum forum:parents){
			System.out.println(forum.getName());
		}
	}
	@Test
	public void testGetLastForum(){
		Forum forum=forumService.getLastChildForum(8);
		System.out.println(forum);
	}

	@Test
	public void testGetThemeCountAndNewestTheme(){
		Set<Forum> forums=forumService.getLastChildForums(3);
/*		for(Forum forum:forums){
			System.out.println(forum.getName());
		}*/
		ThemeContainer container=forumService.getThemeCountOfLastForms(forums);
		
		System.out.println(container.getCounts());
		System.out.println(container.getNewestTheme().getTitle());
	}
}
