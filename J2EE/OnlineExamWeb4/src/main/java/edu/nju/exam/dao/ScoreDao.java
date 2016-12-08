package edu.nju.exam.dao;

import edu.nju.exam.model.ScoreEntity;

import java.util.List;

/**
 * score dao interface
 */
public interface ScoreDao {

    /**
     * get score list of a student
     * @param studentId id of student
     * @return list of scores
     */
    List<ScoreEntity> findList(int studentId);
}
