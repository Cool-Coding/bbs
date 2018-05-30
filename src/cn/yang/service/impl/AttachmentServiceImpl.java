package cn.yang.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.bean.QueryResult;
import cn.yang.dao.DAOSupport;
import cn.yang.domain.Attachment;
import cn.yang.service.AttachmentService;

@Service
@Transactional
public class AttachmentServiceImpl extends DAOSupport<Attachment> implements
		AttachmentService {

	@Override
	public void delete(String wherejpql, Object[] params) {
        	Query query=em.createQuery("delete from Attachment o where "+(wherejpql==null || "".equalsIgnoreCase(wherejpql)? "" : wherejpql));
	        setQueryParams(query, params);
	        query.executeUpdate();
	}

	@Override
	public void addOne(Integer id) {
      Attachment attachment=find(id);
      attachment.setDownloadedCount(attachment.getDownloadedCount()+1);
      update(attachment);
	}
}
