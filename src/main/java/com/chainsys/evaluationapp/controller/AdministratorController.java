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
	@Autowired
	TopicsDAO topicsDAO;

	@PostMapping("/addUser")
	public int registration(@RequestParam("empid") int empid,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password) throws Exception {
		
		//TODO Registration
		
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
		
		//TODO add topic
	
		int noOfRows = 0;
		Topics topic = new Topics();
		topic.setName(topicName);
		topicsDAO.addTopic(topic);
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
		
		//TODO Display all the evaluation as table

		return services.fetchUserTopicsDetails(employeeTopicsList);
	}

	@GetMapping("/displayInfo")
	public void display() throws IOException {
		
		//TODO Display all the evaluation as excel
		
		List<EmployeeTopics> evalList = employeeTopicsDAO.searchEvaluation();
		services.fetchUserTopicsDetails(evalList);
		services.employeeEvaluationExcel(evalList);
	}

}
