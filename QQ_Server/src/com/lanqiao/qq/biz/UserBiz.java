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
	
	//登录
	public User isLogin(User u){
		u = udao.isLogin(u.getAccount(), u.getPassword());
		
		if(u != null){
			//好友信息
			u.setFriends(udao.queryFriends(u.getAccount()));
		}
		return u;
	}
	
	//找回密码
	public User findPass(User u){
		u = udao.findPass(u.getAccount(), u.getEmail());
		return u;
	}
	
	//注册
	public RegeditRS regedit(User u){
		RegeditRS rs = new RegeditRS();
		int i = udao.getNextAccount();//获取下一个账号
		String account = String.format("java_%05d%n", i).trim();
		u.setAccount(account);//设置用户账号！！
		boolean b = udao.addUser(u);
		if(b){
			rs.setMsg("你的登录账号是：" + account);
			rs.setRs(true);
		}else{
			rs.setMsg("注册失败");
			rs.setRs(false);
		}
		return rs;
	}
	
	//精确查找
	public User queryByAccount(String account){
		return udao.queryByAccount(account);
	}
	
	//模糊查询
	public List<User> querryByLikeAccount(String account, String uaccount){
		return udao.queryByLikeAccount(account, uaccount);
	}
	
	//查找所有
	public List<User> queryAll(String uaccount){
		return udao.queryAll(uaccount);
	}
	
	//添加好友
	public void addFriends(AddRSMsg msg){
		udao.addFriends(msg.getForm(), msg.getTo());
	}
	

}
