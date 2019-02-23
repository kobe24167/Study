package com.la.ms1;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EnableAutoConfiguration
@RestController
public class Backend {
	@Autowired
	private OkHttpClient okHttpClient;

  @RequestMapping("/api")
  public String printDate(@RequestHeader(name = "user-name", required = false) String username) {
    
    Response response = null;
    try {
    	Request resquest = new Request.Builder().url("http://localhost:8002/ms2/api").build();
    	response = okHttpClient.newCall(resquest).execute();
    	return response.toString();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (response != null && response.body() != null) {
			response.body().close();
		}
	}
    return "";
  }
}
