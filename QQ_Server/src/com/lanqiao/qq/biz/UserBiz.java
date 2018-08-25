package com.lanqiao.qq.biz;

import java.util.List;

import com.lanqiao.qq.dao.dao.UserDAO;
import com.lanqiao.qq.dao.impl.UserDAOImpl;
import com.lanqiao.qq.entity.AddRSMsg;
import com.lanqiao.qq.entity.RegeditRS;
import com.lanqiao.qq.entity.SendMsg;
import com.lanqiao.qq.entity.User;

public class UserBiz {

	private UserDAO udao;
	
	public UserBiz(){
		udao = new UserDAOImpl();
	}
	
	//��¼
	public User isLogin(User u){
		u = udao.isLogin(u.getAccount(), u.getPassword());
		
		if(u != null){
			//������Ϣ
			u.setFriends(udao.queryFriends(u.getAccount()));
		}
		return u;
	}
	
	//�һ�����
	public User findPass(User u){
		u = udao.findPass(u.getAccount(), u.getEmail());
		return u;
	}
	
	//ע��
	public RegeditRS regedit(User u){
		RegeditRS rs = new RegeditRS();
		int i = udao.getNextAccount();//��ȡ��һ���˺�
		String account = String.format("java_%05d%n", i).trim();
		u.setAccount(account);//�����û��˺ţ���
		boolean b = udao.addUser(u);
		if(b){
			rs.setMsg("��ĵ�¼�˺��ǣ�" + account);
			rs.setRs(true);
		}else{
			rs.setMsg("ע��ʧ��");
			rs.setRs(false);
		}
		return rs;
	}
	
	//��ȷ����
	public User queryByAccount(String account){
		return udao.queryByAccount(account);
	}
	
	//ģ����ѯ
	public List<User> querryByLikeAccount(String account, String uaccount){
		return udao.queryByLikeAccount(account, uaccount);
	}
	
	//��������
	public List<User> queryAll(String uaccount){
		return udao.queryAll(uaccount);
	}
	
	//��Ӻ���
	public void addFriends(AddRSMsg msg){
		udao.addFriends(msg.getForm(), msg.getTo());
	}
	

}
