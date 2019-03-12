package com.aa.ms6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class KafkaSender {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private Gson gson = new GsonBuilder().create();

	// 发送消息方法
	public void send(User user) {
		System.out.println("+++++++++++++++++++++  message = {" + gson.toJson(user) + "}");
		kafkaTemplate.send("test", gson.toJson(user));
	}
}