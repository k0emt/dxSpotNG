package com.dbbear.dxspot;

public final class WebDataFormatter_Pre200604 implements IWebDataFormatter {

	public String format(String rawdata) {
		String strOut;
		int iKeep;	// place holder
		int iEnd;
		
		// if the string isn't empty print it
		// the date, have to skip leading <DT>
		iKeep = rawdata.indexOf('<',2);
		strOut = rawdata.substring(4, iKeep - 1 ).concat(" ");
		
		// the message
		iEnd = rawdata.indexOf('<',iKeep +3);
		return strOut.concat(rawdata.substring(iKeep +3, iEnd )) + "\n";
	}
}
