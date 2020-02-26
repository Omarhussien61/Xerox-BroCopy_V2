package com.tariqs.xerox;

public class SalesModel {
    String id, name, date, num, total, paid, remain, phone;

    public SalesModel(String bill_id, String bill_name, String bill_date, String num, String total
            , String paid, String remain, String phone) {
        this.id = bill_id;
        this.name = bill_name;
        this.date = bill_date;
        this.num = num;
        this.total = total;
        this.paid = paid;
        this.remain = remain;
        this.phone = phone;

    }

    public String getBill_id() {
        return this.id;
    }

    public void setBill_id(String id) {
        this.id = id;
    }

    public String getBill_name() {
        return this.name;
    }

    public void setBill_name(String bill_name) {
        this.name = bill_name;
    }

    public String getBill_date() {
        return this.date;
    }

    public void setBill_date(String bill_date) {
        this.date = bill_date;
    }


    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaid() {
        return this.paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getRemain() {
        return this.remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}