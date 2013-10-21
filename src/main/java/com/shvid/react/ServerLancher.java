package com.shvid.react;

import java.io.DataInputStream;

public class ServerLancher {

	public static void main(String[] args) {
				
		System.out.println("React started");
		System.out.println("Done");
		
	}

	private static void sleep() {
		
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void pause() {
		System.out.println("Enter a string to continue:");
		DataInputStream din = new DataInputStream(System.in);
		try {
			din.readLine();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}


