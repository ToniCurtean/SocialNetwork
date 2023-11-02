package com.example.socialnetworkchatfx.domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {

    private static final long serialVersionUID = 7331115341259248461L;
    private ID id;

    public void setId(ID id){
        this.id=id;
    }

    public ID getId(){
        return this.id;
    }

}
