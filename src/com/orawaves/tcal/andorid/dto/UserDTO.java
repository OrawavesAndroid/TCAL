/**
 * Class level document for generating getters and setters for User Login*/
package com.orawaves.tcal.andorid.dto;


@SuppressWarnings("serial")
public class UserDTO implements DTO
{
    private String userId;
    private String userName;
    private String password;
    private String activeStatus;
    private String mobileNumber;	

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
	 
	 

    public String getActiveStatus()
    {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus)
    {
        this.activeStatus = activeStatus;
    }
	 
}
