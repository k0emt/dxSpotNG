package com.dbbear.dxspottests;
import com.dbbear.dxspot.*;

import junit.framework.TestCase;

public class Wdf200808Tests extends TestCase {

	private IWebDataFormatter webDataFormatter;
	
	protected void setUp() throws Exception {
		super.setUp();
		webDataFormatter = new Wdf200808();
	}

	protected void tearDown() throws Exception {
		webDataFormatter = null;
		super.tearDown();
	}

	public void testWdf200808init()
	{
		assertEquals("",webDataFormatter.format(""));
	}
		
	public void testWdf200808Format()
	{
		assertEquals("May31 16:45 K5SW 50.140 EM25<>EM90 Tnx Sam - KD4ULB\n",
					webDataFormatter.format("<DT>May31 16:45 <B>K5SW 50.140 EM25<>EM90 Tnx Sam - KD4ULB</B>          <FONT COLOR=0000BB><B></B></FONT>(kgldgaambas07-pool17-a38.kgldgaam.tds.net <I>69.130.81.38</I>)"));
	}
	/*
	public void testWdf200604FormatWithWeirdInput()
	{
		assertEquals("Apr16 04:35 k7ad (ssb) 50.125 599 > dn06 - kg6tcv dm13kt\n",
					_wdf.format("<DT>Apr16 04:35 <B>k7ad</B>(ssb) <B>50.125</B>  <i>599 > dn06</i>  -  <FONT COLOR=000088><B>kg6tcv</B> </FONT>(<FONT COLOR=000088>dm13kt</FONT>)                    [1Cust1731.an3.lax32.da.uu.net <I>63.16.166.195</I>]"));
	}
	 */
}
