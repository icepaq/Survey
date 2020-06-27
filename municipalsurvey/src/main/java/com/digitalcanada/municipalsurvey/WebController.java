package com.digitalcanada.municipalsurvey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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
	
	@RequestMapping(value = "/create_survey")
	public String create_survey() {
		
		return "create_survey";
	}
	
	//Processing New Questions
	@RequestMapping(value = "/submit_changes")
	@ResponseBody
	public String submit_changes(@RequestParam String question, @RequestParam String answer1, @RequestParam String answer2, @RequestParam String answer3, @RequestParam String answer4, @RequestParam String answer5, @RequestParam String answer6) throws SQLException {
		
		String query = "INSERT INTO survey_db.questions VALUES(NULL, 1, '" + question + "', " + "'" + answer1 + "|" + answer2 + "|"  + answer3 + "|" + answer4 + "|" + answer5 + "|" + answer6 + "');";
		System.out.println(query);
		
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		Statement stmt = conn.createStatement();
		
		try {
			stmt.executeUpdate(query);
		}catch(SQLException e) {
			System.out.println(e);
			return "SQL_ERROR";
		} 
		
		
			
		return "SUCCESS";
	}
}
	