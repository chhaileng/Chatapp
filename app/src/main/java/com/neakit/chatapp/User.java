package com.neakit.chatapp;

/**
 * Created by Chhaileng on 3/2/17.
 */

public class User {
    int _id;
    String _uid;
    String _name;
    String _email;
    String _password;

    public User(){

    }

    public User(int id, String uid, String name, String email, String password){
        this._id = id;
        this._uid = uid;
        this._name = name;
        this._email = email;
        this._password = password;
    }

    public User(String uid, String name, String email, String password){
        this._uid = uid;
        this._name = name;
        this._email = email;
        this._password = password;
    }

    public int getID(){
        return this._id;
    }
    public void setID(int id){
        this._id = id;
    }

    public String getUid(){
        return this._uid;
    }
    public void setUid(String uid){ this._uid = uid; }

    public String getName(){
        return this._name;
    }
    public void setName(String name){
        this._name = name;
    }

    public String getEmail(){
        return this._email;
    }
    public void setEmail(String email){
        this._email = email;
    }

    public String getPassword(){
        return this._password;
    }
    public void setPassword(String password){
        this._password = password;
    }
}