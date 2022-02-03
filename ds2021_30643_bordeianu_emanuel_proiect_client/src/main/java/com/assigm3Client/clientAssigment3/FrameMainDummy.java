package com.assigm3Client.clientAssigment3;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class FrameMainDummy {

	private JFrame frame;
	private Integer ziuaD;
	private Long idClient;
	private MeasureValue measureValue;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//new FrameMainDummy(idClient);
					//FrameMainDummy window = new FrameMainDummy();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public FrameMainDummy(Long idClient) {
		initialize(idClient);
		frame.setVisible(true);
	}

	
	private void initialize(Long idClient) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ClientAssigment3Application.class);
		measureValue = ctx.getBean(MeasureValue.class);
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 415);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btn_spre_subpunct1 = new JButton("Hourly_historical_energy_cons_over_D_days");
		btn_spre_subpunct1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Subpunct1(idClient);
			}
		});
		btn_spre_subpunct1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_spre_subpunct1.setBounds(70, 34, 337, 77);
		frame.getContentPane().add(btn_spre_subpunct1);
		
		JButton btn_spre_subpunct2 = new JButton("Averaged_energy_cons_over_past_week");
		btn_spre_subpunct2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Subpunct2(idClient);
				
			}
		});
		btn_spre_subpunct2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_spre_subpunct2.setBounds(70, 147, 337, 77);
		frame.getContentPane().add(btn_spre_subpunct2);
		
		JButton btn_spre_subpunct4 = new JButton("Best_time_to_start");
		btn_spre_subpunct4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new DevicesTable(idClient);
				
			}
		});
		btn_spre_subpunct4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_spre_subpunct4.setBounds(70, 258, 337, 77);
		frame.getContentPane().add(btn_spre_subpunct4);
		
		
		ctx.close();
	}
	
}

