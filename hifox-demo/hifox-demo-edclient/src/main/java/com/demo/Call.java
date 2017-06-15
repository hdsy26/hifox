package com.demo;

public interface Call {
	String send(String serviceId, String type, String version, String text);
}
