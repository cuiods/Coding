package edu.nju.exam.entity;

import javax.persistence.*;

/**
 * Created by cuihao on 2017/2/21.
 */
@Entity
@Table(name = "score", schema = "examweb", catalog = "")
@IdClass(ScoreEntityPK.class)
public class ScoreEntity {
    private int sid;
    private int cid;
    private Integer score;
    private String type;

    @Id
    @Column(name = "sid")
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Id
    @Column(name = "cid")
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Id
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreEntity that = (ScoreEntity) o;

        if (sid != that.sid) return false;
        if (cid != that.cid) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sid;
        result = 31 * result + cid;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
