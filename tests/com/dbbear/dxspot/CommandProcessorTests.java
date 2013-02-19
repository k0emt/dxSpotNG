package com.dbbear.dxspot;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class CommandProcessorTests extends TestCase {
	private CommandProcessor cmdProc = null;

	// set ups and tear downs -----------------------------------------------
	protected void setUp() throws Exception {
		super.setUp();
		String[] emptyArgs = new String[0];
		cmdProc = new CommandProcessor(emptyArgs);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		cmdProc = null;
	}

	public void testSetUp() {
		assertNotNull(cmdProc);
	}

	// default, about and help message responses ----------------------------
	public void testDefaultMessage() {
		assertEquals("help or ? for command information", cmdProc.execute(""));
	}

	public void testHelpMessageTextForQuestionMark() {
		assertEquals(CommandProcessor.HelpMessageText, cmdProc.execute("?"));
	}

	public void testHelpMessageTextForHelp() {
		assertEquals(CommandProcessor.HelpMessageText, cmdProc.execute("help"));
	}

	public void testAboutReturnsExpected() {
		assertEquals(CommandProcessor.AboutText, cmdProc.execute("about"));
	}

	public void testWhoAmI() {
		assertEquals("MyCall MyGrid", cmdProc.execute("whoami"));
	}
	// test opening up DX and QSO windows -----------------------------------
	public void test2d() {
		assertEquals("OK", cmdProc.execute("2d"));
	}

	public void testUhfd() {
		assertEquals("OK", cmdProc.execute("ud"));
	}

	public void test6d() {
		assertEquals("OK", cmdProc.execute("6d"));
	}

	public void testvq() {
		assertEquals("OK", cmdProc.execute("vq"));
	}

	public void testhd() {
		assertEquals("OK", cmdProc.execute("hd"));
	}

	public void testhq() {
		assertEquals("OK", cmdProc.execute("hq"));
	}

	// tests for opening up some web sites ----------------------------------
	public void testWebk0emt() {
		BrowserMock _bm = new BrowserMock();
		cmdProc.setBrowser(_bm);
		assertEquals("http://k0emt.dbbear.com/", cmdProc.execute("web k0emt"));
	}

	public void testWebDxWorld() {
		BrowserMock _bm = new BrowserMock();
		cmdProc.setBrowser(_bm);
		assertEquals("http://www.dxworld.com/", cmdProc.execute("web dxworld"));
	}

	public void testWebProp() {
		BrowserMock _bm = new BrowserMock();
		cmdProc.setBrowser(_bm);
		assertEquals("http://dx.qsl.net/propagation/",
				cmdProc.execute("web prop"));
	}

	public void testWebURL() {
		BrowserMock _bm = new BrowserMock();
		cmdProc.setBrowser(_bm);
		assertEquals("http://www.qrz.com/",
				cmdProc.execute("web http://www.qrz.com/"));
	}

	public void testQRZ() {
		BrowserMock _bm = new BrowserMock();
		cmdProc.setBrowser(_bm);
		assertEquals("http://www.wm7d.net/perl/ulsquery.pl?callsign=k0emt",
				cmdProc.execute("QRZ k0emt"));
	}

	public void testqrz() {
		BrowserMock _bm = new BrowserMock();
		cmdProc.setBrowser(_bm);
		assertEquals("http://www.wm7d.net/perl/ulsquery.pl?callsign=wm7d",
				cmdProc.execute("qrz wm7d"));
	}

	// tests for setting user information -----------------------------------
	public void testSetCallsign() {
		cmdProc.execute("set call N0CAL");
		assertEquals("N0CAL", cmdProc.getOpCallSign());
	}

	public void testSetGridsquare() {
		cmdProc.execute("set grid EM38rp");
		assertEquals("EM38rp", cmdProc.getOpGrid());
	}

	// scripted login / configuration tests ---------------------------------
	public void testFileExists() {
		String[] args = new String[] { "config" };
		CommandProcessor cmdProcessor = new CommandProcessor(args);
		assertEquals(args[0], cmdProcessor.configFileName);
	}

	public void testConfigScript() {
		List<String> script = Arrays.asList("set call t3st", "set grid EM48");
		
		cmdProc.runScript(script);

		assertEquals("t3st", cmdProc.getOpCallSign());
		assertEquals("EM48", cmdProc.getOpGrid());
	}
}
