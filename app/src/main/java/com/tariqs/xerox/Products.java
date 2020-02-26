package com.tariqs.xerox;

public class Products {
    private String id,name,skuX,actualpriceX,amountX,descrp;
    private boolean isSelected;

    public Products(String id,String name,String amountX, String actualpriceX,String skuX,String descrp){
        this.id = id;
        this.name = name;
        this.skuX=skuX;
        this.actualpriceX=actualpriceX;
        this.amountX = amountX;
        this.descrp = descrp;

    }
    public String getId() {return this.id; }
    public void setId(String id) {this.id = id ; }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getActualpriceX() {return this.actualpriceX;}
    public void setActualpriceX(String actualpriceX) {this.actualpriceX = actualpriceX; }



    public String getSkuX() {return this.skuX; }
    public void setSkuX(String skuX) {this.skuX = skuX; }

    public String getAmountX() {return this.amountX; }
    public void setAmountX(String amountX) {this.amountX = amountX; }


    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDescrp() {return this.descrp; }
    public void setDescrp(String descrp) {this.descrp = descrp; }
}
