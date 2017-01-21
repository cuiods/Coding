package edu.nju.exam.service;

import java.util.List;

import javax.ejb.Remote;

import edu.nju.exam.entity.ScoreEntity;

@Remote
public interface ScoreService {
	
	/**
     * get score list of a student
     * @param studentId id of student
     * @return list of scores
     */
    List<ScoreEntity> findList(int studentId);
}
