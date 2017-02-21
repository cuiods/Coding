package edu.nju.exam.service.impl;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.entity.VScoreEntity;
import edu.nju.exam.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * score service impl
 * @author cuihao
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreDao scoreDao;

    /**
     * get score list of a student
     *
     * @param studentId id of student
     * @return list of scores
     */
    @Override
    public List<VScoreEntity> findList(int studentId) {
        return scoreDao.findList(studentId);
    }
}
