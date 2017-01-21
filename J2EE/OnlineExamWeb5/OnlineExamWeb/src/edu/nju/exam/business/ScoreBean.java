package edu.nju.exam.business;


import java.io.Serializable;
import java.util.List;

import edu.nju.exam.entity.ScoreEntity;

/**
 * business action<br/>
 * score list
 */
public class ScoreBean implements Serializable{

	private static final long serialVersionUID = 4827158428004584380L;
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
