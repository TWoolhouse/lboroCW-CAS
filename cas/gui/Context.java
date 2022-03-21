package cas.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import cas.Shop;

public class Context {
	JFrame frame;

	public Context(Shop shop) {
		frame = new JFrame();// creating instance of JFrame
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton b = new JButton("click");// creating instance of JButton
		// b.setBounds(130, 100, 100, 40);// x axis, y axis, width, height

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("CLICK");
				// tf.setText("Welcome to Javatpoint.");
			}
		});

		frame.add(b);// adding button in JFrame

		frame.setMinimumSize(new Dimension(400, 400));
		frame.setSize(400, 500);// 400 width and 500 height
		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);// making the frame visible
	}
}
