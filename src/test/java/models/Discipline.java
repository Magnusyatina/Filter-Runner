package models;

public class Discipline {

    private Mark mark;

    public Discipline() {
    }

    public Discipline(Mark mark) {
        this.mark = mark;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}