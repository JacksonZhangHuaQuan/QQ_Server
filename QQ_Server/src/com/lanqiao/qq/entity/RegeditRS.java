package com.lanqiao.qq.entity;

import java.io.Serializable;

public class RegeditRS implements Serializable{

	private boolean rs;//ע��ɹ�����ʧ��
	private String msg;//ע��������Ϣ
	
	public boolean isRs() {
		return rs;
	}
	public void setRs(boolean rs) {
		this.rs = rs;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
