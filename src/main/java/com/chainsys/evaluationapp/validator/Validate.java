package com.chainsys.evaluationapp.validator;

import java.util.List;

import javax.naming.InvalidNameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chainsys.evaluationapp.ExceptionHandler.InvalidEmailException;
import com.chainsys.evaluationapp.ExceptionHandler.InvalidIdException;
import com.chainsys.evaluationapp.ExceptionHandler.InvalidPasswordException;
import com.chainsys.evaluationapp.dao.AuthenticationDAO;
import com.chainsys.evaluationapp.dao.EmployeeTopicsDAO;
import com.chainsys.evaluationapp.dao.TopicsDAO;
import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.model.EmployeeTopics;
import com.chainsys.evaluationapp.model.Topics;

@Repository
public class Validate {

	@Autowired
	TopicsDAO topicsDAO;

	@Autowired
	EmployeeTopicsDAO employeeTopicsDAO;

	@Autowired
	AuthenticationDAO authenticationDAO;

	public void statusInsertValidation(EmployeeTopics employeeTopic)
			throws Exception {
		// TODO status validation

		List<EmployeeTopics> employeeTopicsInDB = employeeTopicsDAO
				.searchEvaluationById(employeeTopic.getEmployee());

		for (EmployeeTopics employeeTopics : employeeTopicsInDB) {
			if (employeeTopics.getTopic().getId() == employeeTopic.getTopic()
					.getId()) {
				throw new InvalidIdException("Status already created");
			}
		}

	}

	public void passwordValidation(Employee employee)
			throws InvalidPasswordException {
		// TODO password validation

		Employee employeeDetails = authenticationDAO
				.searchPasswordExists(employee);
		if (employeeDetails.getId() > 0) {
			throw new InvalidPasswordException("Invalid password");
		}

	}

	public void emailValidation(String email) throws InvalidEmailException {
		// TODO check email ID already exists

		Employee employeeDetails = authenticationDAO.searchEmailExists(email);
		if (employeeDetails.getEmail() != null) {
			throw new InvalidEmailException("Email already exists");
		}
	}

	public void searchTopic(String topicName) throws InvalidNameException {
		// TODO check topics exists
		Topics topic = topicsDAO.searchTopicId(topicName);
		if (topic.getId() > 0) {
			throw new InvalidNameException("Topic name already exists");
		}

	}

}
