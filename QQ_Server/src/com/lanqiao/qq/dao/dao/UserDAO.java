package com.lanqiao.qq.dao.dao;

import java.util.List;

import com.lanqiao.qq.entity.User;

public interface UserDAO {

	//登录
	public User isLogin(String account, String password);
	
	//找回密码
	public User findPass(String account, String email);
	
	//注册
	public boolean addUser(User user); 
	
	//获取下一个账号
	public int getNextAccount();
	
	//获取所有好友信息
	public List<User> queryFriends(String account);
	
	//查找所有用户
	public List<User> queryAll(String uaccount);
	
	//根据账号查找用户
	public User queryByAccount(String account);
	
	//模糊查询
	public List<User> queryByLikeAccount(String account, String uaccount);
	
	//添加好友
	public void addFriends(User u, User f);
}
