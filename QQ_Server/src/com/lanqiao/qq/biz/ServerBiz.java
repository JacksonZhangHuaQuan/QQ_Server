package com.lanqiao.qq.biz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.thread.ClientThread;
import com.lanqiao.qq.util.ObjectUtil;
import com.lanqiao.qq.util.PropertiesUtil;

public class ServerBiz {
	
	ServerSocket ss;
	int port;
	private static Map<String, Socket> maps;
	
	public static Map<String, Socket> getMaps() {
		return maps;
	}

	//启动服务
	public void startService() throws IOException{
		maps = new HashMap<String, Socket>();
		String sport = PropertiesUtil.readPro("port");
		if(sport != null){
			port = Integer.parseInt(sport);
		}else{
			port = 6000;
		}
		ss = new ServerSocket(port);
		while(true){
			Socket s = ss.accept();//等待客户端链接
			System.out.println("客户端连接上了.......");
			// 启动一个线程独立处理这个客户的所有事情
			new ClientThread(s).start();
		}
	}
	
	//停止服务
	public void stopService() throws IOException{
		ss.close();
	}
}
