package com.make_profile.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component(value = "dataSourceLookup")
public class MultiTenantDataSourceLookup extends MapDataSourceLookup {

	private static final Logger logger = LoggerFactory.getLogger(MultiTenantDataSourceLookup.class);

	@Autowired
	DataSource defaultDataSource;

	 
	@PostConstruct
	private void addDefaultDataSourceToLookup() {
//		addDataSource(CommonConstants.DEFAULT_TENANT, defaultDataSource);
//
//		dataSourceConfigurationRepository.findAll().forEach(configuration -> {
//			createDataSource(configuration);
//		});
	}

//	public DataSource getDataSourceFromLookup(String tenant) {
//		DataSource dataSource = null;
//		try {
//			dataSource = getDataSource(tenant);
//		} catch (DataSourceLookupFailureException dataSourceLookupFailureException) {
//
//		}
//		return dataSource;
//	}

//	public DataSource createDataSource() {
//		logger.debug("Service :: createDataSource :: Entered");
//		try {
//			 
//				DataSource datasource = DataSourceBuilder.create()
//						.driverClassName("com.mysql.cj.jdbc.Driver")
//						.username("root").password("root")
//						.url("jdbc:mysql://localhost:3306/hurecom_v2").build();
//				addDataSource("00000", datasource);
//				return datasource;
//			 
//		} catch (Exception e) {
//			logger.error("Service :: createDataSource :: Exception :: " + e.getMessage());
//		}
//		logger.debug("Service :: createDataSource :: Exited");
//		return null;
//	}

}
