package com.lanqiao.qq.entity;

import java.io.Serializable;

public class AddFriendsMsg implements Serializable{
	
	private User from;//˭Ҫ����Ӻ���
	private User to;//Ҫ���˭Ϊ����
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	

}
