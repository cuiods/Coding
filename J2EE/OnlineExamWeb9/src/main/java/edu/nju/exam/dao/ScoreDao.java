package edu.nju.exam.dao;



import edu.nju.exam.entity.VScoreEntity;

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
    List<VScoreEntity> findList(int studentId);
}
