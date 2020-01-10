package br.com.dimo.ediwsboot;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DBConfig {

//   @Bean
//   public DataSource dataSource() {
//      //create a data source
//   }
//
//   @Bean
//   public JdbcTemplate jdbcTemplate() {
//      return new JdbcTemplate(dataSource());
//   }
//
//   @Bean
//   public TransactionManager transactionManager() {
//      return new DataSourceTransactionManager(dataSource());
//   }

}