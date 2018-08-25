package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lanqiao.qq.biz.ServerBiz;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ServerJFrame extends JFrame {

	private JPanel contentPane;
	
	//================================
	private JButton jButton1;
	private JButton jButton2;
	private ServerBiz sbiz;
	
	//======================================

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerJFrame frame = new ServerJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//=======================================
		
		sbiz = new ServerBiz();
		
		//========================================
		
		jButton1 = new JButton("\u542F\u52A8\u670D\u52A1");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start(e);
			}
		});
		jButton1.setBounds(35, 109, 113, 27);
		contentPane.add(jButton1);
		
		jButton2 = new JButton("\u505C\u6B62\u670D\u52A1");
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop(e);
			}
		});
		jButton2.setEnabled(false);
		jButton2.setBounds(263, 109, 113, 27);
		contentPane.add(jButton2);
	}
	
	//======================================
	
	// 启动服务
	public void start(ActionEvent e){
		System.out.println("启动服务......" + e);
		new Thread(){
			public void run() {
				jButton1.setEnabled(false);
				jButton2.setEnabled(true);
				try {
					sbiz.startService();
				} catch (IOException e1) {
					System.out.println("服务端启动失败！！！");
					e1.printStackTrace();
				}
			};
		}.start();
	}
	
	// 关闭服务
	public void stop(ActionEvent e){
		System.out.println("关闭服务....." + e);
		new Thread(){
			public void run() {
				jButton1.setEnabled(true);
				jButton2.setEnabled(false);
				try {
					sbiz.stopService();
				} catch (IOException e1) {
					System.out.println("服务端关闭失败！！！");
					e1.printStackTrace();
				}
			};
		}.start();
	}
	
}
