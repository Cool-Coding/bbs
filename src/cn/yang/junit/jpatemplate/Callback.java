package cn.yang.junit.jpatemplate;

import javax.persistence.EntityManager;

import cn.yang.service.ForumService;

/**
 * JPATemplate所用的回调类
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-7
 */
public interface Callback {

	public void invoke(ForumService em);
}
