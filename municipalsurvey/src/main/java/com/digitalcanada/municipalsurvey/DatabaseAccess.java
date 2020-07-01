package com.digitalcanada.municipalsurvey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccess {
	
	public String addSurvey(String name, String message) throws SQLException {
		
		String query = "INSERT INTO surveys VALUES(NULL, ?, ?)";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, name);
		stmt.setString(2, message);
		
		try {
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		String query2 = "CREATE TABLE " + name + "(question_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, question_mc BOOL, question VARCHAR(60), answers VARCHAR(100))";
		
		try {
			stmt.executeUpdate(query2);
		}catch(SQLException e) {
			System.out.println(e);
		}finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return "SUCCESS";
	}

	//Get all surveys
	public String[][] getSurveys() throws SQLException {
		
		String query = "SELECT * FROM survey_db.surveys";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		int size = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			//Find rows in table
			while(rs.next()) {
				size++;
			}
			
		} catch(SQLException e) {
			System.out.println(e);
		}
		
		String[][] surveys = new String[size][4];
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			int loop_counter = 0;
			while(rs.next()) {
				surveys[loop_counter][0] = rs.getString(2);
				surveys[loop_counter][1] = rs.getString(3);
				surveys[loop_counter][2] = "survey_select('" + surveys[loop_counter][0] + "')";
				surveys[loop_counter][3] = "survey_delete('" + surveys[loop_counter][0] + "')";
				
				loop_counter++;
			}
		} catch(SQLException e) {
			System.out.println(e);
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return surveys;
	}
	
	//Delete a question
	public String deleteQuestion(String question_id) throws SQLException {
		
		String query = "DELETE FROM survey_db.questions WHERE question_id = ?";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, question_id);
		
		try{
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
			return "SQL_ERROR";
		}finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return "SUCCESS";
	}
	
	//Change properties of a question
	public String updateQuestion(String question_id, String question, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) throws SQLException {
		
		String answer = answer1 + "|" + answer2 + "|" + answer3 + "|" + answer4 + "|" + answer5 + "|" + answer6; //Concatenating several variables into one string for database
		String query = "UPDATE survey_db.questions SET question = ?, answers = ? WHERE question_id = ?";
		System.out.println(query);
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, question);
		stmt.setString(2, answer);
		stmt.setString(3, question_id);
		
		try {
			stmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
			return "SQL_ERROR";
		}finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return "SUCCESS";
	}
	
	//Get all questions. Gives more information than just database values/
	public String[][] question_answers(String survey) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		//Finding rows of result
		int size = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM survey_db." + survey);
			
			//Find rows in table
			while(rs.next()) {
				size++;
			}
			
		} catch(SQLException e) {
			System.out.println(e);
		}
		
		String[][] questions = new String[size][6];
		
		//Appending table values into 2D array
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM survey_db." + survey);
			
			int loop_counter = 0;
			while(rs.next()) {
				questions[loop_counter][0] = rs.getString(1);
				questions[loop_counter][1] = rs.getString(2);
				questions[loop_counter][2] = rs.getString(3);
				questions[loop_counter][3] = rs.getString(4);
				
				questions[loop_counter][4] = questions[loop_counter][0] + "_question"; //This variable is used to identify the question being referenced in /create_survey
				questions[loop_counter][5] = questions[loop_counter][0] + "_answer";
				
				loop_counter++;
			}
		} catch(SQLException e) {
			System.out.println(e);
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return questions;
		
	}
	
	//Submit an answer selected in a survey
	public String submit_answer(String user_id, String question, String answer) throws SQLException {
		
		String query = "INSERT INTO survey_db.answers VALUES(NULL, '" + user_id + "', '" + question + "', '" + answer + "', NOW());";
		System.out.println(query);
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		Statement stmt = conn.createStatement();
		
		try {
			stmt.executeUpdate(query);	
		}catch(SQLException e) {
			System.out.println(e);
			return "SQL_ERROR";
		}finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return "SUCCESS";
	}
	
	//Submit a new question
	public String newQuestion(String question, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) throws SQLException {
		String query = "INSERT INTO survey_db.questions VALUES(NULL, 1, '" + question + "', " + "'" + answer1 + "|" + answer2 + "|"  + answer3 + "|" + answer4 + "|" + answer5 + "|" + answer6 + "');";
		
		
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		Statement stmt = conn.createStatement();
		
		try {
			stmt.executeUpdate(query);
		} 	catch(SQLException e) {
			System.out.println(e);
			return "SQL_ERROR";
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		
		return "SUCCESS";
	}
	
	public String[][] getAnswers(String survey) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		Statement stmt = conn.createStatement();
		ResultSet rs;
		
		//Finding rows of result
		int size = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM survey_db." + survey);
			
			//Find rows in table
			while(rs.next()) {
				size++;
			}
			
		} catch(SQLException e) {
			System.out.println(e);
		}
		
		String[][] questions = new String[size][4];
		
		//Appending table values into 2D array
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM survey_db." + survey);
			
			int loop_counter = 0;
			while(rs.next()) {
				questions[loop_counter][0] = rs.getString(1);
				questions[loop_counter][1] = rs.getString(2);
				questions[loop_counter][2] = rs.getString(3);
				questions[loop_counter][3] = rs.getString(4);
				
				loop_counter++;
			}
		} catch(SQLException e) {
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
					} catch(ArrayIndexOutOfBoundsException e) {
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
		if(conn != null) {
			conn.close();
		}
		
		return question_answers;
	}

}
