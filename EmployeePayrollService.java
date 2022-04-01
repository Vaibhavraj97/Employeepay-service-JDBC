package com.bridgelabz.JDBC;


import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class EmployeePayrollService {
	 public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}

	    private List<EmployeePayrollData> employeePayrollList;
	    private static EmployeePayrollDBService employeePayrollDBService;

	    public EmployeePayrollService() {
	        employeePayrollDBService = EmployeePayrollDBService.getInstance();
	    }

	    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList){
	        this();
	        this.employeePayrollList = employeePayrollList;
	    }
	    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioservice) {
	        if (ioservice.equals(IOService.DB_IO)) {
	            this.employeePayrollList = employeePayrollDBService.readData();
	        }
	        return this.employeePayrollList;
	    }

	    public void updateEmployeeSalary(String name, double salary) {
	        int result = employeePayrollDBService.updateEmployeeData(name, salary);
	        if (result == 0) return;
	        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
	        if (employeePayrollData != null) employeePayrollData.salary = salary;
	    }

	    public EmployeePayrollData getEmployeePayrollData(final String name) {
	        return this.employeePayrollList.stream()
	                .filter(new Predicate<EmployeePayrollData>() {
						@Override
						public boolean test(EmployeePayrollData employeePayrollData) {
							return employeePayrollData.name.equals(name);
						}
					})
	                .findFirst()
	                .orElse(null);
	    }

	    public boolean checkEmployeeInSyncWithDB(String name) {
	        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
	        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	    }
	    public List<EmployeePayrollData> getEmployeePayrollDataByGivenDataRange(LocalDate startDate, LocalDate endDate) {
	        return employeePayrollDBService.getEmployeePayrollDataByDataRange(startDate,endDate);
	    }
}