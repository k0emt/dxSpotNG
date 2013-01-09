package com.dbbear.zenTools;

/* zenMsgBox this class implements a message box
 * zenMsgBox (string message);
 * zenMsgBox (string message, string title);
 * zenMsgBox (string message, string title, int type);
 * 
 * todo: build in multi-line handling via /n & JListbox
 *       implement specified buttons & listeners & return values
 */

import javax.swing.*;// need for our GUI
import java.awt.*;
import java.awt.event.*;

public class zenMsgBox {

	// these will probably have to be moved so that they can be used

	// zenMsgBox display/button types
	public static int zMB_NONE = 0;
	public static int zMB_OK_ONLY = 1;
	public static int zMB_OK_CANCEL= 2;
	public static int zMB_YES_NO = 3;
	public static int zMB_ACCEPT_DECLINE = 4;

	// zenMsgBox return values
	public static int zMB_OK = 1;
	public static int zMB_CANCEL = 0;
	public static int zMB_YES = 1;
	public static int zMB_NO = 0;
	public static int zMB_ACCEPT = 1;
	public static int zMB_DECLINE = 0;

	public zenMsgBox(String strText) {
	JFrame frmMsgBox = new JFrame("Notice");
	JLabel lblText = new JLabel(strText);
	JPanel pnlMain = new JPanel();

	pnlMain.add(lblText); 
	frmMsgBox.getContentPane().add(pnlMain,BorderLayout.CENTER);
	frmMsgBox.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	frmMsgBox.pack();
	frmMsgBox.setVisible(true);

	frmMsgBox.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		// System.exit(0);
	    }
	});
	}
	public zenMsgBox(String strText, String strTitle) {
	JFrame frmMsgBox = new JFrame(strTitle);
	JLabel lblText = new JLabel(strText);
	JPanel pnlMain = new JPanel();

	pnlMain.add(lblText); 
	frmMsgBox.getContentPane().add(pnlMain,BorderLayout.CENTER);
	frmMsgBox.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	frmMsgBox.pack();
	frmMsgBox.setVisible(true);

	frmMsgBox.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		// System.exit(0);
	    }
	});
	}
	public zenMsgBox(String strText, String strTitle, int iType) {
	JFrame frmMsgBox = new JFrame(strTitle);
	JLabel lblText = new JLabel(strText);
	JPanel pnlMain = new JPanel();

	pnlMain.add(lblText); 
	frmMsgBox.getContentPane().add(pnlMain,BorderLayout.CENTER);
	frmMsgBox.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	frmMsgBox.pack();
	frmMsgBox.setVisible(true);

	frmMsgBox.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		// System.exit(0);
	    }
	});
	}
}
