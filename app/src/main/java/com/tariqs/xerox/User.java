package com.tariqs.xerox;

public class User {
    private String error,Uid,phone,GroupId;
    public User(){
        /* this.error = error;
        this.Uid=Uid;
        this.phone=phone;
        this.GroupId=GroupId;
*/
    }
    public String getError() {
        return this.error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public String getUid() {return this.Uid;}
    public void setUid(String Uid) {this.Uid = Uid; }

    public String getPhone() {return this.phone; }
    public void setPhone(String phone) {this.phone = phone; }

    public String getGroupId() {return this.GroupId; }
    public void setGroupId(String GroupId) {this.GroupId = GroupId; }
}
