package com.bridgelabz.JDBC;


	import java.sql.*;
	import java.text.DateFormat;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Scanner;
	public class EmployeePayrollDBService {

	    Connection connection = null;
	    Statement statement = null;
	    Scanner scanner = new Scanner(System.in);

	    private Connection getConnection() throws SQLException {
	        String dbURL = "jdbc:mysql://localhost:3306/payroll_service";
	        String userName="root";
	        String password="Rajuskarvaibh97#";
	        Connection connection;
	        connection = DriverManager.getConnection(dbURL,userName,password);
	        System.out.println(" Database connection is successfull");
	        return connection;
	    }

	    public List<EmployeePayrollData> readData() {
	        String query="SELECT * from payroll_service;";
	        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
	        try {
	            Connection connection = this.getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(query);
	            while (resultSet.next()){
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                double salary = resultSet.getDouble("salary");
	                LocalDate startDate = resultSet.getDate("start").toLocalDate();
	                employeePayrollList.add(new EmployeePayrollData(id,name,salary,startDate));
	            }
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return employeePayrollList;
	    }
	    public static void main(String[] args) throws ParseException {
	        EmployeePayrollDBService employeePayrollService=new EmployeePayrollDBService();
	        employeePayrollService.readData();
	    }
	    public int updateEmployeeData(String name, double salary) {
	    	System.out.println(this.updateDataUsingStatement(name, salary));
	        return this.updateDataUsingStatement(name, salary);
	    }

	    private int updateDataUsingStatement(String name, double salary) {
	        String query = String.format("update payroll_service set salary = %.2f where name= '%s';", salary, name);
	        try (Connection connection = this.getConnection()) {
	            Statement statement = connection.createStatement();
	            return statement.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
	        List<EmployeePayrollData> employeePayrollList = null;
	        if (this.employeePayrollDataStatement == null) {
	            this.prepareStatementForEmployeeData();
	        }
	        try {
	            employeePayrollDataStatement.setString(1, name);
	            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
	            employeePayrollList = this.getEmployeePayrollData(resultSet);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return employeePayrollList;
	    }

	    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
	        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
	        try {
	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                double salary = resultSet.getDouble("salary");
	                LocalDate startDate = resultSet.getDate("start").toLocalDate();
	                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return employeePayrollList;
	    }

	    private void prepareStatementForEmployeeData() {
	        try {
	            connection = this.getConnection();
	            String sql = "SELECT * FROM payroll_service WHERE name = ?";
	            employeePayrollDataStatement = connection.prepareStatement(sql);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	

