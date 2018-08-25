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

	//��������
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
			Socket s = ss.accept();//�ȴ��ͻ�������
			System.out.println("�ͻ�����������.......");
			// ����һ���̶߳�����������ͻ�����������
			new ClientThread(s).start();
		}
	}
	
	//ֹͣ����
	public void stopService() throws IOException{
		ss.close();
	}
}
