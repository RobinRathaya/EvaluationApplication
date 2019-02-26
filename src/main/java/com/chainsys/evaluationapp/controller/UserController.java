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

import com.chainsys.evaluationapp.ExceptionHandler.InvalidPasswordException;
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
		
		//TODO validate Sign-in

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
		
		//TODO add new status for a topic
		
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

		validator.statusInsertValidation(employeeTopics);
		return noOfRows;
	}

	@PostMapping("/updatestatus")
	public int updateStatus(@RequestParam("empid") int empid,
			@RequestParam("topicname") String topicName,
			@RequestParam("statusid") int statusId) {
		
		//TODO update the status for topic

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
		
		// TODO display all the topics
		
		List<Topics> topicsList = topicsDAO.displayTopics();
		return topicsList;
	}

	@PostMapping("/resetpassword")
	public int resetPassword(@RequestParam("empid") int empid,			
			@RequestParam("newpassword") String newpassword,
			@RequestParam("oldpassword") String oldpassword) throws InvalidPasswordException {
		
		//TODO reset the password for profile
		
		int resetStatus = 0;
		Employee employee = new Employee();
		employee.setId(empid);
		employee.setPassword(oldpassword);
		validator.passwordValidation(employee);
		resetStatus = authenticationDAO.resetPassword(employee);
		return resetStatus;
	}
}
