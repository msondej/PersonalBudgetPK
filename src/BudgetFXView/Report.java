package BudgetFXView;

import java.sql.Date;


public class Report {

    private String category;
    private Double amount;
    private Date date;

    public Report(String category, Double amount, Date date)
    {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }
    public Report()
    {
        this.category = "null";
        this.amount = 0.0;
        this.date = null;

    }


    public String getCategory() {
        return category;
    }


    public Double getAmount() {
        return amount;
    }


    public Date getDate() {
        return date;
    }


    public void setCategory(String category)
    {
        this.category = category;
    }


    public void setAmount(Double amount)
    {
        this.amount = amount;
    }


    public void setDate(Date date)
    {
        this.date = date;
    }
}
