package com.tariqs.xerox;

public class LibraryData {
    private String id,numberof,dateadd;
    public LibraryData(String id,String numberof,String dateadd){
        this.id = id;
        this.numberof = numberof;
        this.dateadd=dateadd;


    }
    public String getId() {return this.id; }
    public void setId(String id) {this.id = id ; }
    public String getNumberof() {return this.numberof; }
    public void setNumberof(String numberof) {this.numberof = numberof ; }
    public String getDateadd() {return this.dateadd; }
    public void setDateadd(String dateadd) {this.dateadd = dateadd ; }
}
