package com.tariqs.xerox;

public class Customers {
    private String id,name,phone,email,pass,note,gender;
    public Customers(String id,String name,String phone, String email, String pass,String note,String gender){
        this.id = id;
        this.name = name;
        this.phone=phone;
        this.email=email;
        this.pass = pass;
        this.note = note;
        this.gender = gender;

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

    public String getPhone() {return this.phone;}
    public void setPhone(String phone) {this.phone = phone; }



    public String getEmail() {return this.email; }
    public void setEmail(String email) {this.email = email; }

    public String getPass() {return this.pass;  }
    public void setPass(String pass) {this.pass = pass; }

    public String getNote() {return this.note; }
    public void setNote(String note) {this.note = note; }

    public String getGender() {return this.gender; }
    public void setGender(String gender) {this.gender = gender; }

}
