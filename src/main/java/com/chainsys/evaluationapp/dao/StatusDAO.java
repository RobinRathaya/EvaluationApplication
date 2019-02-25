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

	public Status searchStatusName(int statusId) {
		String query = "SELECT s.name FROM EV_STATUS s WHERE s.id=?";
		Object[] parameters = new Object[] { statusId };
		Status statusName = jdbcTemplate.queryForObject(query, parameters, (
				resultSet, row) -> {

			Status name = statusNameIntailization(resultSet);
			return name;
		});

		return statusName;
	}

	public Status searchStatusId(String statusName) {
		String query = "SELECT s.id FROM EV_STATUS s WHERE s.name=?";
		Object[] parameters = new Object[] { statusName };
		Status statusDeatils = jdbcTemplate.queryForObject(query, parameters, (
				resultSet, row) -> {

			Status statusDetail = statusIdIntailization(resultSet);
			return statusDetail;
		});

		return statusDeatils;
	}

	private Status statusNameIntailization(ResultSet resultSet)
			throws SQLException {
		// TODO Auto-generated method stub
		Status name = new Status();
		name.setName(resultSet.getString("s.name"));

		return name;
	}
	
	private Status statusIdIntailization(ResultSet resultSet)
			throws SQLException {
		// TODO Auto-generated method stub
		Status status = new Status();
		status.setId(resultSet.getInt("s.id"));

		return status;
	}

}
