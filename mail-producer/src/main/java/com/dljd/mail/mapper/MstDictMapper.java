package com.dljd.mail.mapper;

import com.dljd.mail.config.database.BaseMapper;
import com.dljd.mail.entity.MstDict;

import java.util.List;


public interface MstDictMapper extends BaseMapper<MstDict> {

	public List<MstDict> findByStatus(String status);

}