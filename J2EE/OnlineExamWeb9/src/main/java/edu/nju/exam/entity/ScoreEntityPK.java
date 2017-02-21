package edu.nju.exam.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by cuihao on 2017/2/21.
 */
public class ScoreEntityPK implements Serializable {
    private int sid;
    private int cid;
    private String type;

    @Column(name = "sid")
    @Id
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Column(name = "cid")
    @Id
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Column(name = "type")
    @Id
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

        ScoreEntityPK that = (ScoreEntityPK) o;

        if (sid != that.sid) return false;
        if (cid != that.cid) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sid;
        result = 31 * result + cid;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
