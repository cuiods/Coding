package edu.nju.exam.business;

import edu.nju.exam.entity.VScoreEntity;

import java.io.Serializable;
import java.util.List;

/**
 * business action<br/>
 * score list
 */
public class ScoreBean implements Serializable{

    /**
     * score list
     */
    private List<VScoreEntity> scoreEntities;

    public List<VScoreEntity> getScoreEntities() {
        return scoreEntities;
    }

    public void setScoreEntities(List<VScoreEntity> scoreEntities) {
        this.scoreEntities = scoreEntities;
    }

    public VScoreEntity getScoreEntity(int index) {
        return scoreEntities.get(index);
    }

    public void setScoreEntity(int index, VScoreEntity scoreEntity) {
        scoreEntities.set(index, scoreEntity);
    }
}
