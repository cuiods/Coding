package edu.nju.exam.taglib;

import edu.nju.exam.entity.VScoreEntity;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * show score tag description
 * @author cuihao
 */
public class ScoreInfoTag extends TagSupport{

    private List<VScoreEntity> scoreEntities;

    @Override
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            if (scoreEntities == null || scoreEntities.size() == 0) {
                writer.println("你还没有考试成绩。");
                return SKIP_BODY;
            }
            writer.println("<table border=\"1\">");
            writer.println("<tr>\n<th>课程</th>\n<th>分数</th>\n<th>类型</th>\n</tr>");
            ArrayList<String> noScoreCourses = new ArrayList<String>();
            scoreEntities.forEach(scoreEntity -> showSingleScore(scoreEntity,writer,noScoreCourses));
            writer.println("</table>");
            if (noScoreCourses.size()>0) {
                writer.println("<h2 style=\"color:yellow;\">警告信息:</h2>");
                noScoreCourses.forEach(s -> showWarning(s,writer));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        this.scoreEntities = null;
    }

    public void setScoreEntities(List<VScoreEntity> scoreEntities) {
        this.scoreEntities = scoreEntities;
    }

    public List<VScoreEntity> getScoreEntities() {
        return scoreEntities;
    }

    private void showWarning(String courseName, JspWriter writer) {
        try {
            writer.println("<p>"+courseName+" 没有参加考试! </p>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSingleScore (VScoreEntity scoreEntity, JspWriter writer, ArrayList<String> noScores) {
        String color = "black";
        int score = 0;
        if (scoreEntity.getScore()==null) {
            score = -1;
        } else {
            score = scoreEntity.getScore();
        }
        if (score<60) {
            color="red";
        }
        String scoreStr = "";
        if (score < 0) {
            scoreStr = "未参加考试";
            noScores.add(scoreEntity.getCname());
        } else {
            scoreStr += score;
        }
        try {
            writer.println("<tr>\n" +
                    "<td>"+scoreEntity.getCname()+"</td>\n" +
                    "<td style=\"color:"+color+";\">"+scoreStr+"</td>\n" +
                    "<td>"+scoreEntity.getType()+"</td>\n"+
                    "</tr>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
