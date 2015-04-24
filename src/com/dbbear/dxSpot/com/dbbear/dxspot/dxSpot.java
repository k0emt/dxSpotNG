package com.dbbear.dxspot;

/* author: k0emt@dbbear.com
 * purpose: provide a clean java interface to dxworld.com's DX Spot & QSO data
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.dbbear.dxspot.CommandProcessor;
import com.dbbear.zenTools.zenMsgBox;

//main class - init & main menu
class dxSpot {

	final static String dxAppTitle = "k0emt's dxSpot - data via dxworld.com";
	final static String dxVersion = "Release: January 2013";
	final static String dxCopyright = "Copyright 2000,2006,2013 Bryan Nehl - k0emt, dbBear.com";
	final static int iUpdateInterval = 300; // update every 5 minutes
	final static String[] info = {
			"dxSpot " + dxVersion + " by k0emt@dbbear.com",
			dxCopyright,
			" ",
			"data via Bob\'s dxworld.com",
			"This program is designed as an alternate interface to dxworld.com\'s html interface.",
			"You should thoroughly familiarize yourself with dxworld.com BEFORE using dxSpot.",
			"If you don\'t visit dxworld.com you will miss out on a lot of resources.",
			" ",
			"The DX window is for DX SPOTS ONLY.",
			"DO post the other ARS callsign, grid & frequency (QRG).",
			" ",
			"The QSO windows ARE for ragchewing, video spots, etc.",
			" ",
			"Design Considerations:",
			"Minimize the amount of bandwidth used when updating the data.",
			"(It only reads data up to the point of where it last read.)",
			"Provide as much data as possible (utilizes the look back log).",
			"Each frame is set to refresh 5 minutes from the last completed refresh.",
			"The user may also manually force an update.",
			" ",
			"By changing the interface, the user can easily have DX Spot and QSO",
			"information open for 6M, 2M and HF.",
			" ",
			"The users callsign and grid are automatically appended to every submission.",
			"IF they are set other than MyCall and MyGrid. ", " ", "HINT:",
			"Make sure to use the <ENTER> key when changing Callsign, Grid or",
			"submitting a DX Spot or comment to a QSO.", " ",
			"Please send your comments and suggestions to k0emt@dbbear.com" };

	private CommandProcessor commandProcessor;

	// our main function
	public static void main(String[] args) {
		new dxSpot(args);
	}

	private dxSpot(String[] args) {
		setLookAndFeel();

		commandProcessor = new CommandProcessor(args);
		// create the frame
		final JFrame frmMain = new JFrame("k0emt's dxSpot via dxWorld.com");

		// menubar
		JMenuBar mnuBar = new JMenuBar();
		frmMain.setJMenuBar(mnuBar);

		// Build the first menu.
		JMenu menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menu.getAccessibleContext().setAccessibleDescription(
				"Help & Info about k0emt's dxSpot interface to dxWorld.com");
		mnuBar.add(menu);

		// a group of JMenuItems
		JMenuItem menuItem = new JMenuItem("About k0emt's dxSpot Interface",
				KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"About k0emt's dxSpot Interface");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// the displayed dialog box
				final JDialog frmAbout = new JDialog(frmMain,
						"About k0emt's dxSpot Interface");
				frmAbout.getContentPane().setLayout(new BorderLayout(5, 5));

				// info to display in the center
				JList<String> jlInfo = new JList<String>(dxSpot.info);

				// create the info panel, add the text area
				JScrollPane pnlInfo = new JScrollPane(jlInfo);

				// buttons to go to k0emt@dbbear.com, dxworld.com & close
				JButton cmdk0emt = new JButton("k0emt www");
				JButton cmddxworld = new JButton("www.dxworld.com");
				JButton cmdClose = new JButton("Close");

				cmdk0emt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						commandProcessor.execute("web k0emt");
					}
				});

				cmddxworld.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						commandProcessor.execute("web dxworld");
					}
				});

				cmdClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frmAbout.setVisible(false);
						frmAbout.dispose();
					}
				});

				// create the button panel, add the buttons
				JPanel pnlButtons = new JPanel(new FlowLayout(
						FlowLayout.CENTER, 5, 5));
				pnlButtons.add(cmdk0emt);
				pnlButtons.add(cmddxworld);
				pnlButtons.add(cmdClose);

				// add panels to dialog
				frmAbout.getContentPane().add("Center", pnlInfo);
				frmAbout.getContentPane().add("South", pnlButtons);

				// display the form
				frmAbout.pack();
				frmAbout.setVisible(true);
			}
		});
		menu.add(menuItem);

		// create the controls
		// text boxes & labels for operators call sign and grid
		// buttons for 6M, 2M & HF
		final JTextField txtOperator = new JTextField(
				CommandProcessor.opHam.getCallSign(), 10); // for ARS Op's call
															// sign
		final JTextField txtGrid = new JTextField(
				CommandProcessor.opHam.getGrid(), 6); // for Operator's grid
		JLabel lblOpGrid = new JLabel("My ARS/Grid:");
		JButton cmdSix = new JButton("6M DX");
		JButton cmdTwo = new JButton("2M DX");
		JButton cmdHF = new JButton("HF DX");
		JButton cmdSixQ = new JButton("VHF QSO");
		JButton cmdUhf = new JButton("UHF DX");
		JButton cmdHFQ = new JButton("HF QSO");

		// create the panels
		JPanel pnlText = new JPanel();
		pnlText.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		pnlText.setLayout(new GridLayout(0, 3));
		pnlText.add(lblOpGrid);
		pnlText.add(txtOperator);
		pnlText.add(txtGrid);
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlButtons.setLayout(new GridLayout(0, 3));

		// DX buttons
		pnlButtons.add(cmdSix);
		pnlButtons.add(cmdTwo);
		pnlButtons.add(cmdHF);

		// QSO buttons
		pnlButtons.add(cmdSixQ);
		pnlButtons.add(cmdUhf);
		pnlButtons.add(cmdHFQ);

		// put panels into frame
		frmMain.getContentPane().add(pnlText, BorderLayout.NORTH);
		frmMain.getContentPane().add(pnlButtons, BorderLayout.SOUTH);

		// action Handlers
		txtOperator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommandProcessor.opHam.setCallSign(txtOperator.getText());
				zmb("Call Sign Changed: " + txtOperator.getText(), dxAppTitle);
			}
		});
		txtGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommandProcessor.opHam.setGrid(txtGrid.getText());
				zmb("Grid Changed: " + txtGrid.getText(), dxAppTitle);
			}
		});
		cmdSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Launch a 6M DX frame, init & display
				commandProcessor.execute("6d");
			}
		});
		cmdSixQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Launch a VHF QSO frame, init, set update interval & make
				// visible
				commandProcessor.execute("vq");
			}
		});
		cmdTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Launch a 2M DX frame, init & display
				commandProcessor.execute("2d");
			}
		});
		cmdUhf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commandProcessor.execute("ud");
			}
		});
		cmdHF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Launch an HF DX frame, init & display
				commandProcessor.execute("hd");
			}
		});
		cmdHFQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Launch an HF/Generic QSO frame
				commandProcessor.execute("hq");
			}
		});
		frmMain.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0); // when this window is shut, the program ends
			}
		});

		// bring it to life
		frmMain.pack();
		frmMain.setVisible(true);
	}

	private void setLookAndFeel() {
		// set the Java look and feel
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
	}

	public static void zmb(String strMsg, String strTitle) {
		new zenMsgBox(strMsg, strTitle);
	}
}
