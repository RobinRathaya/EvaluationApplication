package com.chainsys.evaluationapp.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chainsys.evaluationapp.ExceptionHandler.InvalidEmailException;
import com.chainsys.evaluationapp.ExceptionHandler.InvalidIdException;
import com.chainsys.evaluationapp.ExceptionHandler.InvalidPasswordException;
import com.chainsys.evaluationapp.dao.AuthenticationDAO;
import com.chainsys.evaluationapp.dao.EmployeeTopicsDAO;
import com.chainsys.evaluationapp.dao.TopicsDAO;
import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.model.EmployeeTopics;

public class Validate {

	@Autowired
	TopicsDAO topicsDAO;

	@Autowired
	EmployeeTopicsDAO employeeTopicsDAO;

	@Autowired
	AuthenticationDAO authenticationDAO;

	public void loginValidation(Employee User) throws Exception {
		Employee userVerify = authenticationDAO.loginValidation(User);
		if (userVerify == null) {
			throw new InvalidEmailException("Email does not exists");
		} else if (userVerify.getName() == null) {
			throw new InvalidEmailException("Invalid email");
		}
	}

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
		if (employeeDetails.getId() < 0) {
			throw new InvalidPasswordException("Invalid password");
		}

	}

}
