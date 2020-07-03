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
	
	@RequestMapping(value = "/delete_survey")
	@ResponseBody
	public String delete_survey(@RequestParam String survey) throws SQLException{
		
		return a.deleteSurvey(survey);
	}
	
	@RequestMapping(value = "/new_survey")
	@ResponseBody
	public String new_survey(@RequestParam String survey_name, @RequestParam String survey_message) throws SQLException {
		
		return a.addSurvey(survey_name, survey_message);
	}
	
	//Home Page
	@RequestMapping(value = "/")
	public String index(Model model) throws SQLException{
		
		model.addAttribute("surveys", a.getSurveys());
		return "index";
	}
	
	//English Survey
	@RequestMapping(value = "/survey")
	public String english(Model model, @RequestParam String survey) throws SQLException {

		model.addAttribute("questions", a.getAnswers(survey));
		return "english";
	}
	
	@RequestMapping(value = "/surveys")
	public String surveys(Model model) throws SQLException {
		
		model.addAttribute("surveys", a.getSurveys());
		return "surveys";
	}
	
	@RequestMapping(value = "/survey_control")
	public String create_survey(Model model, @RequestParam String survey) throws SQLException {
		
		model.addAttribute("survey", survey);
		model.addAttribute("questions", a.getAnswers(survey)); //Stores answers from selected survey table passing as a GET/POST Parameter
		model.addAttribute("question_answers", a.question_answers(survey)); //Similar to getAnswers but retrieves additional indexes for Thymeleaf tags
		return "create_survey";
	}
	
	//Processing New Questions
	@RequestMapping(value = "/submit_changes")
	@ResponseBody
	public String submit_changes(@RequestParam String survey, @RequestParam String question, @RequestParam String answer1, @RequestParam String answer2, @RequestParam String answer3, @RequestParam String answer4, @RequestParam String answer5, @RequestParam String answer6) throws SQLException {
		
		return a.newQuestion(survey, question, answer1, answer2, answer3, answer4, answer5, answer6);
	}
	
	@RequestMapping(value = "/submit_answer")
	@ResponseBody
	public String submit_answer(@RequestParam String question, @RequestParam String answer, HttpServletRequest request) throws SQLException {
		
		a.submit_answer(request.getRemoteAddr(), question, answer);
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/submit_question_changes")
	@ResponseBody
	public String submit_question_changes(@RequestParam String survey, @RequestParam String question_id, @RequestParam String question, @RequestParam String answer1, @RequestParam String answer2, @RequestParam String answer3, @RequestParam String answer4, @RequestParam String answer5, @RequestParam String answer6) throws SQLException {
		
		return a.updateQuestion(survey, question_id, question, answer1, answer2, answer3, answer4, answer5, answer6);
	}	
	
	@RequestMapping(value = "/delete_question")
	@ResponseBody
	public String delete_question(@RequestParam String question_id) throws SQLException {
		
		return a.deleteQuestion(question_id);
	}
}
	