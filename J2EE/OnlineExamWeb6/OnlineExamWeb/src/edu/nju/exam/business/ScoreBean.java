package edu.nju.exam.business;


import java.io.Serializable;
import java.util.List;

import edu.nju.exam.entity.ScoreEntity;
import edu.nju.exam.entity.VScoreEntity;

/**
 * business action<br/>
 * score list
 */
public class ScoreBean implements Serializable{

	private static final long serialVersionUID = 4827158428004584380L;
	/**
     * score list
     */
    private List<VScoreEntity> scoreEntities;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<VScoreEntity> getScoreEntities() {
        return scoreEntities;
    }

    public void setScoreEntities(List<VScoreEntity> scoreEntities) {
        this.scoreEntities = scoreEntities;
    }
}
