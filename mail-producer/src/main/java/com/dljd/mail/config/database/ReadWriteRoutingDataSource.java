package com.dljd.mail.config.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {
	 
	  @Override
	  protected Object determineCurrentLookupKey() {
		  return DataBaseContextHolder.getDataBaseType();
	  }
}
