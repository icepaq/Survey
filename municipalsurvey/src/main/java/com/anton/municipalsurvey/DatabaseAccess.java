package com.anton.municipalsurvey;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//This class takes care of all MySQL connections and other logic that has to do with database connections.
public class DatabaseAccess {
	
	private int temp_index; //See exists()
	
	
	//Sets the code completion column to true
	public String surveyComplete(String code) throws SQLException {
		
		String query = "UPDATE codes SET complete = true WHERE code  = ?";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		ResultSet rs;
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, code);
		
		try {
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return "";
	}
	
	//If the survey has codes enabled then a randomly generated 8 character alphanumeric string is passed
	public String getEndCode(String survey, String ip_address) throws SQLException {
		
		String query = "SELECT * FROM surveys WHERE survey_name = ?";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		ResultSet rs;
		PreparedStatement stmt = conn.prepareStatement(query);
		
		String code = "NULL";
		stmt.setString(1, survey);
		
		try {
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt(5) == 1) { 
					
					SecureRandom sr = new SecureRandom();
					byte bytes[] = new byte[8];
					
					sr.nextBytes(bytes);
					code = bytes.toString().substring(3).toUpperCase(); //Substring removes the SecureRandom constant.
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		//Now that the code has been generated, add it to the database
		String code_query = "INSERT INTO codes VALUES(NULL, ?, ?, ?, ?, NOW())";
		PreparedStatement stmt2 = conn.prepareStatement(code_query);
		
		stmt2.setString(1, code);
		stmt2.setString(2, survey);
		stmt2.setString(3, ip_address);
		stmt2.setBoolean(4, false); //The code is currently marked as incomplete. Once the survey is completed, the code will be marked complete.
		
		try {
			stmt2.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		if(conn != null) {
			conn.close();
		}
		
		return code;
	}
	
	//Change end message
	public String updateEndMessage(String survey, String message) throws SQLException {
		
		String query = "UPDATE surveys SET survey_end_message = ? WHERE survey_name = ?";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, message);
		stmt.setString(2, survey);
		
		try {
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		return "SUCCESS";
	}
	
	public String getEndMessage(String survey) throws SQLException {
		
		String query = "SELECT survey_end_message FROM surveys WHERE survey_name = ?";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs;
		
		stmt.setString(1, survey);
		
		String message = "";
		
		try {
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				
				message = rs.getString(1);
			}
		}
		catch(SQLException e) {
			
			System.out.println(e);
		}
		
		return message;
	}
	
	public String[][][] surveyResults() throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		ResultSet rs;
		Statement stmt = conn.createStatement();
		
		String[][] surveys = surveys(); // Get a list of surveys
		String[] queries = new String[surveys.length]; //Each query in this array will access a specific survey results table
		
		//Creating the queries to access each table
		for(int i = 0; i < surveys.length; i++) {
			queries[i] = "SELECT * FROM answers WHERE survey_name = '" + surveys[i][0] + "'";
		}
		
		//Checks the highest amount of entries that any table has
		int entries = 0;
		for(int a = 0; a < surveys.length; a++) {
			try {
				rs = stmt.executeQuery(queries[a]);
				
				int size = 0;
				while(rs.next()) {
					size++;
				}
				
				if(size > entries) {
					entries = size;
				}
				
			}catch(SQLException e) {
				System.out.println(e);
			}
		}
		
		String[][][] results = new String[surveys.length][entries][2]; //Initiating 3D array. [surveys][entries][questions, answers]
		
		//Go over each survey
		for(int b = 0; b < surveys.length; b++) {
			
			try {
				
				rs = stmt.executeQuery(queries[b]); //Execute the appropriate query for each table
				int counter = 0;
				while(rs.next()) {
					
					results[b][counter][0] = rs.getString(3); //Question 
					results[b][counter][1] = rs.getString(4); //Answer
					counter++;
				}
			}
			catch(SQLException e) {
				System.out.print(e);
			}
		}
		
		
		/* The following 2 step sorting algorithm takes the raw data from results and makes it easily accessible for Thymeleaf */
		
		String[][][] sorted_results = new String[surveys.length][entries][3]; //Each survey, largest amount of entries made on any of the surveys
		
		for(int survey_counter = 0; survey_counter < surveys.length; survey_counter++) { //Goes over each survey

			String[][] sorted_results_cache = new String[entries][2]; //Made for checking duplicate entries
			
			//Goes over each entry
			for(int entry_counter = 0; entry_counter < results[survey_counter].length; entry_counter++) { 
				
				if(exists(results[survey_counter][entry_counter], sorted_results_cache)) { 
						
					//Increment the results counter by 1
					int count = Integer.parseInt(sorted_results[survey_counter][temp_index][2]) + 1;
					sorted_results[survey_counter][temp_index][2] = Integer.toString(count);
				}
				else { //If the entry does not already exist, add it.
					
					if(results[survey_counter][entry_counter][0] != null) {
						
						//Add the question/answer combo into the array	
						sorted_results[survey_counter][entry_counter][0] = results[survey_counter][entry_counter][0];
						sorted_results[survey_counter][entry_counter][1] = results[survey_counter][entry_counter][1];
						sorted_results[survey_counter][entry_counter][2] = "1";
						
						sorted_results_cache[entry_counter][0] = results[survey_counter][entry_counter][0];
						sorted_results_cache[entry_counter][1] = results[survey_counter][entry_counter][1];
					}
				}
			}
		}
			
		/* Step 2. Adding question and answer to  */
		String[][][] final_results = new String[surveys.length][entries][3];
		
		/* for(int survey = 0; survey < surveys.length; survey++) {
			
			for()
		} */
		return sorted_results;
	}
	
