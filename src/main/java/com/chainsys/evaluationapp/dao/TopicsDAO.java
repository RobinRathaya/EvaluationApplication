package com.chainsys.evaluationapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.evaluationapp.model.Topics;

@Repository
public class TopicsDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * @param topic
	 * @throws Exception
	 * @return void
	 */
	public int addTopic(Topics topic) throws Exception {
		// TODO Auto-generated method stub

		String query = "INSERT INTO EV_TOPICS(name) VALUES(?)";
		Object[] parameters = new Object[] { topic.getName() };
		int addTopicResult = jdbcTemplate.update(query, parameters);
		return addTopicResult;
	}

	/**
	 * 
	 * @param topic
	 * @throws Exception
	 * @return void
	 */
	public int updateTopic(Topics topic) throws Exception {
		String query = "UPDATE EV_TOPICS SET name=? WHERE topicid=? ";
		Object[] parameters = new Object[] { topic.getName(), topic.getId() };
		int updateTopicResult = jdbcTemplate.update(query, parameters);
		return updateTopicResult;
	}

	public Topics searchTopicName(int topicId) {

		String query = "SELECT t.name FROM EV_TOPICS t WHERE t.topicid=?";
		Object[] parameters = new Object[] { topicId };

		Topics topic = jdbcTemplate.queryForObject(query, parameters, (
				resultSet, row) -> {

			Topics topicNames = topicNameIntialization(resultSet);

			return topicNames;
		});
		return topic;

	}

	private Topics topicNameIntialization(ResultSet resultSet)
			throws SQLException {

		Topics topic = new Topics();
		topic.setName(resultSet.getString("t.name"));

		return topic;
	}

	/**
	 * 
	 * @return {@link List}
	 * @throws Exception
	 */

	public List<Topics> displayTopics() throws Exception {

		String query = "SELECT topicid,name FROM EV_TOPICS";

		List<Topics> topicList = jdbcTemplate.query(query,
				(resultSet, row) -> {
					Topics topic = topicIntialization(resultSet);
					return topic;
				});
		return topicList;

	}

	/**
	 * 
	 * @param resultSet
	 * @return {@link Topics}
	 * @throws SQLException
	 */

	private Topics topicIntialization(ResultSet resultSet) throws SQLException {
		Topics topic = new Topics();
		topic.setId(resultSet.getInt("topicid"));
		topic.setName(resultSet.getString("name"));

		return topic;
	}

	/**
	 * 
	 * @param topic
	 * @return void
	 */

	public int deleteTopic(Topics topic) {

		String query = "DELETE FROM EV_TOPICS WHERE name=?";
		Object[] parameters = new Object[] { topic.getId() };
		int deleteTopicResult = jdbcTemplate.update(query, parameters);
		return deleteTopicResult;
	}

	public Topics searchTopicId(String topicName) {

		String query = "SELECT topicid,name FROM EV_TOPICS  WHERE name=?";
		Object[] parameters = new Object[] { topicName };

		Topics fetchedTopic = jdbcTemplate.queryForObject(query, parameters, (
				resultSet, row) -> {

			Topics topicIdFetch = topicIntialization(resultSet);

			return topicIdFetch;
		});

		return fetchedTopic;

	}

}
