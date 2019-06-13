package com.example.demo.setup;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.dto.EmployeeDTO;

public class ProcessorOne implements ItemProcessor<EmployeeDTO, EmployeeDTO>{

	@Override
	public EmployeeDTO process(EmployeeDTO dto) throws Exception {

		return dto;
	}

}
