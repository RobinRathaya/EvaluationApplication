package com.chainsys.evaluationapp.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chainsys.evaluationapp.dao.EmployeeDAO;
import com.chainsys.evaluationapp.dao.EmployeeTopicsDAO;
import com.chainsys.evaluationapp.dao.StatusDAO;
import com.chainsys.evaluationapp.dao.TopicsDAO;
import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.model.EmployeeTopics;

@Service
public class Services {
	@Autowired
	public EmployeeTopicsDAO employeeTopicsDAO;
	@Autowired
	public TopicsDAO topicsDAO;
	@Autowired
	public StatusDAO statusDAO;
	@Autowired
	public EmployeeDAO employeeDAO;

	/**
	 * 
	 * @param employee
	 * @return {@link List}
	 * @throws Exception
	 * 
	 */

	public List<EmployeeTopics> fetchUserDetails(Employee employee)
			throws Exception {

		// To Fetch and initialize topic and status name

		List<EmployeeTopics> employeeEvaluationDetails = employeeTopicsDAO
				.searchEvaluationById(employee);
		
		employeeEvaluationDetails.forEach(employeeDetail -> {
			
			employeeDetail.setTopic(topicsDAO.searchTopicName(employeeDetail
					.getTopic().getId()));
			employeeDetail.setStatus(statusDAO.searchStatusName(employeeDetail
					.getStatus().getId()));
			
			employeeDetail.setEmployee(employee);

		});
		
		return employeeEvaluationDetails;
	}

	/**
	 * 
	 * @param topic
	 * @return {@link List}
	 */

	public List<EmployeeTopics> fetchUserTopicsDetails(
			List<EmployeeTopics> employeeTopicsList) {

		// To initialize employee,topic and status name

		employeeTopicsList.forEach(employeeDetail -> {
			employeeDetail.setEmployee(employeeDAO
					.searchEmployeeName(employeeDetail.getEmployee().getId()));
			employeeDetail.setTopic(topicsDAO.searchTopicName(employeeDetail
					.getTopic().getId()));
			employeeDetail.setStatus(statusDAO.searchStatusName(employeeDetail
					.getStatus().getId()));
		});

		return employeeTopicsList;
	}

	/**
	 * 
	 * @throws IOException
	 * @return {@link Void}
	 */

	public void employeeDetailsExcel() throws IOException {

		// To export user details to excel

		final String file = "E:\\INTERNSHIP\\JAVA\\WORKSPACE\\evaluation-app\\Records\\EmployeeList.xlsx";
		Workbook employeeWorkbook = new XSSFWorkbook();
		Sheet employeeSheet = employeeWorkbook.createSheet();
		List<Employee> employeeList = employeeDAO.fetchUsers();
		int rowIndex = 0;
		for (Employee employee : employeeList) {
			int cellIndex = 0;
			Row row = employeeSheet.createRow(rowIndex++);
			row.createCell(cellIndex++).setCellValue(employee.getId());
			row.createCell(cellIndex++).setCellValue(employee.getName());
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			employeeWorkbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param evaluationList
	 * @throws IOException
	 * @return {@link Void}
	 */

	public void employeeEvaluationExcel(List<EmployeeTopics> evaluationList)
			throws IOException {
		
		//To export evaluation details of all users to excel
		
		final String path = "E:\\INTERNSHIP\\JAVA\\WORKSPACE\\evaluation-app\\Records\\EmployeeEvaluation.xlsx";
		Workbook evaluationWorkBook = new XSSFWorkbook();
		Sheet sheetName = null;
		Row row = null;
		int rowIndex = 0;
		int cellIndex = 0;
		for (EmployeeTopics evaluationInfo : evaluationList) {

			if (evaluationWorkBook.getSheet(evaluationInfo.getEmployee()
					.getName()) == null) {
				cellIndex = 0;
				sheetName = evaluationWorkBook.createSheet(evaluationInfo
						.getEmployee().getName());
				row = sheetName.createRow(rowIndex++);
				row.createCell(cellIndex++).setCellValue(
						evaluationInfo.getTopic().getName());
				row.createCell(cellIndex++).setCellValue(
						evaluationInfo.getStatus().getName());
			} else {
				sheetName = evaluationWorkBook.getSheet(evaluationInfo
						.getEmployee().getName());
				rowIndex = sheetName.getLastRowNum() + 1;
				cellIndex = 0;
				row = sheetName.createRow(rowIndex++);
				sheetName.autoSizeColumn(3);
				row.createCell(cellIndex++).setCellValue(
						evaluationInfo.getTopic().getName());
				row.createCell(cellIndex++).setCellValue(
						evaluationInfo.getStatus().getName());
			}
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			evaluationWorkBook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
