package com.dbbear.dxspottests;

import com.dbbear.dxspot.CommandProcessor;

import junit.framework.TestCase;

public class CommandProcessorTests extends TestCase {
	private CommandProcessor _cp = null;
	
	// set ups and tear downs -----------------------------------------------
	protected void setUp() throws Exception {
		super.setUp();
		_cp = new CommandProcessor();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
		_cp = null;
	}
	public void testSetUp() {
		assertNotNull(_cp);
	}
	
	// default, about and help message responses ----------------------------
	public void testDefaultMessage()
	{
		assertEquals("help or ? for command information", _cp.execute(""));
	}
	public void testHelpMessageTextForQuestionMark()
	{
		assertEquals(CommandProcessor.HelpMessageText, _cp.execute("?"));
	}
	public void testHelpMessageTextForHelp()
	{
		assertEquals(CommandProcessor.HelpMessageText, _cp.execute("help"));
	}
	public void testAboutReturnsExpected()
	{
		assertEquals(CommandProcessor.AboutText, _cp.execute("about"));
	}
	 
	// test opening up DX and QSO windows -----------------------------------
	public void test2d()
	{
		assertEquals("OK", _cp.execute("2d"));
	}
	public void test2q()
	{
		assertEquals("OK", _cp.execute("2q"));
	}
	public void test6d()
	{
		assertEquals("OK", _cp.execute("6d"));
	}
	public void testvq()
	{
		assertEquals("OK", _cp.execute("vq"));
	}
	public void testhd()
	{
		assertEquals("OK", _cp.execute("hd"));
	}
	public void testhq()
	{
		assertEquals("OK", _cp.execute("hq"));
	}
	
	// tests for opening up some web sites ----------------------------------
	public void testWebk0emt()
	{
		BrowserMock _bm = new BrowserMock();
		_cp.setBrowser(_bm);
		assertEquals("http://www.dbbear.com/k0emt/", _cp.execute("web k0emt"));
	}
	public void testWebDxWorld()
	{
		BrowserMock _bm = new BrowserMock();
		_cp.setBrowser(_bm);
		assertEquals("http://www.dxworld.com/", _cp.execute("web dxworld"));
	}
	public void testWebProp()
	{
		BrowserMock _bm = new BrowserMock();
		_cp.setBrowser(_bm);
		assertEquals("http://dx.qsl.net/propagation/", _cp.execute("web prop"));
	}
	public void testWebURL()
	{
		BrowserMock _bm = new BrowserMock();
		_cp.setBrowser(_bm);
		assertEquals("http://www.qrz.com/",_cp.execute("web http://www.qrz.com/"));
	}
		
	// tests for setting user information -----------------------------------
	public void testSetCallsign()
	{
		assertEquals("N0CAL", _cp.execute("set call N0CAL"));
	}
	public void testSetGridsquare()
	{
		assertEquals("EM38rp", _cp.execute("set grid EM38rp"));
	}
	
	// tests for DxZone IRC interface ---------------------------------------
	public void testDxZoneIRC()
	{
		assertEquals("DxZone IRC", _cp.execute("dxzone"));
	}
	
	// tests that haven't been implement yet, need to figure out how? ------- 
	public void IGNORED_testExitQuitBye()
	{
		// DEV how do I test that the application has terminated?
		assertEquals("WHAT DO I PUT HERE?", _cp.execute("exit"));
		assertEquals("WHAT DO I PUT HERE?", _cp.execute("quit"));
		assertEquals("WHAT DO I PUT HERE?", _cp.execute("bye"));
	}
}
