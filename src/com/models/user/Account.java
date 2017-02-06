package com.models.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Andrew Albert
 * @version 1.0
 * @since 12/9/2016
 */
@Entity
public class Account  {
	
    private String password;
    @Id @GeneratedValue
    private int User_Id;
    private String type;
   

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
