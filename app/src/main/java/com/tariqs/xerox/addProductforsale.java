package com.tariqs.xerox;

public class addProductforsale {
    private String id, name, qty, APrice;
    private String isChecked;

    public addProductforsale(String id, String name, String qty, String APrice) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.APrice = APrice;
    }
    public String getId() {return this.id; }
    public void setId(String id) {this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {return this.qty;}
    public void setQty(String qty) {this.qty = qty; }



    public String getAPrice() {return this.APrice; }
    public void setAPrice(String APrice) {this.APrice = APrice; }

    public String getChecked() {
        return this.isChecked;
    }

    public void setChecked(String checked) {
        isChecked = checked;
    }
}