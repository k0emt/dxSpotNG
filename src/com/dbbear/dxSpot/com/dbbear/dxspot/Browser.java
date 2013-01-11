package com.dbbear.dxspot;

//import java.net.URI;
//import java.awt.Desktop;

public class Browser implements IBrowser {

	@Override
	public void displayURL(String url) {
		if (!java.awt.Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported (fatal)");
			System.exit(1);
		}

		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

		if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
			System.err.println("Desktop doesn't support the browse action (fatal)");
			System.exit(1);
		}

		try {
			java.net.URI uri = new java.net.URI(fixUp(url));
			desktop.browse(uri);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	String fixUp(String rawUrl) {
		String url;
		if (rawUrl.contains(":")) {
			url = rawUrl;
		} else {
			url = "http://" + rawUrl;
		}
		
		return url;
	}
}