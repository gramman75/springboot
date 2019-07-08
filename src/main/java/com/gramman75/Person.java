package com.gramman75;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
