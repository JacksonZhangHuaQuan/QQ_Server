package com.lanqiao.qq.dao.dao;

import java.util.List;

import com.lanqiao.qq.entity.User;

public interface UserDAO {

	//��¼
	public User isLogin(String account, String password);
	
	//�һ�����
	public User findPass(String account, String email);
	
	//ע��
	public boolean addUser(User user); 
	
	//��ȡ��һ���˺�
	public int getNextAccount();
	
	//��ȡ���к�����Ϣ
	public List<User> queryFriends(String account);
	
	//���������û�
	public List<User> queryAll(String uaccount);
	
	//�����˺Ų����û�
	public User queryByAccount(String account);
	
	//ģ����ѯ
	public List<User> queryByLikeAccount(String account, String uaccount);
	
	//��Ӻ���
	public void addFriends(User u, User f);
}
