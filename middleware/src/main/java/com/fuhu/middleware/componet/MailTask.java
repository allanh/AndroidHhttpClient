/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fuhu.middleware.componet;

import java.io.Serializable;

/**
 *
 * @author HsiehChenWei
 */
public class MailTask implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String      strClassName;
    private String      address;
    private ICommand    mCommand;
    

    public void setClassName(final String strClassName){
        this.strClassName = strClassName;
    }

    public String getClassName(){
        return this.strClassName;
    }

    public void setCommand(final ICommand mCommand){
        this.mCommand = mCommand;
    }

    public ICommand getCommand(){
        return this.mCommand;
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof MailTask){
            MailTask mMailTask=(MailTask)obj;
            if(mMailTask.mCommand.equals(this.mCommand) && mMailTask.strClassName.equals(this.strClassName)){
                mMailTask.setAddress(this.getAddress());
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode(){
        return this.mCommand.hashCode()+this.strClassName.hashCode();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

