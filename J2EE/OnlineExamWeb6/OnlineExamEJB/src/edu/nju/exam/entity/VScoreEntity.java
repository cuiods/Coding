package edu.nju.exam.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cuihao on 2017/1/24.
 */
@Entity
@Table(name = "v_score", schema = "examweb", catalog = "")
public class VScoreEntity implements Serializable{
    private String cname;
    private int sid;
    private Integer score;
    private int cid;
    private String type;

    @Basic
    @Column(name = "cname")
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Id
    @Column(name = "sid")
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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
    @Column(name = "cid")
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

        VScoreEntity that = (VScoreEntity) o;

        if (sid != that.sid) return false;
        if (cid != that.cid) return false;
        if (cname != null ? !cname.equals(that.cname) : that.cname != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cname != null ? cname.hashCode() : 0;
        result = 31 * result + sid;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + cid;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
