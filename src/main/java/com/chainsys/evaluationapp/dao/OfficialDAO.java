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
public class OfficialDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<EmployeeTopics> searchEmployeeTopicsByName(Employee employee) {
		String query = "SELECT et.id,t.name,s.name,et.createdon,et.modifiedon FROM EV_EMPLOYEETOPICS et JOIN EV_EMPLOYEE e ON et.empid=e.id JOIN EV_TOPICS t ON et.topicid=t.topicid JOIN EV_STATUS s ON et.statusid=s.id WHERE e.name=?;";
		Object[] parameter = new Object[] { employee.getName() };
		List<EmployeeTopics> employeeTopicsList = jdbcTemplate
				.query(query,
						parameter,
						(resultSet, row) -> {
							EmployeeTopics employeeTopic = employeeTopicsIntailization(resultSet);
							return employeeTopic;

						});

		return employeeTopicsList;
	}

	public List<EmployeeTopics> searchEmployeeTopicsById(Employee employee) {
		String query = "SELECT et.id,t.name,s.name,et.createdon,et.modifiedon FROM EV_EMPLOYEETOPICS et JOIN EV_EMPLOYEE e ON et.empid=e.id JOIN EV_TOPICS t ON et.topicid=t.topicid JOIN EV_STATUS s ON et.statusid=s.id WHERE e.id=?;";
		Object[] parameter = new Object[] { employee.getId() };
		List<EmployeeTopics> employeeTopicsList = jdbcTemplate
				.query(query,
						parameter,
						(resultSet, row) -> {

							EmployeeTopics employeeTopic = employeeTopicsIntailization(resultSet);
							return employeeTopic;

						});

		return employeeTopicsList;
	}

	public List<EmployeeTopics> displayEvaluation() throws Exception {

		String query = "SELECT et.id,t.name,s.name,et.createdon,et.modifiedon FROM EV_EMPLOYEETOPICS et JOIN EV_EMPLOYEE e ON et.empid=e.id JOIN EV_TOPICS t ON et.topicid=t.topicid JOIN EV_STATUS s ON et.statusid=s.id";

		List<EmployeeTopics> employeeEvaluationList = jdbcTemplate
				.query(query,
						(resultSet, row) -> {

							EmployeeTopics employeeDetail = employeeTopicsIntailization(resultSet);
							return employeeDetail;

						});
		return employeeEvaluationList;

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
		Topics topic = new Topics();
		Status status = new Status();

		topic.setName(resultSet.getString("t.name"));
		status.setName(resultSet.getString("s.name"));
		employeeDetails.setId(resultSet.getInt("id"));
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

}
