package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.DataHelper;
import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.model.ScoreEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * impl of scoreDao
 */
public class ScoreDaoImpl implements ScoreDao {

    private static volatile ScoreDaoImpl scoreDao;

    private DataHelper dataHelper;

    private ScoreDaoImpl() {
        dataHelper = DataHelperImpl.getInstance();
    }

    public static ScoreDaoImpl getInstance() {
        if (scoreDao==null) {
            synchronized (ScoreDaoImpl.class) {
                if (scoreDao==null) {
                    scoreDao = new ScoreDaoImpl();
                }
            }
        }
        return scoreDao;
    }

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
                scoreEntities.add(scoreEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet!=null) {
                dataHelper.closeResultSet(resultSet);
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
