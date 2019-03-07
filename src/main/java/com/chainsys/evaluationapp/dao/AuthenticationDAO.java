package com.chainsys.evaluationapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.evaluationapp.model.Employee;
import com.chainsys.evaluationapp.services.Services;

@Repository
public class AuthenticationDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Services services;

	/**
	 * initialize employee detail
	 * @param resultSet
	 * @return {@link Employee}
	 * @throws SQLException
	 */

	private Employee employeeIntialization(ResultSet resultSet)
			throws SQLException {
		Employee employee = new Employee();
		employee.setId(resultSet.getInt("id"));
		employee.setName(resultSet.getString("name"));

		return employee;
	}

	/**
	 *  perform login validation
	 * @param employee
	 * @return {@link Employee}
	 * @throws Exception
	 */

	public Employee loginValidation(Employee employee)
			throws Exception {
		String query = "SELECT id,name,email,password FROM EV_EMPLOYEE WHERE email= ? AND password=? ";
		Object[] parameters = new Object[] { employee.getEmail(),
				employee.getPassword() };
		Employee employeeDetail = null;
		try {
			employeeDetail = jdbcTemplate.queryForObject(query,
					parameters, (resultSet, row) -> {
						Employee userInfo = employeeIntialization(resultSet);
						return userInfo;
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return employeeDetail;

	}

	/**
	 *  perform reset password
	 * @param employee
	 * @return int
	 */

	public int resetPassword(Employee employee) {
		String query = "UPDATE EV_EMPLOYEE SET password=? WHERE id=?";
		Object[] parameters = new Object[] { employee.getPassword(),
				employee.getId() };
		int resetPasswordResult = jdbcTemplate.update(query, parameters);
		return resetPasswordResult;
	}

	/**
	 * conform the old password
	 * @param employee
	 * @return {@link Employee}
	 */

	public Employee searchPasswordExists(Employee employee) {
		String query = "SELECT id,name FROM EV_EMPLOYEE WHERE password = ? AND id = ?";
		Object[] parameters = new Object[] { employee.getPassword(),
				employee.getId() };
		Employee employeeDetails = jdbcTemplate
				.queryForObject(
						query,
						parameters,
						(resultSet, row) -> {
							if (!resultSet.equals(null)) {
								Employee employeeDetail = employeeIntialization(resultSet);
								return employeeDetail;
							} else {
								Employee employee1 = null;
								return employee1;
							}
						});
		return employeeDetails;
	}
	/**
	 * 
	 * @param String
	 * @return Employee
	 */

	public Employee searchEmailExists(String email) {
		String query = "SELECT id,name FROM EV_EMPLOYEE WHERE email = ?";
		Object[] parameters = new Object[] { email };
		Employee employeeDetails = jdbcTemplate.queryForObject(query,
				parameters, (resultSet, row) -> {
					Employee employeeDetail = null;
					if (!resultSet.equals(null)) {
						employeeDetail = employeeIntialization(resultSet);
					}
					return employeeDetail;
				});
		return employeeDetails;
	}
}
