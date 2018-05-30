package cn.yang.service;

import cn.yang.dao.DAO;
import cn.yang.domain.Attachment;

public interface AttachmentService extends DAO<Attachment> {
    
	/**根据条件进行删除*/
	public void delete(String wherejpql,Object[] params);
	/*根据ID将附件的下载次数加1*/
	public void addOne(Integer id);
}
