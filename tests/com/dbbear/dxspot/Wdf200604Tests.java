package com.dbbear.dxspot;

import junit.framework.TestCase;

public class Wdf200604Tests extends TestCase {

	private IWebDataFormatter _wdf;
	
	protected void setUp() throws Exception {
		super.setUp();
		_wdf = new Wdf200604();
	}

	protected void tearDown() throws Exception {
		_wdf = null;
		super.tearDown();
	}

	public void testWdf200604init()
	{
		assertEquals("",_wdf.format(""));
	}
	
	public void testWdf200604Format()
	{
		assertEquals("May12 00:26 N7ARC (SSB) 50.125 5/8  DM41 into EM42 - W5DAS EM42\n",
					_wdf.format("<DT>May12 00:26 <B>N7ARC</B>(SSB) <B>50.125</B>  <i>5/8  DM41 into EM42</i>  -  <FONT COLOR=000088><B>W5DAS</B> </FONT>(<FONT COLOR=000088>EM42</FONT>)                    [adsl-144-207-220.jan.bellsouth.net <I>70.144.207.220</I>]"));
	}
	
	public void testWdf200604FormatWithWeirdInput()
	{
		assertEquals("Apr16 04:35 k7ad (ssb) 50.125 599 > dn06 - kg6tcv dm13kt\n",
					_wdf.format("<DT>Apr16 04:35 <B>k7ad</B>(ssb) <B>50.125</B>  <i>599 > dn06</i>  -  <FONT COLOR=000088><B>kg6tcv</B> </FONT>(<FONT COLOR=000088>dm13kt</FONT>)                    [1Cust1731.an3.lax32.da.uu.net <I>63.16.166.195</I>]"));
	}
}
