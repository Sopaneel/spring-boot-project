package com.example.demo.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.setup.CompositeWriter;
import com.example.demo.setup.ProcessorOne;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	   @Autowired
	    public JobBuilderFactory jobBuilderFactory;
	    @Autowired
	    public StepBuilderFactory stepBuilderFactory;
	 
	@Autowired
	private SimpleJobLauncher jobLauncher;
	    
	@Scheduled(cron = "0 0/1 * 1/1 * ?")    
	public void sendSmsForExpiringBookmark() throws Exception 
	{
	System.out.println(" Job Started at :"+ new Date());
	JobParameters param = new JobParametersBuilder().addString("JobID",
	String.valueOf(System.currentTimeMillis())).toJobParameters();
	JobExecution execution = jobLauncher.run(job(), param);
	System.out.println("Job finished with status :" + execution.getStatus());
	}
	
	@Bean
    public Job job() {
        return jobBuilderFactory.get("jobOne")
                .incrementer(new RunIdIncrementer())
                .flow(stepOne())
                .end()
                .build();
    }
	
	@Bean
    public Step stepOne() {
        return stepBuilderFactory.get("stepOne")
                .<EmployeeDTO, EmployeeDTO> chunk(1)
                .reader(readerOne())
                .processor(processorOne())
                .writer(compositeWriter())
//                .stream(writerOne())
//                .stream(writerTwo())
                .build();
    }
	@Bean
	public JdbcCursorItemReader<EmployeeDTO> readerOne(){
		JdbcCursorItemReader<EmployeeDTO> batchItemWriter = new JdbcCursorItemReader<>();
		
		return batchItemWriter;
	}
	@Bean
	public ItemProcessor<EmployeeDTO, EmployeeDTO> processorOne(){
		return new ProcessorOne();
	}
	@Bean
	public CompositeItemWriter<EmployeeDTO> compositeWriter(){
		CompositeItemWriter<EmployeeDTO> compositeItemWriter = new CompositeItemWriter<>();
		List<ItemWriter<? super EmployeeDTO>> writers = new ArrayList<>(2);
		writers.add(writerOne());
		writers.add(writerTwo());
		compositeItemWriter.setDelegates(writers);
		return new CompositeWriter();
	}
	@Bean
	public JdbcBatchItemWriter<EmployeeDTO> writerOne(){
		JdbcBatchItemWriter<EmployeeDTO> batchItemWriter = new JdbcBatchItemWriter<>();
		
		return batchItemWriter;
	}
	@Bean
	public JdbcBatchItemWriter<EmployeeDTO> writerTwo(){
		JdbcBatchItemWriter<EmployeeDTO> batchItemWriter = new JdbcBatchItemWriter<>();
		
		return batchItemWriter;
	}
}
