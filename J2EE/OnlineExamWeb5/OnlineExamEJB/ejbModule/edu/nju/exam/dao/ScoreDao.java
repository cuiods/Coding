package edu.nju.exam.dao;

import java.util.List;

import javax.ejb.Remote;

import edu.nju.exam.entity.ScoreEntity;

/**
 * score dao
 * @author cuihao
 *
 */
@Remote
public interface ScoreDao {
	
	/**
     * get score list of a student
     * @param studentId id of student
     * @return list of scores
     */
    List<ScoreEntity> findList(int studentId);

}
