package com.example.projectxue;

import java.util.HashMap;

public class BasicUserProfile {

    public HashMap H;

    public BasicUserProfile() {
        H=new HashMap();
    }

    public BasicUserProfile(HashMap h) {
        H = h;
    }

    public  void setBasicProp(){

        this.H.put("Email"," Not set( tap edit below )");
        this.H.put("Username","Not set( tap edit below )");
        this.H.put("Phone","Not set( tap edit below )");
    }
    public HashMap getH() {
        return H;
    }

    public void setH(HashMap h) {
        H = h;
    }
    public  void setEmail(String s){

          this.H.put("Email",s);
    }
    public String  getEmail()
    { return this.H.get("Email").toString();}

    public String  getUsername()
    { return this.H.get("Username").toString();}

    public  void setPhone(String s){

          this.H.put("Phone",s);
    }
    public String getPhone()
    { return  this.H.get("Phone").toString();}

    public void   setUser()
    {
     this.H.put("USER","1");
    }
 public String  getUser()
    {  return  this.H.get("USER").toString();
    }

    public  void setUsername(String s){
         this.H.put("Username",s);
    }
    public void setUID(String s) {
        this.H.put("UserUID",s);
    }
}
