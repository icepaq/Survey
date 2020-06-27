package com.digitalcanada.municipalsurvey;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	
	//Home Page
	@RequestMapping(value = "/")
	public String index() {
		
		return "index";
	}
	
	//English Survey
	@RequestMapping(value = "/english")
	public String english() {
		
		return "english";
	}
	
	
	

}
