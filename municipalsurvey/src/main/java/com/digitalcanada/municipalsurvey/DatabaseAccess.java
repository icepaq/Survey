package com.digitalcanada.municipalsurvey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccess {
	
	public String submit_answer(String user_id, String question, String answer) throws SQLException {
		
		String query = "INSERT INTO survey_db.answers VALUES(NULL, '" + user_id + "', '" + question + "', '" + answer + "');";
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
	public String submit_changes(String question, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) throws SQLException {
		String query = "INSERT INTO survey_db.questions VALUES(NULL, 1, '" + question + "', " + "'" + answer1 + "|" + answer2 + "|"  + answer3 + "|" + answer4 + "|" + answer5 + "|" + answer6 + "');";
		
		
		
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
	
	public String[][] getAnswers() throws SQLException {
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
		
		return question_answers;
	}

}
