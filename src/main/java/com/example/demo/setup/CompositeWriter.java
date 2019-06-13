package com.example.demo.setup;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.EmployeeDTO;

public class CompositeWriter extends CompositeItemWriter<EmployeeDTO> {
	
	@Autowired
	private DataSource dataSource;
	
	List<EmployeeDTO> insert;
	List<EmployeeDTO> update;

	@Override
	public void write(List<? extends EmployeeDTO> arg0) throws Exception {
		CompositeItemWriter<EmployeeDTO> compositeItemWriter = new CompositeItemWriter<>();
		compositeItemWriter.setDelegates(Arrays.asList(insertW(),updateW()));
	}
	private JdbcBatchItemWriter<EmployeeDTO> insertW() throws Exception{
		JdbcBatchItemWriter<EmployeeDTO> batchItemWriter = new JdbcBatchItemWriter<>();
		batchItemWriter.setDataSource(dataSource);
		batchItemWriter.setSql("");
		batchItemWriter.write(insert);
		return batchItemWriter;
	}
	
	private JdbcBatchItemWriter<EmployeeDTO> updateW() throws Exception{
		JdbcBatchItemWriter<EmployeeDTO> batchItemWriter = new JdbcBatchItemWriter<>();
		batchItemWriter.setDataSource(dataSource);
		batchItemWriter.setSql("");
		batchItemWriter.write(update);
		return batchItemWriter;
	}
}
