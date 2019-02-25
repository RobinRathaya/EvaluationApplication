package com.chainsys.evaluationapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chainsys.evaluationapp.dao.AuthenticationDAO;
import com.chainsys.evaluationapp.dao.EmployeeDAO;
import com.chainsys.evaluationapp.dao.TopicsDAO;
import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.model.EmployeeTopics;
import com.chainsys.evaluationapp.model.Status;
import com.chainsys.evaluationapp.model.Topics;
import com.chainsys.evaluationapp.validator.Validate;

@CrossOrigin
@RestController
@RequestMapping("/evaluation")
public class UserController {

	@Autowired
	public AuthenticationDAO authenticationDAO;

	@Autowired
	public EmployeeDAO employeeDAO;

	@Autowired
	public TopicsDAO topicsDAO;

	@Autowired
	Validate validator;

	@PostMapping("/login")
	public List<EmployeeTopics> login(@RequestParam("email") String email,
			@RequestParam("password") String password) throws Exception {

		Employee employee = new Employee();
		employee.setEmail(email);
		employee.setPassword(password);
		List<EmployeeTopics> userDetails = authenticationDAO
				.loginValidation(employee);
		return userDetails;
	}

	@PostMapping("/addstatus")
	public int addStatus(@RequestParam("empid") int empid,
			@RequestParam("topicname") String topicName,
			@RequestParam("statusid") int statusId) throws Exception {
		int noOfRows = 0;
		EmployeeTopics employeeTopics = new EmployeeTopics();
		Employee employee = new Employee();
		Status status = new Status();
		Topics topic = topicsDAO.searchTopicId(topicName);

		employee.setId(empid);
		status.setId(statusId);

		employeeTopics.setEmployee(employee);
		employeeTopics.setStatus(status);
		employeeTopics.setTopic(topic);

		employeeTopics.setCreatedOn(LocalDateTime.now());
		employeeTopics.setUpdatedOn(null);

		boolean isExist = validator.statusInsertValidation(employeeTopics);
		if (!isExist)
			noOfRows = employeeDAO.addEmployeeStatus(employeeTopics);
		else
			throw new Exception("Already entered status for this topic");
		return noOfRows;
	}

	@PostMapping("/updatestatus")
	public int updateStatus(@RequestParam("empid") int empid,
			@RequestParam("topicname") String topicName,
			@RequestParam("statusid") int statusId) {

		EmployeeTopics employeeTopics = new EmployeeTopics();
		Employee employee = new Employee();
		Topics topic = topicsDAO.searchTopicId(topicName);
		Status status = new Status();

		employee.setId(empid);
		status.setId(statusId);

		employeeTopics.setEmployee(employee);
		employeeTopics.setTopic(topic);
		employeeTopics.setStatus(status);
		employeeTopics.setUpdatedOn(LocalDateTime.now());

		int noOfRows = employeeDAO.updateEmployeeStatus(employeeTopics);

		return noOfRows;
	}

	@GetMapping("/displaytopics")
	public List<Topics> displayTopics() throws Exception {
		List<Topics> topicsList = topicsDAO.displayTopics();
		return topicsList;
	}

	@PostMapping("/resetpassword")
	public int resetPassword(@RequestParam("empid") int empid,			
			@RequestParam("newpassword") String newpassword,
			@RequestParam("oldpassword") String oldpassword) throws Exception {
		int resetStatus = 0;
		Employee employee = new Employee();
		employee.setId(empid);
		employee.setPassword(oldpassword);
		boolean isExists = validator.passwordValidation(employee);
		System.out.println(isExists);
		if (isExists) {
			employee.setPassword(newpassword);
			resetStatus = authenticationDAO.resetPassword(employee);
		}

		else {
			throw new Exception("Old password is wrong");
		}
		return resetStatus;
	}
}
