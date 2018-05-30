package cn.yang.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yang.dao.DAOSupport;
import cn.yang.domain.SystemLog;
import cn.yang.service.SystemLogService;



@Service
@Transactional
public class SystemLogServiceImpl extends DAOSupport<SystemLog> implements SystemLogService {

}
