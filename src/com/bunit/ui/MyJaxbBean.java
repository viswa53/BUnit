package com.bunit.ui;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class MyJaxbBean {
	@XmlElement(name = "name")
	public String name;
	@XmlAttribute(name = "age")
	public int age;

	public MyJaxbBean() {} // JAXB needs this

	public MyJaxbBean(String name, int age) {
		this.name = name;
		this.age = age;
	}
}
