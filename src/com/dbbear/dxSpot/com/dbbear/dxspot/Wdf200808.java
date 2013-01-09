package com.dbbear.dxspot;


public class Wdf200808 implements IWebDataFormatter {

	@Override
	public String format(final String rawdata) 
	{
		// use the Wdf200604 code as a starting point
		int iPos;
		int iEnd = 0;
		
		final StringBuffer formatted = new StringBuffer("");
		
		if (rawdata.length() >= 15) 
		{
			iPos = rawdata.indexOf('<',19);
			
			// date time and call
			formatted.append( rawdata.substring(4,15)
					+ " "
					+ rawdata.substring(19,iPos)
					+ " "
					);

			// callsign
			formatted.append(" - ");
			iPos = rawdata.indexOf('>',iEnd) +28;
			iEnd = rawdata.indexOf('<',iPos);
			formatted.append(rawdata.substring(iPos,iEnd));
			
			// mode of operation
			iPos = rawdata.indexOf('>',iPos) +1;
			iEnd = rawdata.indexOf('<',iPos);
			formatted.append(rawdata.substring(iPos,iEnd));

			// frequency
			iPos = rawdata.indexOf('>',iPos) +1;
			iEnd = rawdata.indexOf('<',iPos);
			formatted.append(rawdata.substring(iPos,iEnd));
			
			formatted.append(' ');

			// grid
			formatted.append(' ');
			iPos = rawdata.indexOf('>',iPos) +29;
			iEnd = rawdata.indexOf('<',iPos) +6;
			formatted.append(rawdata.substring(iPos,iEnd));

			// comment
			// those pesky users can put all sorts of stuff
			// in the comments, including brackets
			// try and do some additional handling
			iPos = rawdata.indexOf('>',iPos) +6;
			iEnd = rawdata.indexOf('<',iPos);
			formatted.append(rawdata.substring(iPos,iEnd));
			
		}
		 return formatted.toString();
	}
}
