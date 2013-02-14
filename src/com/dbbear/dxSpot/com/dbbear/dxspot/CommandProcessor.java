package com.dbbear.dxspot;

import java.util.StringTokenizer;

public class CommandProcessor {
	final static int UPDATE_INTERVAL = 300; // update every 5 minutes

	public final static String HelpMessageText = new String(
			"\nhelp or ? - bring up this info\n"
					+ "exit, quit or bye - Exit the program\n"
					+ "2d open a 2M DX Spot window\n"
					+ "6d open a 6M DX Spot window\n"
					+ "hd open a HF DX Spot window\n"
					+ "ud open a UHF DX Spot window\n"
					+ "\n"
					+ "vq open a VHF QSO window\n"
					+ "hq open a HF QSO window\n"
					+ "If Callsign and Grid are set they are automatically appended.\n"
					+ "\n"
					+ "set call N0CAL - sets the callsign of the operator to N0CAL\n"
					+ "set grid EM38rp - sets the grid location of the operator EM38rp\n"
					+ "\n"
					+ "QRZ callsign - queries QRZ for the given callsign\n"
					+ "web dxworld - opens a browser for dxWorld\n"
					+ "web prop - opens a browser with propagation info\n"
					+ "web k0emt - opens a browser for k0emt home page\n"
					+ "web URL - opens a browser for the given URL\n"
					+ "about - information about this program\n");

	// TODO need to get or set Application name, version and copyright in shared
	// area
	public final static String AboutText = new String(
			"\nDx Command by k0emt@dbbear.com\n"
					+ "data via Bob\'s dxworld.com\n"
					+ "\n"
					+ "This program is designed as an alternate interface to dxworld.com\'s html interface.\n"
					+ "You should thoroughly familiarize yourself with dxworld.com BEFORE using dxSpot.\n"
					+ "If you don\'t visit dxworld.com you will miss out on a lot of resources.\n"
					+ "\n"
					+ "The DX window is for DX SPOTS ONLY.\n"
					+ "DO post the other ARS callsign, grid & frequency (QRG).\n"
					+ "\n"
					+ "The QSO windows ARE for ragchewing, video spots, etc.\n"
					+ "\n"
					+ "Design Considerations:\n"
					+ "Minimize the amount of bandwidth used when updating the data.\n"
					+ "(It only reads data up to the point of where it last read.)\n"
					+ "Provide as much data as possible (utilizes the look back log).\n"
					+ "Each frame is set to refresh 5 minutes from the last completed refresh.\n"
					+ "The user may also manually force an update.\n"
					+ "\n"
					+ "By changing the interface, the user can easily have DX Spot and QSO\n"
					+ "information open for 6M, 2M and HF.\n"
					+ "\n"
					+ "The users callsign and grid are automatically appended to every submission.\n"
					+ "IF they are set other than MyCall and MyGrid. \n"
					+ "\n"
					+ "HINT:\n"
					+ "Make sure to use the <ENTER> key when changing Callsign, Grid or\n"
					+ "submitting a DX Spot or comment to a QSO.\n"
					+ "\n"
					+ "TBD:\n"
					+ "User configuration file for Callsign, and Grid.\n"
					+ "Save contents to a file on the users machine.\n"
					+ "\n"
					+ "Please send your comments and suggestions to k0emt@dbbear.com");

	protected static User opHam = new User();

	private IBrowser browser;

	public IBrowser getBrowser() {
		return browser;
	}

	public void setBrowser(IBrowser _ib) {
		this.browser = _ib;
	}
	
	public String getOpCallSign() {
		return opHam.getCallSign();
	}
	
	public String getOpGrid() {
		return opHam.getGrid();
	}

