package ee.esutoniagodesu.pojo.cf;


import com.jc.structure.pojo.IntIDStringTitle;

import java.io.Serializable;

public class LI_Classifier implements IntIDStringTitle, Serializable {

    private static final long serialVersionUID = -7770191084533654623L;

    private int id;
    private int classifierId;
    private String title;
    private String descr;
    private String comment;
    private transient int status;//ei lase gsonil serialiseerida
    private int orderInLine;
    private int parentId;
    private transient LI_Classifier parent;//ei lase gsonil serialiseerida
    private int countAllUsage;

    public LI_Classifier() {
    }

    public LI_Classifier(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(int classifierId) {
        this.classifierId = classifierId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderInLine() {
        return orderInLine;
    }

    public void setOrderInLine(int orderInLine) {
        this.orderInLine = orderInLine;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public LI_Classifier getParent() {
        return parent;
    }

    public void setParent(LI_Classifier parent) {
        this.parent = parent;
    }

    public int getCountAllUsage() {
        return countAllUsage;
    }

    public void setCountAllUsage(int countAllUsage) {
        this.countAllUsage = countAllUsage;
    }

    public String toString() {
        return "LI_Classifier{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", descr='" + descr + '\'' +
            ", comment='" + comment + '\'' +
            ", status=" + status +
            ", orderInLine=" + orderInLine +
            '}';
    }
}
