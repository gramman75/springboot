package com.gramman75;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class SimpleController {
	
	@GetMapping("/hello")
	public String hello(@RequestParam("name") Person person) {
		log.debug("grammanLog");
		return "hello " + person.getName();
	}
	
	@GetMapping("/message")
	@ResponseBody
	public String messasge(@RequestBody String body) {
		return body;
		
	}
	
	@GetMapping("/jsonMessage")
	@ResponseBody
	public Person jsonMessage(@RequestBody Person person) {
		return person;
	}
}



//@GetMapping("/hello/{name}")
//public String hello(@PathVariable("name") Person person) {
//	return "hello " + person.getName();
//}