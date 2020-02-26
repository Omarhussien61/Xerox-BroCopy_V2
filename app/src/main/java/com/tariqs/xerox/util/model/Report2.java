package com.tariqs.xerox.util.model;

public class Report2 {
    public String cusname,name;
    public String qty;
    public String total;

    public String date;

    public Report2(String cusname,String name, String qty, String total, String date)
    {
        this.cusname = cusname;
        this.name = name;
        this.qty = qty;
        this.total = total;
        this.date = date;

    }

    public String getCusname() {
        return this.cusname;
    }
    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return this.qty;
    }
    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return this.total;
    }
    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }


}
