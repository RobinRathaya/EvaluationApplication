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

		String query = "SELECT et.id,et.empid,et.topicid,et.statusid,et.createdon,et.modifiedon FROM EV_EMPLOYEETOPICS et WHERE et.empid=?";
		Object[] parameters = new Object[] { employee.getId() };
		List<EmployeeTopics> employeeEvaluationDetails = jdbcTemplate
				.query(query,
						parameters,
						(resultSet, row) -> {

							EmployeeTopics employeeDetail = employeeTopicsIntailization(resultSet);
							employeeDetail.getEmployee().setName(
									employee.getName());
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
		EmployeeTopics employeeDetails = new EmployeeTopics();
		employeeDetails.topic = new Topics();
		employeeDetails.status = new Status();
		employeeDetails.employee = new Employee();
		employeeDetails.employee.setId(resultSet.getInt("et.empid"));
		employeeDetails.topic.setId(resultSet.getInt("et.topicid"));
		employeeDetails.status.setId(resultSet.getInt("et.statusid"));
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

	public List<EmployeeTopics> searchEvaluation() {
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
