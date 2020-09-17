package com.anton.municipalsurvey;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebController {
	
	DatabaseAccess a = new DatabaseAccess();
	
	@RequestMapping(value = "/surveyComplete")
	@ResponseBody
	public String surveyComplete(@RequestParam String code) throws SQLException {
		
		return a.surveyComplete(code);
	}
	
	@RequestMapping(value = "/updateEndMessage")
	@ResponseBody
	public String updateEndMEssage(@RequestParam String survey, @RequestParam String message) throws SQLException {
		
		return a.updateEndMessage(survey, message);
	}
	
	@RequestMapping(value = "/value")
	@ResponseBody
	public String value(@RequestParam String value) {
		
		System.out.println();
		return "";
	}
	
	@RequestMapping(value = "/results")
	public String results(Model model) throws SQLException {
		
		model.addAttribute("surveys", a.surveys());
		model.addAttribute("results", a.surveyResults());
		a.getQuestions();
		return "results";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		
		return "login";
	}
	
	//Delete Survey
	@RequestMapping(value = "/delete_survey")
	@ResponseBody
	public String delete_survey(@RequestParam String survey) throws SQLException{
		
		return a.deleteSurvey(survey);
	}
	
	//New survey
	@RequestMapping(value = "/new_survey")
	@ResponseBody
	public String new_survey(@RequestParam String survey_name, @RequestParam String survey_message, @RequestParam String survey_end_message, @RequestParam String survey_end_code) throws SQLException {
		
		System.out.println(survey_end_code);
		return a.addSurvey(survey_name, survey_message, survey_end_message, survey_end_code);
	}
	
	//Home Page
	@RequestMapping(value = "/")
	public String index(Model model) throws SQLException{
		
		try {
			
			model.addAttribute("surveys", a.getSurveys());
		}
		catch(SQLException e) {
			
			System.out.println(e);
			return "mysqlerror";
		}
		
		return "index";	
	}
	
	//Survey Page
	@RequestMapping(value = "/survey")
	public String survey(Model model, @RequestParam String survey, HttpServletRequest request) throws SQLException {

		model.addAttribute("questions", a.getQuestions(survey));
		model.addAttribute("survey", survey);
		model.addAttribute("end_message", a.getEndMessage(survey));
		model.addAttribute("code", a.getEndCode(survey, request.getRemoteAddr()));
		
		
		return "survey";
	}
	
	//Survey Management
	@RequestMapping(value = "/surveys")
	public String surveys(Model model) throws SQLException {
		
		model.addAttribute("surveys", a.getSurveys());
		return "surveys";
	}
	
	@RequestMapping(value = "/survey_control")
	public String create_survey(Model model, @RequestParam String survey) throws SQLException {
		
		model.addAttribute("survey", survey);
		model.addAttribute("questions", a.question_answers(survey)); //Stores answers from selected survey table passing as a GET/POST Parameter
		model.addAttribute("question_answers", a.question_answers(survey)); //Similar to getAnswers but retrieves additional indexes for Thymeleaf tags
		return "create_survey";
	}
	
	//Processing New Questions
	@RequestMapping(value = "/submit_changes")
	@ResponseBody
	public String submit_changes(@RequestParam String survey, @RequestParam String question, @RequestParam String answer1, @RequestParam String answer2, @RequestParam String answer3, @RequestParam String answer4, @RequestParam String answer5, @RequestParam String answer6) throws SQLException {
		
		return a.newQuestion(survey, question, answer1, answer2, answer3, answer4, answer5, answer6);
	}
	
	//Submit Answer
	@RequestMapping(value = "/submit_answer")
	@ResponseBody
	public String submit_answer(@RequestParam String question, @RequestParam String answer, @RequestParam String code, @RequestParam String survey, HttpServletRequest request) throws SQLException {
		
		a.submit_answer(request.getRemoteAddr(), question, answer, code, survey);
		System.out.println("WebController.submit_answer 82: " + survey);
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/submit_question_changes")
	@ResponseBody
	public String submit_question_changes(@RequestParam String survey, @RequestParam String question_id, @RequestParam String question, @RequestParam String answer1, @RequestParam String answer2, @RequestParam String answer3, @RequestParam String answer4, @RequestParam String answer5, @RequestParam String answer6) throws SQLException {
		System.out.println("/submit_question_changes");
		return a.updateQuestion(survey, question_id, question, answer1, answer2, answer3, answer4, answer5, answer6);
	}	
	
	@RequestMapping(value = "/delete_question")
	@ResponseBody
	public String delete_question(@RequestParam String question_id) throws SQLException {
		
		return a.deleteQuestion(question_id);
	}
}
	