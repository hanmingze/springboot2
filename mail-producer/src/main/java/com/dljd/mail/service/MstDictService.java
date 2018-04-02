package com.dljd.mail.service;

import java.util.List;

import com.dljd.mail.config.database.ReadOnlyAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dljd.mail.entity.MstDict;
import com.dljd.mail.mapper.MstDictMapper;
import com.github.pagehelper.PageHelper;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author baihezhuo
 * @since 2017年5月7日 下午11:02:17
 */
@Service
public class MstDictService {
	
    @Autowired
    private MstDictMapper mstDictMapper;


    public List<MstDict> findAll(Integer page, Integer rows){
        if (page != null && page != null) {
            PageHelper.startPage(page, rows);
        }
        return mstDictMapper.selectAll();
    }

    @ReadOnlyAnnotation
    public List<MstDict> findByStatus(String status){
    	return mstDictMapper.findByStatus(status);
    }

}
