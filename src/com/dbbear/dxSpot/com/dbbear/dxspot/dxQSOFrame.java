package com.dbbear.dxspot;

/* QSO Frame Class
 * display contents of QSO in List Box w/updates
 * submit QSO items via a URL Connection
 */

import javax.swing.*; // need for our GUI
import java.awt.*;
import java.awt.event.*;
import java.net.*; // URL & connection
import java.io.*; // system & file i/o
import java.util.Stack; // Stack implementation

class dxQSOFrame extends JFrame {
	static final long serialVersionUID = 1;

	String strQSOtitle = new String();
	String strQSOURL = new String();
	String strQSOSubmitURL = new String();
	String strLastRead = new String();
	int iUpdateInterval; // set this in seconds, we multiply by 1000 (ms)

	private JPanel _panelContent = null;
	private JPanel _panelInput = null;
	private JTextField _txtComment = null;
	private JTextArea _txtareaQSO = null;
	private JScrollPane _scrollpaneQSO = null;
	private JButton _btnUpdate = null;
	private Timer _timer; // used for auto-updating
	private IWebDataFormatter _wdf;

	_EventHandler eh = new _EventHandler();

	class _EventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == dxQSOFrame.this.getJB_update())
				updateActionEvents();
		};
	};

	public dxQSOFrame() {
		super();
		initialize();
	}

	public dxQSOFrame(String strTitle, String strURL, String strSubmitURL) {
		super();
		initialize();
		strQSOtitle = strTitle + " - " + dxSpot.dxAppTitle;
		strQSOURL = strURL;
		strQSOSubmitURL = strSubmitURL;
		strLastRead = "";
		setTitle(strQSOtitle);
	}

	public void set_WebDataFormatter(IWebDataFormatter _wdf) {
		this._wdf = _wdf;
	}

	private void updateActionEvents() {
		try {
			jB_update_ActionEvents();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	private javax.swing.JButton getJB_update() {
		if (_btnUpdate == null) {
			try {
				_btnUpdate = new javax.swing.JButton();
				_btnUpdate.setName("JB_update");
				_btnUpdate.setToolTipText("Query the server for any new info.");
				_btnUpdate.setText("update");
				_btnUpdate.setBounds(6, 310, 76, 20);
				_btnUpdate.setActionCommand("update");
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return _btnUpdate;
	}

	private javax.swing.JPanel getJFrameContentPane() {
		if (_panelContent == null) {
			try {
				_panelContent = new javax.swing.JPanel(new BorderLayout());
				_panelContent.setName("JFrameContentPane");

				getJFrameContentPane().add(getJSP_qso(), BorderLayout.CENTER);

				_panelInput = new javax.swing.JPanel(new BorderLayout());
				_panelInput.setName("JFrameInputPanel");
				_panelInput.add(getJTFcomment(), BorderLayout.CENTER);
				_panelInput.add(getJB_update(), BorderLayout.AFTER_LINE_ENDS);

				getJFrameContentPane().add(_panelInput, BorderLayout.SOUTH);

			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return _panelContent;
	}

	private javax.swing.JScrollPane getJSP_qso() {
		if (_scrollpaneQSO == null) {
			try {
				_scrollpaneQSO = new javax.swing.JScrollPane();
				_scrollpaneQSO.setName("JSP_qso");
				_scrollpaneQSO.setBounds(5, 5, 490, 301);
				getJSP_qso().setViewportView(getJTA_qso());
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return _scrollpaneQSO;
	}

	private javax.swing.JTextArea getJTA_qso() {
		if (_txtareaQSO == null) {
			try {
				_txtareaQSO = new javax.swing.JTextArea();
				_txtareaQSO.setName("JTA_qso");
				_txtareaQSO.setToolTipText("The running QSO");
				_txtareaQSO.setLineWrap(false);
				_txtareaQSO.setRows(12);
				_txtareaQSO.setBounds(10, 10, 490, 173);
				_txtareaQSO.setEditable(false);
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return _txtareaQSO;
	}

	private javax.swing.JTextField getJTFcomment() {
		if (_txtComment == null) {
			try {
				_txtComment = new javax.swing.JTextField();
				_txtComment.setName("JTFcomment");
				_txtComment
						.setToolTipText("Your comment (call sign & grid appended automagically)");
				_txtComment.setBounds(84, 310, 411, 21);

				_txtComment.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// TODO: this method has changed on some pages
						// need a new implementation that uses the
						// CommandProcessor and a new ISpotSubmitter class

						/****
						 * this is the html used to post a new comment <form
						 * method="POST"
						 * action="http://dxworld.com/cgi-bin/magicband.cgi">
						 * <input size=50 name="comment"><input type="submit"
						 * value="Submit/Refresh"></form>
						 */

						// open a URL connection to the cgi and initiate output
						try {
							URL urlSubmit = new URL(strQSOSubmitURL);
							URLConnection cnSubmit = urlSubmit.openConnection();
							DataOutputStream dsOut;

							// set properties
							cnSubmit.setDoOutput(true);
							cnSubmit.setUseCaches(false);
							cnSubmit.setAllowUserInteraction(false);
							cnSubmit.setRequestProperty("content-type",
									"application/x-www-form-urlencoded");
							dsOut = new DataOutputStream(cnSubmit
									.getOutputStream());

							// build the submission string and send it
							String strSubmit = new String("comment="
									+ URLEncoder.encode(_txtComment.getText(),
											"US-ASCII"));

							// if the callsign isn't set, or is set to empty
							// then do nothing
							// otherwise, append the callsign
							if ((CommandProcessor.opHam.getCallSign()
									.compareTo("MyCall") == 0)
									|| (CommandProcessor.opHam.getCallSign().compareTo(
											"") == 0)) {
								// do nothing, callsign isn't set, or it's set
								// to blank
							} else {
								strSubmit = strSubmit + " de "
										+ CommandProcessor.opHam.getCallSign();
							}

							// if the Grid isn't set, or is set to empty, then
							// do nothing
							// otherwise, auto-magically append the grid
							if ((CommandProcessor.opHam.getGrid().compareTo("MyGrid") == 0)
									|| (CommandProcessor.opHam.getGrid().compareTo("") == 0)) {
								// do nothing, grid isn't set, or it's set to
								// blank
							} else {
								strSubmit = strSubmit + " in "
										+ CommandProcessor.opHam.getGrid();
							}

							dsOut.writeBytes(strSubmit);
							dsOut.flush();
							dsOut.close();

							// although I don't do anything with this
							// the server doesn't accept the post if it can't
							// spit everything back
							BufferedReader in = new BufferedReader(
									new InputStreamReader(cnSubmit
											.getInputStream()));
							// String inputLine;

							// see if I can get away with taking just one line
							// inputLine =
							in.readLine();
							in.close();

							_txtComment.setText("");

							// also explicitly update the LogPane
							wakeUp();
						} catch (java.lang.Throwable ivjExc) {
							handleException(ivjExc);
						}
					}
				});

				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return _txtComment;
	}

	private void handleException(java.lang.Throwable exception) {

		/* Uncomment the following lines to print uncaught exceptions to stdout */
		// System.out.println("--------- UNHANDLED EXCEPTION ---------");
		// exception.printStackTrace(System.out);
	}

	/**
	 * This populates the qso pane when it's created wakeUp() is a variation on
	 * this could have modified wakeUp() to do this, but went with the
	 * additional code instead because wakeUp will be called repeatedly by the
	 * "alarm clock" so don't need the extra conditionals Creation date:
	 * (8/4/2000 16:28:34)
	 */
	void init_pane() {
		// open the QSO URL and update the list box
		try {
			URL dxURL = new URL(strQSOURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					dxURL.openStream()));

			String strLine;
			String strOut;
			boolean bSet;
			bSet = false;

			while ((strLine = in.readLine()) != null)
				try {
					strOut = _wdf.format(strLine);

					// in this case last read is actually the first item read
					if (bSet == false) {
						strLastRead = strOut;
						bSet = true;
					}
					if (strLine.length() > 0) {
						_txtareaQSO.insert(strOut, 0);
					}
				} catch (Exception e) {
					// do nothing, end of contents
				}
		} catch (Exception e) {
			System.out.println(strQSOURL);
			e.printStackTrace(); // something went wrong
		}

		// start a timer going that will automatically update the qso pane
		// waiting a minute between calls
		_timer = new Timer(iUpdateInterval * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				wakeUp();
			}
		});
		_timer.start();
	}

	private void initConnections() throws java.lang.Exception {
		getJB_update().addActionListener(eh);
	}

	// Initialize the class.
	private void initialize() {
		try {
			iUpdateInterval = 60; // default to a 60 second refresh interval

			setName("dxQSOFrame");
			setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			setResizable(true);
			setSize(505, 360);
			setVisible(true);
			setTitle("QSO Session");
			setContentPane(getJFrameContentPane());
			initConnections();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	private void jB_update_ActionEvents() {
		// the only action event should be "update"
		wakeUp();
		return;
	}

	/*
	 * make sure that init_qso_pane() is updated if there are changes in data
	 * file format it is basically the same as this but it does an insert
	 * instead of an append
	 */
	void wakeUp() {
		String strNewLastRead = new String();
		Stack<String> stackNewInfo = new Stack<String>();

		try {
			URL dxURL = new URL(strQSOURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					dxURL.openStream()));

			String strLine;
			String strOut;
			boolean bFirst;
			bFirst = true;
			while ((strLine = in.readLine()) != null) {
				try {
					strOut = _wdf.format(strLine);

					if (bFirst == true) {
						strNewLastRead = strOut;
						bFirst = false;
					}
					if (strLine.length() > 0) {
						if (strOut.compareTo(strLastRead) != 0) {
							stackNewInfo.push(strOut);
						} else {
							strLastRead = strNewLastRead;
							break; // hop out of while loop - done reading data
						}
					}
				} catch (Exception e) {
					// do nothing, end of contents
				}
			}

			while (stackNewInfo.empty() == false) {
				this._txtareaQSO.append((String) stackNewInfo.pop());
			}

		} catch (Exception e) {
			e.printStackTrace(); // something went wrong
		}
	}
}
