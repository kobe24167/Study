package com.exmaple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exmaple.annotation.Log;

@RestController
public class SolutionController {
	
	@Autowired
	private Solution solution;

//	@Log(name = "index«Î«Û")
	@RequestMapping(value="/hello",method= RequestMethod.GET)
    public String index() {
		int[] nums = { 2, 7, 11, 15 };
		int target = 9;
		solution.twoSumExmaple(nums, target);
        return "index";
    }
}