	//Check if an entry is in the sorted_results
	private boolean exists(String[] entry, String[][] sorted_results) {
		for(int i = 0; i < sorted_results.length; i++) { //Go through each entry in sorted_results
			
			//Avoid crashing when sorted_results is empty
			try {
				if(sorted_results[i][0].equals(entry[0])) { //Check if the question has already been added
					
					if(sorted_results[i][1].equals(entry[1])) { //Check if an answer to that question has already been added
						
						temp_index = i; //i is the entry that is being checked. Used by survey_results to append the question/answer combo count
						return true;
					}
				}
			}
			catch(NullPointerException e) {
				
			}
		}
		return false;
	}

	public String[][] surveys() throws SQLException {
		
		String query = "SELECT * FROM survey_db.surveys";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		ResultSet rs;
		Statement stmt = conn.createStatement();
		
		int size = 0;
		try {
			rs = stmt.executeQuery(query);
			
			//Find rows in table
			while(rs.next()) {
				size++;
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		String[][] surveys = new String[size][2];
		try {
			rs = stmt.executeQuery(query);
			
			int counter = 0;
			while(rs.next()) {
				surveys[counter][0] = rs.getString(2).toUpperCase();
				surveys[counter][1] = "tab(survey_id=" + Integer.toString(counter) + ")";
				counter++;
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
	
	//Deletes a survey by removing the survey entry from the surveys table and, dropping the survey's table.
	public String deleteSurvey(String survey) throws SQLException {
		String query = "DELETE FROM surveys WHERE survey_name = ?"; //Delete Survey Entry
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, survey);
		
		try {
			stmt.executeUpdate(); 
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		String query2 = "DELETE FROM questions WHERE survey_name = ?";
		stmt = conn.prepareStatement(query2);
		stmt.setString(1, survey);
		
		try {
			stmt.executeUpdate(); 
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		if(conn != null) {
			conn.close();
		}
		
		return "";
	}
	
	//Create a survey
	public String addSurvey(String name, String message, String end_message, String end_code) throws SQLException {
		
		/* Checks if `name` is a duplicate entry */
		String queryEntryCheck = "SELECT * FROM surveys WHERE survey_name = ?";
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		PreparedStatement EntryCheckStatement = conn.prepareStatement(queryEntryCheck);
		ResultSet rs;
		
		EntryCheckStatement.setString(1, name);
		
		try {
			rs = EntryCheckStatement.executeQuery();
			
			//If the entry is a duplicate then abort
			if(rs.isBeforeFirst()) {
				System.out.println("Duplicate Survey Entry");
				conn.close();
				return "ERROR - DUPLICATE ENTRY";
			}
		}catch(SQLException e) {
			System.out.println("Line 51: " + e);
		}
		
		
		/* Create a survey entry */
		String query = "INSERT INTO surveys VALUES(NULL, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, name);
		stmt.setString(2, message);
		stmt.setString(3, end_message);
		
		if(end_code.equals("true")) {
			stmt.setInt(4, 1);
		} 
		else {
			stmt.setInt(4, 0);
		}
		
		try {
			System.out.println("Line 48: " + query);
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		finally {
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
				surveys[loop_counter][0] = rs.getString(2); //Survey name
				surveys[loop_counter][1] = rs.getString(3); //Survey message
				
				//These two variables are used for th:attr due to String limitations in Thymeleaf
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
	public String updateQuestion(String survey, String question_id, String question, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) throws SQLException {
		
		String answer = answer1 + "|" + answer2 + "|" + answer3 + "|" + answer4 + "|" + answer5 + "|" + answer6; //Concatenating several variables into one string for database
		String query = "UPDATE survey_db." + survey + " SET question = ?, answers = ? WHERE question_id = ?";
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
		
		String query = "SELECT * FROM questions WHERE survey_name = ?";
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		ResultSet rs;
		
		PreparedStatement stmt;
		
		
		
		//Finding rows of result
		int size = 0;
		try {
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, survey);
			rs = stmt.executeQuery();
			
			//Find rows in table
			while(rs.next()) {
				size++;
			}
			System.out.println("Array Size is: " + size);
		} catch(SQLException e) {
			System.out.println(e);
		}
		
		String[][] questions = new String[size][6];
		
		//Appending table values into 2D array
		try {
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, survey);
			rs = stmt.executeQuery();
			
			int loop_counter = 0;
			while(rs.next()) {
				questions[loop_counter][0] = rs.getString(1);
				questions[loop_counter][1] = rs.getString(3);
				questions[loop_counter][2] = rs.getString(4);
				questions[loop_counter][3] = rs.getString(5);
				
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
		
		System.out.println("---------------RESULTS FROM QUESTION_ANSWERS----------------");
		
		for(int a = 0; a < questions.length; a++) {
			
			for(int b = 0; b < questions[a].length; b++) {
				System.out.println(questions[a][b]);
			}
			System.out.println("------------------------");
		}
		return questions;
		
	}
	
	//Submit an answer selected in a survey
	public String submit_answer(String ip_address, String question, String answer, String code, String survey) throws SQLException {
		
		//String q = "INSERT INTO survey_db." + survey + "_answers VALUES(NULL, '" + question + "', '" + answer + "', '" + survey + "', '" + user_id + "', NOW());";
		String query = "INSERT INTO survey_db.answers VALUES(NULL, ?, ?, ?, ?, ?, NOW())";
		System.out.println(query);
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "spring_user", "Java_test.");
		PreparedStatement stmt = conn.prepareStatement(query);
		
		System.out.println(code);
		
		stmt.setString(1, survey);
		stmt.setString(2, question);
		stmt.setString(3, answer);
		stmt.setString(4, ip_address);
		stmt.setString(5, code);
		
		
		try {
			stmt.executeUpdate();	
		}catch(SQLException e) {
			System.out.println(e);
			return "SQL_ERROR";
		}
		
		
		
		return "SUCCESS";
	}
	
	//Submit a new question
	public String newQuestion(String survey, String question, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) throws SQLException {
		
		String query = "INSERT INTO questions VALUES(NULL, ?, ?, ?, ?)";
		System.out.println("Line 249: " + query);
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, survey);
		stmt.setString(2, "MC");   //Temporarily multiple choice is the only question type
		stmt.setString(3, question);
		stmt.setString(4, answer1 + "|" + answer2 + "|" + answer3 + "|" + answer4 + "|" + answer5 + "|" + answer6); //Currently up to 6 MC options are available
		
		
		try {
			stmt.executeUpdate();
		} 	
		catch(SQLException e) {
			System.out.println(e);
			return "SQL_ERROR";
		} 
		finally {
			if(conn != null) {
				conn.close();
			}
		}
		
		
		return "SUCCESS";
	}
	
	//Retrieves all questions in a survey
	public String[][] getQuestions(String survey) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/survey_db", "spring_user", "Java_test.");
		PreparedStatement stmt;
		ResultSet rs;
		String query = "SELECT * FROM questions WHERE survey_name = ?";
		
		//Finding rows of result
		int size = 0;
		try {
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, survey);
			rs = stmt.executeQuery();
			
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
			stmt = conn.prepareStatement(query);
			stmt.setString(1, survey);
			rs = stmt.executeQuery();
			
			int loop_counter = 0;
			while(rs.next()) {
				questions[loop_counter][0] = rs.getString(4); //The question
				questions[loop_counter][1] = rs.getString(5); //The answers
				questions[loop_counter][2] = rs.getString(3); //The question type
			
				loop_counter++;
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		
		
		String[][] question_answers = new String[size][8]; //This array will be passed into Thymeleaf with the question and answers
		String[] temp_answers = new String[6]; //Temporary holder for when String or answers will be split
		
		
		for(int row = 0; row < size; row++) {
			
			for(int column = 0; column < 2; column++) {
				
				question_answers[row][0] = questions[row][2]; //The question type
				question_answers[row][1] = questions[row][0]; //The question 
				temp_answers = questions[row][1].split("\\|");	 //Splitting up string of answers into an array
				
				for(int answers = 2; answers < 8; answers++) {
					
					//Try Catch will ignore out of bounds exceptions if temp_answers is too small
					try {
						question_answers[row][answers] = temp_answers[answers - 2]; //Adding answers to the array;
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
