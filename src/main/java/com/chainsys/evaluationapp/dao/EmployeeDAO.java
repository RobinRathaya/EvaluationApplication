package com.chainsys.evaluationapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.model.EmployeeTopics;

@Repository
public class EmployeeDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Employee> fetchUsers() {
		String query = "SELECT id,name FROM EV_EMPLOYEE";
		List<Employee> userDetails = jdbcTemplate
				.query(query,
						(resultSet, row) -> {

							Employee userInfo = EmployeeDetailInitialization(resultSet);
							return userInfo;

						});
		return userDetails;
	}

	private Employee EmployeeDetailInitialization(ResultSet resultSet)
			throws SQLException {
		// TODO Auto-generated method stub
		Employee employee = new Employee();
		employee.setId(resultSet.getInt("id"));
		employee.setName(resultSet.getString("name"));
		return employee;
	}

	/**
	 * 
	 * @param employee
	 * @throws Exception
	 * @return Void
	 */
	public int addEmployee(Employee employee) throws Exception {

		String query = "INSERT INTO EV_EMPLOYEE values(?,?,?,?)";

		Object[] parameters = new Object[] { employee.getId(),
				employee.getName(), employee.getEmail(), employee.getPassword() };
		int addEmployeeResult = jdbcTemplate.update(query, parameters);
		return addEmployeeResult;

	}

	public int addEmployeeStatus(EmployeeTopics employeeTopics) {
		String query = "INSERT INTO EV_EMPLOYEETOPICS(empid,topicid,statusid,createdon,modifiedon) values (?,?,?,?,?)";
		Object[] parameters = new Object[] {
				employeeTopics.getEmployee().getId(),
				employeeTopics.getTopic().getId(),
				employeeTopics.getStatus().getId(),
				employeeTopics.getCreatedOn(), employeeTopics.getUpdatedOn() };

		int insertEmployeeStatus = jdbcTemplate.update(query, parameters);
		return insertEmployeeStatus;

	}

	public int updateEmployeeStatus(EmployeeTopics employeeTopics) {
		String query = "UPDATE EV_EMPLOYEETOPICS SET statusid=? ,modifiedon=? WHERE empid=? and topicid=?";
		Object[] parameters = new Object[] {

		employeeTopics.getStatus().getId(), employeeTopics.getUpdatedOn(),
				employeeTopics.getEmployee().getId(),
				employeeTopics.getTopic().getId() };

		int updateEmployeeStatus = jdbcTemplate.update(query, parameters);
		return updateEmployeeStatus;

	}

	public Employee searchEmployeeName(int id) {
		String query = "SELECT id,name FROM EV_EMPLOYEE WHERE id=?";
		Object[] parameters = new Object[] { id };
		Employee employeeDetails = jdbcTemplate
				.queryForObject(
						query,
						parameters,
						(resultSet, row) -> {

							Employee employeeObj = employeeNameInitialization(resultSet);
							return employeeObj;

						});
		return employeeDetails;
	}

	public Employee employeeNameInitialization(ResultSet resultSet)
			throws SQLException {
		Employee employee = new Employee();
		employee.setId(resultSet.getInt("id"));
		employee.setName(resultSet.getString("name"));
		return employee;
	}

		
}
