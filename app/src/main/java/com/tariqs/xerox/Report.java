package com.tariqs.xerox;

public class Report {
    private String bill_id, bill_name, bill_date, total_b_desc,disc,total_a_desc,tax14,total_a_tax;

    public Report(String bill_id, String bill_name, String bill_date, String total_b_desc,String disc
    ,String total_a_desc,String tax14) {
        this.bill_id = bill_id;
        this.bill_name = bill_name;
        this.bill_date = bill_date;
        this.total_b_desc = total_b_desc;
        this.disc = disc;
        this.total_a_desc = total_a_desc;
        this.tax14 = tax14;

    }

    public String getBill_id() {
        return this.bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_name() {
        return this.bill_name;
    }

    public void setBill_name(String bill_name) {
        this.bill_name = bill_name;
    }

    public String getBill_date() {
        return this.bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }


    public String getTotal_b_desc() {
        return this.total_b_desc;
    }

    public void setTotal_b_desc(String total_b_desc) {
        this.total_b_desc = total_b_desc;
    }
    public String getDisc() {
        return this.disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }  public String getTotal_a_desc() {
        return this.total_a_desc;
    }

    public void setTotal_a_desc(String total_a_desc) {
        this.total_a_desc = total_a_desc;
    }  public String getTax14() {
        return this.tax14;
    }

   }
