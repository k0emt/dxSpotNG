package com.dbbear.dxspot;

/* this class is used to hold information about the current user 
 */

class User {
	static String CallSign = new String();
	static String Grid = new String();

	User() {
		CallSign = "MyCall";
		Grid = "MyGrid";
	}

	User(String strOp) {
		CallSign = strOp;
		Grid = "MyGrid";
	}

	User(String strOp, String strGrid) {
		CallSign = strOp;
		Grid = strGrid;
	}

	public String getGrid() {
		return Grid;
	}

	public String getCallSign() {
		return CallSign;
	}

	public void setGrid(String strGrid) {
		Grid = strGrid;
	}

	public void setCallSign(String strOp) {
		CallSign = strOp;
	}
}
