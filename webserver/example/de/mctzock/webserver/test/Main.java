package de.mctzock.webserver.test;

import de.mctzock.webserver.Webserver;

public class Main {
	
	public static void main(String[] args) throws Exception{
		Webserver server = new Webserver(800);
		server.setResponse("<h1>Hello World, ich bin cool!</h1><br><h2>Unterzeile :D</h2>");
		server.start();
	}
}
