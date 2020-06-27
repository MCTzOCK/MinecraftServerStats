package de.mctzock.webserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Webserver extends Thread{
	
	public boolean online = false;	
	public ArrayList<Socket> clients = new ArrayList<Socket>();
	public ServerSocket server = null;
	public Integer port = 80;
	public String response = "";
	
	public Webserver(Integer port) throws Exception
	{
		this.port = port;
	}
	
	public void startServer() throws Exception
	{
		System.out.println("[WEBSERVER] Setting serverstatus to ONLINE!");
		server = new ServerSocket(this.port);
		this.online = true;
		while(this.isOnline())
		{
			this.handlePackets();
		}
	}
	
	public void stopServer() throws Exception
	{
		System.out.println("[WEBSERVER] Setting serverstatus to OFFLINE!");
		server.close();
		this.online = false;
	}
	
	public boolean isOnline()
	{
		return this.online;
	}

	@Override
	public void run()
	{
		while(isOnline())
		{
			try {
				Socket client = server.accept();
				clients.add(client);
				
				PrintWriter w = new PrintWriter(client.getOutputStream());
				BufferedOutputStream dataOut = new BufferedOutputStream(client.getOutputStream());
				byte[] data = this.toByteArray(this.response);
				
				w.println("HTTP/1.1 200 OK");
				w.println("Server: Java HTTP Webserver from MCTzOCK : 1.0");
				w.println("Date: " + new Date());
				w.println("Content-type: text/html");
				w.println("Content-lenght: " + this.response.length());
				w.println();
				w.flush();
				
				dataOut.write(data);
				dataOut.flush();
				
				w.close();
				dataOut.close();
				
				client.close();
				clients.remove(client);
			}catch (Exception ex)
			{
				
			}
		}
	}
	
	public byte[] toByteArray(String n) throws IOException 
	{
		byte[] fileData = n.getBytes();
		return fileData;
	}
	
	public void setResponse(String html)
	{
		System.out.println("[WEBSERVER] Setting response");
		this.response = html;
	}
	
	public String getResponse()
	{
		return this.response;
	}
}
