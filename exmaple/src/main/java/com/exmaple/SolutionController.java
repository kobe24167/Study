package com.exmaple;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exmaple.annotation.Log;

@RestController
public class SolutionController {

	@Log(name = "index����")
	@RequestMapping(value="/hello",method= RequestMethod.GET)
    public String index() {
        return "index";
    }
}
