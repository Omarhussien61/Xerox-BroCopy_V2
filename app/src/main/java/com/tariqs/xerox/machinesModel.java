package com.tariqs.xerox;

public class machinesModel
{
    private String id,cusname,macname,papers,adddate,mid,cid,pap,max,mo;

        public machinesModel(String id,String cusname,String macname, String papers
                ,String adddate,String mid,String cid,String pap,String max,String mo){
            this.id = id;
            this.cusname = cusname;
            this.macname=macname;
            this.papers=papers;
            this.adddate = adddate;
            this.mid = mid;
            this.cid = cid;
            this.pap = pap;
            this.max = max;
            this.mo = mo;

        }
        public String getId() {return this.id; }
        public void setId(String id) {this.id = id ; }

        public String getCusname() {
            return this.cusname;
        }
        public void setCusname(String cusname) {
            this.cusname = cusname;
        }

        public String getMacname() {return this.macname;}
        public void setMacname(String macname) {this.macname = macname; }



        public String getPapers() {return this.papers; }
        public void setPapers(String papers) {this.papers = papers; }

        public String getAdddate() {return this.adddate; }
        public void setAdddate(String adddate) {this.adddate = adddate; }



        public String getMid() {return this.mid; }
        public void setMid(String mid) {this.mid = mid; }

    public String getCid() {return this.cid; }
    public void setCid(String cid) {this.cid = cid; }

    public  String getPap() {return this.pap;  }
    public void setPap (String pap) {this.pap = pap ; }

    public String getMax() {return this.max; }
    public void setMax(String max) {this.max = max; }

    public String getMo() {return this.mo; }
    public void setMo(String mo) {this.mo = mo; }
}
