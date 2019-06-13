/**
 * 
 */
package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Sopaneel
 *
 */
@Api(value="Demo controller")
@RestController
public class DemoController {

	@ApiResponses(value= {
			@ApiResponse(code=200, message="")
	})
	@ApiOperation(value="Simply return Hello", response=String.class)
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String getMessage() {
		return "Hello";
	}
	
	@ApiOperation(value="Simply return hello world", response=String.class)
	@RequestMapping(value="/hello/world", method=RequestMethod.GET)
	public String getMessageNew() {
		return "Hello World";
	}
}
