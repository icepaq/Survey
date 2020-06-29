package com.digitalcanada.municipalsurvey;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebController {
	
	DatabaseAccess a = new DatabaseAccess();
	
	//Home Page
	@RequestMapping(value = "/")
	public String index() {
		
		return "index";
	}
	
	//English Survey
	@RequestMapping(value = "/english")
	public String english(Model model) throws SQLException {

		model.addAttribute("questions", a.getAnswers());
		return "english";
	}
	
	@RequestMapping(value = "/create_survey")
	public String create_survey() {
		
		return "create_survey";
	}
	
	//Processing New Questions
	@RequestMapping(value = "/submit_changes")
	@ResponseBody
	public String submit_changes(@RequestParam String question, @RequestParam String answer1, @RequestParam String answer2, @RequestParam String answer3, @RequestParam String answer4, @RequestParam String answer5, @RequestParam String answer6) throws SQLException {
		
		return a.submit_changes(question, answer1, answer2, answer3, answer4, answer5, answer6);
	}
	
	@RequestMapping(value = "/submit_answer")
	@ResponseBody
	public String submit_answer(@RequestParam String question, @RequestParam String answer, HttpServletRequest request) throws SQLException {
		
		a.submit_answer(request.getRemoteAddr(), question, answer);
		return "SUCCESS";
	}
}
	