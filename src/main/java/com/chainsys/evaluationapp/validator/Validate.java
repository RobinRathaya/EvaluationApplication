package com.chainsys.evaluationapp.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	

	public boolean topicInsertValidation(Topics topic) {
		
		boolean isExist = false;
		Topics topicPresent = topicsDAO.searchTopicId(topic.getName());
		
		if (topicPresent.getId() > 0) {
			isExist = true;
		} else {
			isExist = false;
		}
		return isExist;
	}
	
	public boolean statusInsertValidation(EmployeeTopics employeeTopic) throws Exception
	{
		
		boolean isExist = false;
		List<EmployeeTopics> employeeTopicsInDB = employeeTopicsDAO.searchEvaluationById(employeeTopic.getEmployee());
		
		for(EmployeeTopics employeeTopics:employeeTopicsInDB){
		if (employeeTopics.getTopic().getId()==employeeTopic.getTopic().getId()) {
			isExist = true;
			break;
		} else {
			isExist = false;
		}
		}
		return isExist;
		
	}
	public boolean passwordValidation(Employee employee)
	{
		boolean isExists=false;
		Employee employeeDetails=authenticationDAO.searchPasswordExists(employee);
		if(employeeDetails.getId()>0)
		{
			isExists=true;
		}
		else
		{
			isExists=false;
		}
		return isExists;
		
	}
	

}
