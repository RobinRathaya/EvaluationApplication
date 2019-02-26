package com.chainsys.evaluationapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.evaluationapp.model.Status;

@Repository
public class StatusDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * @param statusId
	 * @return {@link Status}
	 */

	public Status searchStatusName(int statusId) {
		//TODO Search status name by id
		
		String query = "SELECT s.name FROM EV_STATUS s WHERE s.id=?";
		Object[] parameters = new Object[] { statusId };
		Status statusName = jdbcTemplate.queryForObject(query, parameters, (
				resultSet, row) -> {

			Status name = statusNameInitialization(resultSet);
			return name;
		});

		return statusName;
	}
	
	/**
	 * 
	 * @param statusName
	 * @return {@link Status}
	 */

	public Status searchStatusId(String statusName) {
		//TODO Search status ID by name
		
		String query = "SELECT s.id FROM EV_STATUS s WHERE s.name=?";
		Object[] parameters = new Object[] { statusName };
		Status statusDeatils = jdbcTemplate.queryForObject(query, parameters, (
				resultSet, row) -> {

			Status statusDetail = statusIdInitialization(resultSet);
			return statusDetail;
		});

		return statusDeatils;
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return Status
	 * @throws SQLException
	 */

	private Status statusNameInitialization(ResultSet resultSet)
			throws SQLException {
		// TODO Status Name Initialization
		Status name = new Status();
		name.setName(resultSet.getString("s.name"));

		return name;
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return Status
	 * @throws SQLException
	 */
	
	private Status statusIdInitialization(ResultSet resultSet)
			throws SQLException {
		// TODO Auto-generated method stub
		Status status = new Status();
		status.setId(resultSet.getInt("s.id"));

		return status;
	}

}
