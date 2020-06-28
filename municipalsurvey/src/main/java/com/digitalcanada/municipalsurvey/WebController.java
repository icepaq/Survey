package com.digitalcanada.municipalsurvey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String english(Model model) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		//Finding rows of result
		int size = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM survey_db.questions");
			
			//Find rows in table
			while(rs.next()) {
				size++;
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		String[][] questions = new String[size][4];
		
		//Appending table values into 2D array
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM survey_db.questions");
			
			int loop_counter = 0;
			while(rs.next()) {
				questions[loop_counter][0] = rs.getString(1);
				questions[loop_counter][1] = rs.getString(2);
				questions[loop_counter][2] = rs.getString(3);
				questions[loop_counter][3] = rs.getString(4);
				loop_counter++;
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		
		String[][] question_answers = new String[size][7]; //This array will be passed into Thymeleaf with the question and answers
		String[] temp_answers = new String[6]; //Temporary holder for when String or answers will be split
		
		
		for(int row = 0; row < size; row++) {
			
			for(int column = 0; column < 2; column++) {
				
				question_answers[row][0] = questions[row][2]; //Make the first index of the sub-array the question
				
				temp_answers = questions[row][3].split("\\|"); //Splitting up string of answers into an array
				
				for(int answers = 1; answers < 7; answers++) {
					
					//Try Catch will ignore out of bounds exceptions if temp_answers is too small
					try {
						question_answers[row][answers] = temp_answers[answers-1]; //Adding answers to the array;
					}catch(ArrayIndexOutOfBoundsException e) {
						System.out.println(e);
					}
					
					
				}
			}
		}
		
		for(int a = 0; a < question_answers.length; a++) {
			for(int b = 0; b < 7; b++) {
				System.out.println(question_answers[a][b]);
			}
			System.out.println();
			System.out.println();
		}
		
		
		
		model.addAttribute("questions", question_answers);
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
	