	public String execute(final String command) {
		String sResult = new String("help or ? for command information");
		String sWebResult = "";

		if (command.equals("help") || command.equals("?")) {
			sResult = HelpMessageText;
		}

		if (command.equals("about")) {
			sResult = AboutText;
		}

		if (command.equals("exit") || command.equals("quit")
				|| command.equals("bye")) {
			System.exit(0);
		}

		if (command.equals("2d")) {
			// Launch a 2M DX frame, init & display
			dxQSOFrame dx2Mspot = new dxQSOFrame("2M DX",
					"http://dxworld.com/144proplookback.php",
					"http://dxworld.com/cgi-bin/2mprop.cgi");
			dx2Mspot.set_WebDataFormatter(new WdfPre200604());
			dx2Mspot.init_pane();
			dx2Mspot.iUpdateInterval = UPDATE_INTERVAL;
			dx2Mspot.setVisible(true);

			sResult = "OK";
		}

		if (command.equals("ud")) {
			// Launch a UHF DX frame
			dxQSOFrame dxUhfSpot = new dxQSOFrame("UHF DX",
					"http://dxworld.com/uhflookback.php",
					"http://dxworld.com/cgi-bin/uhflog.cgi");
			dxUhfSpot.set_WebDataFormatter(new WdfPre200604());
			dxUhfSpot.init_pane();
			dxUhfSpot.iUpdateInterval = UPDATE_INTERVAL;
			dxUhfSpot.setVisible(true);

			sResult = "OK";
		}

		// TODO: the dxworld website has changed the format of the 6M DX data
		// a new data formatter is needed
		if (command.equals("6d")) {
			// Launch a 6M DX frame, init & display
			dxQSOFrame dx6Mspot = new dxQSOFrame("6M DX",
					"http://dxworld.com/50proplookback.php",
					"http://dxworld.com/cgi-bin/50props.cgi");
			dx6Mspot.set_WebDataFormatter(new Wdf200604());
			dx6Mspot.init_pane();
			dx6Mspot.iUpdateInterval = UPDATE_INTERVAL;
			dx6Mspot.setVisible(true);

			sResult = "OK";
		}

		if (command.equals("vq")) {
			// Launch a VHF QSO frame, init, set update interval & make visible
			dxQSOFrame dx6Mqso = new dxQSOFrame("VHF QSO",
					"http://dxworld.com/vhfqsolookback.php",
					"http://dxworld.com/cgi-bin/vhfqso.cgi");
			dx6Mqso.set_WebDataFormatter(new WdfPre200604());
			dx6Mqso.init_pane();
			dx6Mqso.iUpdateInterval = UPDATE_INTERVAL;
			dx6Mqso.setVisible(true);

			sResult = "OK";
		}

		if (command.equals("hd")) {
			// Launch an HF DX frame, init & display
			dxQSOFrame dxHFspot = new dxQSOFrame("HF DX",
					"http://dxworld.com/hfproplookback.php",
					"http://dxworld.com/cgi-bin/hfprop.cgi");
			// "http://www.dxworld.com/files/hfprop22.htm",
			dxHFspot.set_WebDataFormatter(new WdfPre200604());
			dxHFspot.init_pane();
			dxHFspot.iUpdateInterval = UPDATE_INTERVAL;
			dxHFspot.setVisible(true);

			sResult = "OK";
		}

		if (command.equals("hq")) {
			// Launch an HF/Generic QSO frame
			dxQSOFrame dxHFqso = new dxQSOFrame("HF/General QSO",
					"http://dxworld.com/qsolookback.php",
					"http://dxworld.com/cgi-bin/qso.cgi");
			dxHFqso.set_WebDataFormatter(new WdfPre200604());
			dxHFqso.init_pane();
			dxHFqso.iUpdateInterval = UPDATE_INTERVAL; // set to the user
														// specified update
														// interval
			dxHFqso.setVisible(true);

			sResult = "OK";
		}

		if (command.startsWith("set call ")) {
			opHam.setCallSign(command.substring(9));
			sResult = opHam.getCallSign();
		}
		
		if (command.startsWith("set grid ")) {
			opHam.setGrid(command.substring(9));
			sResult = opHam.getGrid();
		}
		
		if (command.toUpperCase().startsWith("QRZ ")) {
			String queryUrl = "http://www.wm7d.net/perl/ulsquery.pl?callsign=" +
		command.substring(command.indexOf(" ") + 1);
			browser.displayURL(queryUrl);
			sResult = queryUrl;
		}
		
		// if all else fails, try to run it through the web
		sWebResult = cmdProcessorWebHelper(command);

		if (!sWebResult.equals("")) {
			sResult = sWebResult;
		}

		return sResult;
	}

	protected String cmdProcessorWebHelper(String command) {
		String sResult = new String("");

		// generic processing
		StringTokenizer sCmdTok;
		String sURL;

		sCmdTok = new StringTokenizer(command);

		if (sCmdTok.countTokens() == 2) {
			sURL = new String(sCmdTok.nextToken());
			if (sURL.equals("web")) {
				sURL = new String(sCmdTok.nextToken());
				sResult = sURL;

				if (sURL.equals("k0emt")) {
					browser.displayURL("http://k0emt.dbbear.com/");
					sResult = "http://k0emt.dbbear.com/";
				}

				if (sURL.equals("dxworld")) {
					browser.displayURL("http://www.dxworld.com/");
					sResult = "http://www.dxworld.com/";
				}

				if (sURL.equals("prop")) {
					browser.displayURL("http://dx.qsl.net/propagation/");
					sResult = "http://dx.qsl.net/propagation/";
				}

				// result is still equal to command,
				// so it wasn't a special case and wasn't launched
				if (sResult.equals(sURL)) {
					browser.displayURL(sURL);
				}
			}
		}

		return sResult;
	}
}
