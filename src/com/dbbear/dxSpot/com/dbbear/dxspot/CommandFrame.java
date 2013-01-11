package com.dbbear.dxspot;


import java.lang.String;           // string handling
import java.util.Date;             // date routines
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

final public class CommandFrame extends JFrame {
	
	final static String APP_TITLE = "k0emt's DxCommand - data via dxworld.com";
	final static String APP_VERSION = "2013.01.10";
	final static String APP_COPYRIGHT = "Copyright 2000,2006,2013 Bryan Nehl - k0emt, dbBear.com";
	static final long serialVersionUID = 3;
	
	private int iEntry;
	private String sEntry;
	
	public CommandFrame(  ) {
		super("DxSpot Command " + APP_VERSION);
		setSize(400, 110); // size of the frame
		setLocation(100, 100); // location on the screen
		
		// command history text area
		// different font, identify it as history area, don't allow changes
		final JTextArea jtaCommandHistory = new JTextArea( );
		jtaCommandHistory.setFont(new Font("Serif", Font.PLAIN, 11));
		jtaCommandHistory.setText(
				APP_TITLE + ", " +	APP_VERSION + "\n" +
				APP_COPYRIGHT + "\n" +
				"? for help"
		);
		jtaCommandHistory.setEditable(false);
		jtaCommandHistory.setLineWrap(false);
		
		// put the history text area on a scroll pane
		Container content = getContentPane(  );
		
		content.add(new JScrollPane(jtaCommandHistory), BorderLayout.CENTER);
		
		// clear history and command line area go in shared JPanel
		// which should default to flow layout
		final JPanel jpEntry = new JPanel();
		jpEntry.setLayout(new BoxLayout(jpEntry, BoxLayout.X_AXIS));
		
		// command line text field
		// create this first so we can set focus here in the 
		// clear history button event
		final JTextField tfCmdLine = new JTextField(20);
		tfCmdLine.setFont(new Font("DialogInput",Font.PLAIN,12));
		
		// clear history button
		final JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Dialog",Font.BOLD,10));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jtaCommandHistory.setText("");
				tfCmdLine.requestFocus();
			}
		});
		
		// add in the order we want them
		jpEntry.add(tfCmdLine);
		jpEntry.add(btnClear);
		
		// add the entry panel to the contents
		content.add(jpEntry,BorderLayout.SOUTH);
		
		setVisible(true);
		jpEntry.setVisible(true);
		
		tfCmdLine.requestFocus(  );
		
		iEntry = 0; // DEV
		
		final CommandProcessor cp = new CommandProcessor();
		final Browser bc = new Browser();
		cp.setBrowser(bc);
		
		// add an action listener to our command line
		tfCmdLine.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				
				sEntry = tfCmdLine.getText();
				iEntry++;
				
				// append the entered text to the text area
				// Process the command and include the result
				
				jtaCommandHistory.append('\n' +
						String.valueOf(iEntry) + " | " + 
						String.valueOf(new Date()) + " | " +
						cp.execute(sEntry) + " | " + 
						sEntry );
				
				jtaCommandHistory.setCaretPosition(jtaCommandHistory.getDocument().getLength()); 
				
				// reset the command line to blank
				tfCmdLine.setText("");
			}
		});
	}

	public static void main(String[] args) {
		JFrame f = new CommandFrame();
		f.addWindowListener(new WindowAdapter(  ) {
			public void windowClosing(WindowEvent we) { System.exit(0); }
			public void windowClosed(WindowEvent we) { System.exit(0); }
		});
		f.setVisible(true);
	}
}

