package com.lanqiao.qq.entity;

import java.io.Serializable;

public class Find implements Serializable{

	private int type;// ��������
	private String faccount;//�����˺�
	private String uaccount;
	
	public static final int ONE = 1;
	public static final int All = 2;
	
	public String getUaccount() {
		return uaccount;
	}
	public void setUaccount(String uaccount) {
		this.uaccount = uaccount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFaccount() {
		return faccount;
	}
	public void setFaccount(String faccount) {
		this.faccount = faccount;
	}
	
	
}
