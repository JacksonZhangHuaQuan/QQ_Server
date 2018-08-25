package com.lanqiao.qq.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lanqiao.qq.biz.ServerBiz;
import com.lanqiao.qq.biz.UserBiz;
import com.lanqiao.qq.entity.AddFriendsMsg;
import com.lanqiao.qq.entity.AddRSMsg;
import com.lanqiao.qq.entity.Find;
import com.lanqiao.qq.entity.RegeditRS;
import com.lanqiao.qq.entity.SendFileMsg;
import com.lanqiao.qq.entity.SendFileRs;
import com.lanqiao.qq.entity.SendMsg;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.ObjectUtil;

public class ClientThread extends Thread{

	Socket s;
	private UserBiz uBiz;
	
	public ClientThread(Socket s) {
		this.s = s;
		uBiz = new UserBiz();
	}

	@Override
	public void run() {
		while(true){ //不停的监听
			try {
				Object o = ObjectUtil.readObject(s);
				if(o instanceof User){
					User u = (User)o;
					if(u.getAccount() != null){
						if(u.getPassword() != null){//登录
							u = uBiz.isLogin(u);
							if(u != null){//登录成功 account+Socket->Map
								ServerBiz.getMaps().put(u.getAccount(), s);
							}
							ObjectUtil.writeObject(s, u);//返回验证结果给客户端
						}else{
							System.out.println(u.getAccount() + "======= " + u.getEmail());
							u = uBiz.findPass(u);
							ObjectUtil.writeObject(s, u);
						}	
					}else{//注册
						RegeditRS rs = uBiz.regedit(u);
						ObjectUtil.writeObject(s, rs);//返回验证结果给客户端
					}
				}else if(o instanceof Find){
					{//查找
						Find find = (Find)o;
						List<User> ulist = new ArrayList<User>();
						switch(find.getType()){
						case Find.ONE:
							List<User> u = uBiz.querryByLikeAccount(find.getFaccount(), find.getUaccount());
							System.out.println(u.size());
							if(u != null){
								ulist.addAll(u);
							}
							break;
						case Find.All:
							ulist.addAll(uBiz.queryAll(find.getUaccount()));
							break;
						}
						ObjectUtil.writeObject(s, ulist);//返回验证结果给客户端
					}
				}else if(o instanceof AddFriendsMsg){
					AddFriendsMsg msg = (AddFriendsMsg)o;
					System.out.println(msg.getFrom().getNickname() + " want to add " + msg.getTo().getNickname());
					Socket fs = ServerBiz.getMaps().get(msg.getTo().getAccount());
					if(fs != null){//用户在线
						ObjectUtil.writeObject(fs, msg);
					}else{// 用户不在线 -> 保存消息在数据库中，离线消息
						
					}
				}else if(o instanceof AddRSMsg){
					AddRSMsg rs = (AddRSMsg)o;
					Socket fs = ServerBiz.getMaps().get(rs.getTo().getAccount());
					if(rs.isAgree()){
						uBiz.addFriends(rs);
					}
					if(fs != null){//用户在线
						ObjectUtil.writeObject(fs, rs);
					}else{// 用户不在线 -> 保存消息在数据库中，离线消息
						
					}
				}else if(o instanceof SendMsg){
					SendMsg msg = (SendMsg)o;
					Socket fs = ServerBiz.getMaps().get(msg.getTo().getAccount());
					if(fs != null){//用户在线
						ObjectUtil.writeObject(fs, msg);
					}else{// 用户不在线 -> 保存消息在数据库中，离线消息
						
					}
				} else if(o instanceof SendFileMsg){
					SendFileMsg msg = (SendFileMsg)o;
					Socket fs = ServerBiz.getMaps().get(msg.getTo().getAccount());
					if(fs != null){//用户在线
						ObjectUtil.writeObject(fs, msg);
					}else{// 用户不在线 -> 保存消息在数据库中，离线消息
						
					}
				} else if(o instanceof SendFileRs){
					SendFileRs rs = (SendFileRs)o;
					Socket fs = ServerBiz.getMaps().get(rs.getTo().getAccount());
					if(fs != null){//用户在线
						ObjectUtil.writeObject(fs, rs);
					}else{// 用户不在线 -> 保存消息在数据库中，离线消息
						
					}
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
