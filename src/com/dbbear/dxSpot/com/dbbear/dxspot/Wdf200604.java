package com.dbbear.dxspot;

public class Wdf200604 implements IWebDataFormatter {

	public String format(String rawdata) {
		int iPos;
		int iEnd;
		
		String formatted;
		
		if (rawdata.length() < 15) return "";
				
		iPos = rawdata.indexOf('<',19);
		
		// date time and call
		formatted = rawdata.substring(4,15)
				+ " "
				+ rawdata.substring(19,iPos)
				+ " ";
		
		// mode of operation
		iPos = rawdata.indexOf('>',iPos) +1;
		iEnd = rawdata.indexOf('<',iPos);
		formatted += rawdata.substring(iPos,iEnd);
		
		// frequency
		iPos = rawdata.indexOf('>',iPos) +1;
		iEnd = rawdata.indexOf('<',iPos);
		formatted += rawdata.substring(iPos,iEnd);
		
		formatted += " ";
		
		// comment
		// those pesky users can put all sorts of stuff
		// in the comments, including brackets
		// try and do some additional handling
		iPos = rawdata.indexOf('>',iPos) +6;
		iEnd = rawdata.indexOf('<',iPos);
		formatted += rawdata.substring(iPos,iEnd);

		// callsign
		formatted += " - ";
		iPos = rawdata.indexOf('>',iEnd) +28;
		iEnd = rawdata.indexOf('<',iPos);
		formatted += rawdata.substring(iPos,iEnd);
		
		// grid
		formatted += " ";
		iPos = rawdata.indexOf('>',iPos) +29;
		iEnd = rawdata.indexOf('<',iPos);
		formatted += rawdata.substring(iPos,iEnd) + "\n";;
		
		 return formatted;
	}
}
