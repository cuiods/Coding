package edu.nju.exam.business;

import edu.nju.exam.model.ScoreEntity;

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
    private List<ScoreEntity> scoreEntities;

    public List<ScoreEntity> getScoreEntities() {
        return scoreEntities;
    }

    public void setScoreEntities(List<ScoreEntity> scoreEntities) {
        this.scoreEntities = scoreEntities;
    }

    public ScoreEntity getScoreEntity(int index) {
        return scoreEntities.get(index);
    }

    public void setScoreEntity(int index, ScoreEntity scoreEntity) {
        scoreEntities.set(index, scoreEntity);
    }
}
