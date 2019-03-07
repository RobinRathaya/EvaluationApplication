package com.chainsys.evaluationapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.model.EmployeeTopics;
import com.chainsys.evaluationapp.model.Status;
import com.chainsys.evaluationapp.model.Topics;

@Repository
public class EmployeeTopicsDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	TopicsDAO topicsDAO;

	@Autowired
	StatusDAO statusDAO;
	

	/**
	 * 
	 * @return {@link List}
	 * @throws Exception
	 */
	public List<EmployeeTopics> searchEvaluationById(Employee employee)
			throws Exception {
			
		//TODO Search the Evaluation detail by employee id
		
		String query = "SELECT et.id,et.empid,et.topicid,et.statusid,et.createdon,et.modifiedon FROM EV_EMPLOYEETOPICS et WHERE et.empid=?";
		Object[] parameters = new Object[] { employee.getId() };
		List<EmployeeTopics> employeeEvaluationDetails = jdbcTemplate
				.query(query,
						parameters,
						(resultSet, row) -> {
							
							EmployeeTopics employeeDetail = employeeTopicsIntailization(resultSet);
							return employeeDetail;

						});
		return employeeEvaluationDetails;

	}

	/**
	 * 
	 * @param resultSet
	 * @return {@link EmployeeTopics}
	 * @throws SQLException
	 */
	private EmployeeTopics employeeTopicsIntailization(ResultSet resultSet)
			throws SQLException {
		
		//TODO Initialize employeeTopics
		
		EmployeeTopics employeeDetails = new EmployeeTopics();
		Topics topic = new Topics();
		Status status = new Status();
		Employee employee = new Employee();
		employee.setId(resultSet.getInt("et.empid"));
		topic.setId(resultSet.getInt("et.topicid"));
		status.setId(resultSet.getInt("et.statusid"));
		employeeDetails.setEmployee(employee);
		employeeDetails.setTopic(topic);
		employeeDetails.setStatus(status);
		employeeDetails.setCreatedOn(resultSet.getTimestamp("createdon")
				.toLocalDateTime());
		if (resultSet.getObject("modifiedon") != null) {
			employeeDetails.setUpdatedOn(resultSet.getTimestamp("modifiedon")
					.toLocalDateTime());
		} else {
			employeeDetails.setUpdatedOn(null);
		}
		return employeeDetails;
	}
	
	/**
	 * 
	 * @return {@link List}
	 */

	public List<EmployeeTopics> searchEvaluation() {
		
		//TODO Search Evaluation Details
		
		String query = "SELECT et.id,et.empid,et.topicid,et.statusid,et.createdon,et.modifiedon FROM EV_EMPLOYEETOPICS et";

		List<EmployeeTopics> employeeEvaluationDetails = jdbcTemplate
				.query(query,

						(resultSet, row) -> {

							EmployeeTopics employeeDetail = employeeTopicsIntailization(resultSet);
							return employeeDetail;

						});
		return employeeEvaluationDetails;

	}
	
	

}
