package models;

public class Mark {

    private double mark;

    private String startDate;

    private String endDate;


    public Mark(double mark) {
        this.mark = mark;
    }

    public Mark(double mark, String startDate, String endDate) {
        this.mark = mark;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
