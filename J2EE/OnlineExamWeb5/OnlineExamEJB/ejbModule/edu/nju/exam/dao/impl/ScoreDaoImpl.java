package edu.nju.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import edu.nju.exam.dao.DataHelper;
import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.entity.ScoreEntity;

@Stateless
public class ScoreDaoImpl implements ScoreDao {
	
	private DataHelper dataHelper = DataHelperImpl.getBaseDaoInstance();

	@Override
	public List<ScoreEntity> findList(int studentId) {
		List<ScoreEntity> scoreEntities = new ArrayList<ScoreEntity>();
        Connection connection = dataHelper.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement =
                    connection.prepareStatement("SELECT * FROM v_score WHERE sid=? ");
            statement.setInt(1,studentId);
            resultSet = statement.executeQuery();
            while (resultSet!=null && resultSet.next()) {
                ScoreEntity scoreEntity = new ScoreEntity();
                scoreEntity.setStudentId(resultSet.getInt("sid"));
                scoreEntity.setCourseId(resultSet.getInt("cid"));
                scoreEntity.setCourseName(resultSet.getString("cname"));
                String scoreStr = resultSet.getString("score");
                int score = -1;
                if (scoreStr!=null && !scoreStr.equals("null")) {
                    score = Integer.parseInt(scoreStr);
                }
                scoreEntity.setScore(score);
                scoreEntity.setType(resultSet.getString("type"));
                scoreEntities.add(scoreEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet!=null) {
                dataHelper.closeResult(resultSet);
            }
            if (statement!=null) {
                dataHelper.closePreparedStatement(statement);
            }
            if (connection!=null) {
                dataHelper.closeConnection(connection);
            }
        }
        return scoreEntities;
	}

}
