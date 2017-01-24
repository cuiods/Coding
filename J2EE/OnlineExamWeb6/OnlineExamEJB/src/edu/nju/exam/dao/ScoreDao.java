package edu.nju.exam.dao;

import edu.nju.exam.entity.ScoreEntity;
import edu.nju.exam.entity.VScoreEntity;

import javax.ejb.Remote;
import java.util.List;

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
    List<VScoreEntity> findList(int studentId);

}
