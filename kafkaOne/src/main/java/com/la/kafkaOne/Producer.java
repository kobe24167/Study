package com.la.kafkaOne;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class Producer {
	@Autowired
	private KafkaTemplate kafkaTemplate;

	private static Gson gson = new GsonBuilder().create();

	//发送消息方法
	public void send() {
	    Message message = new Message();
	    message.setId("KFK_"+System.currentTimeMillis());
	    message.setMsg(UUID.randomUUID().toString());
	    message.setSendTime(new Date());
	    kafkaTemplate.send("test", gson.toJson(message));
	}
}
