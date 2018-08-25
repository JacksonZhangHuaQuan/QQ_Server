package com.lanqiao.qq.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lanqiao.qq.dao.dao.UserDAO;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.DBUtil;

public class UserDAOImpl implements UserDAO {

	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	List<User> ulist;
	
	private void closeAll(){
		try {
			if(rs != null){
				rs.close();
			}
			if(pst != null){
				pst.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//登录
	@Override
	public User isLogin(String account, String password) {
		sql = "select * from qquser where account = ? and password = ?";
		try {
			conn = DBUtil.getConn();
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()){
				User u = new User();
				u.setAccount(rs.getString("account"));
				u.setPassword(rs.getString("password"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setNickname(rs.getString("nickname"));
				u.setImg(rs.getString("img"));
				return u;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return null;
	}
	
	//找回密码
	@Override
	public User findPass(String account, String email) {
		sql = "select * from qquser where account = ? and email = ?";
		try {
			conn = DBUtil.getConn();
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, email);
			rs = pst.executeQuery();
			if(rs.next()){
				User u = new User();
				u.setAccount(rs.getString("account"));
				u.setPassword(rs.getString("password"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setNickname(rs.getString("nickname"));
				u.setImg(rs.getString("img"));
				return u;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return null;
	}

	//注册新户
	@Override
	public boolean addUser(User user) {
		sql = "insert into qquser values(?,?,?,?,?,?)";
		try {
			conn = DBUtil.getConn();
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getAccount());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getNickname());
			pst.setInt(4, user.getAge());
			pst.setString(5, user.getEmail());
			pst.setString(6, user.getImg());
			rs = pst.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return false;
	}

	//获取下一个账号数字
	@Override
	public int getNextAccount() {
		// 1.select + 1 -> 2.update 事务
		sql = "select * from account";
		int id = -1;
		try {
			conn = DBUtil.getConn();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if(rs.next()){
				id = rs.getInt("id") + 1;
				sql = "update account set id = ?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, id);
				pst.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
		return id;
	}

	//查找好友
	@Override
	public List<User> queryFriends(String account) {
		List<User> ulist = new ArrayList<User>();
		sql = "select * from qquser where account in "
				+ "(select friendid from friends where userid = ?)";
		try {
			conn = DBUtil.getConn();
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			rs = pst.executeQuery();
			while(rs.next()){
				User u = new User();
				u.setAccount(rs.getString("account"));
				u.setPassword(rs.getString("password"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setNickname(rs.getString("nickname"));
				u.setImg(rs.getString("img"));
				ulist.add(u);
			}
			return ulist;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
		return null;
	}

	//查找所有用户
	@Override
	public List<User> queryAll(String account) {
		List<User> ulist = new ArrayList<User>();
		sql = "select * from qquser where account not in "
				+ "(select friendid from friends where userid = ?) and account <> ?";
		try {
			conn = DBUtil.getConn();
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			pst.setString(2, account);
			rs = pst.executeQuery();
			while(rs.next()){
				User u = new User();
				u.setAccount(rs.getString("account"));
				u.setPassword(rs.getString("password"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setNickname(rs.getString("nickname"));
				u.setImg(rs.getString("img"));
				ulist.add(u);
			}
			return ulist;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
		return null;
	}

	//根据账号查找用户
	@Override
	public User queryByAccount(String account) {
		sql = "select * from qquser where account = ?";
		try {
			conn = DBUtil.getConn();
			pst = conn.prepareStatement(sql);
			pst.setString(1, account);
			rs = pst.executeQuery();
			if(rs.next()){
				User u = new User();
				u.setAccount(rs.getString("account"));
				u.setPassword(rs.getString("password"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setNickname(rs.getString("nickname"));
				u.setImg(rs.getString("img"));
				return u;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
		return null;
	}

	//添加好友
	@Override
	public void addFriends(User u, User f) {
		
		sql = "insert into friends values(?,?)";
		try {
			conn = DBUtil.getConn();
			conn.setAutoCommit(false);//设置不要主动提交
			pst = conn.prepareStatement(sql);
			pst.setString(1, u.getAccount());
			pst.setString(2, f.getAccount());
			pst.executeUpdate();
			pst.clearParameters();
			System.out.println("ok");
			pst.setString(1, f.getAccount());
			pst.setString(2, u.getAccount());
			pst.executeUpdate();
			conn.commit();//手动提交修改
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
	}


	@Override
	public List<User> queryByLikeAccount(String account,String uaccount) {
		List<User> ulist = new ArrayList<User>();
		sql = "select * from qquser where account not in "
				+ "(select friendid from friends where userid = ?) and account <> ? and account like ?";
		try {
			conn = DBUtil.getConn();
			pst = conn.prepareStatement(sql);
			pst.setString(1, uaccount);
			pst.setString(2, uaccount);
			pst.setString(3, "%" + account + "%");
			rs = pst.executeQuery();
			while(rs.next()){
				User u = new User();
				u.setAccount(rs.getString("account"));
				u.setPassword(rs.getString("password"));
				u.setAge(rs.getInt("age"));
				u.setEmail(rs.getString("email"));
				u.setNickname(rs.getString("nickname"));
				u.setImg(rs.getString("img"));
				ulist.add(u);
			}
			System.out.println("  =======================  " + ulist.size());
			return ulist;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
		return null;
	}


}
