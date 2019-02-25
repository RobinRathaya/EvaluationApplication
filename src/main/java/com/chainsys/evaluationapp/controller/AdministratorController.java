package com.chainsys.evaluationapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chainsys.evaluationapp.dao.EmployeeDAO;
import com.chainsys.evaluationapp.dao.EmployeeTopicsDAO;
import com.chainsys.evaluationapp.dao.TopicsDAO;
import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.model.EmployeeTopics;
import com.chainsys.evaluationapp.model.Topics;
import com.chainsys.evaluationapp.services.Services;
import com.chainsys.evaluationapp.validator.Validate;

@CrossOrigin
@RestController
@RequestMapping("/administrator")
public class AdministratorController {

	@Autowired
	EmployeeDAO employeeDAO;
	@Autowired
	TopicsDAO topicDAO;
	@Autowired
	EmployeeTopicsDAO employeeTopicsDAO;
	@Autowired
	Services services;
	@Autowired
	Validate validator;

	@PostMapping("/addUser")
	public int registeration(@RequestParam("empid") int empid,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password) throws Exception {
		Employee employee = new Employee();
		employee.setId(empid);
		employee.setName(name);
		employee.setEmail(email);
		employee.setPassword(password);
		int noOfRows = employeeDAO.addEmployee(employee);
		return noOfRows;

	}

	@PostMapping("/addTopic")
	public int insertTopic(@RequestParam("topicName") String topicName)
			throws Exception {
		int noOfRows = 0;
		Topics topic = new Topics();
		topic.setName(topicName);
		boolean isExists = validator.topicInsertValidation(topic);
		System.out.println(isExists);
		if (!isExists) {
			noOfRows = topicDAO.addTopic(topic);
		}
		else
			throw new Exception("Already exists in database");
		return noOfRows;
	}

	@GetMapping("/dispayUsers")
	public List<Employee> displayUsers() {

		return employeeDAO.fetchUsers();
	}

	@GetMapping("/displayEvaluation")
	public List<EmployeeTopics> displayEvaluation() {
		List<EmployeeTopics> employeeTopicsList = employeeTopicsDAO
				.searchEvaluation();

		return services.fetchUserTopicsDetails(employeeTopicsList);
	}
	
	@GetMapping("/displayInfo")
	public void display() throws IOException
	{
		List<EmployeeTopics> evalList=employeeTopicsDAO.searchEvaluation();
		services.fetchUserTopicsDetails(evalList);
		services.employeeEvaluationDetail(evalList);
	}

}
