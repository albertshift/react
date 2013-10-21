package com.shvid.react;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public final class NetworkUtil {

	private static void throwBusyException(String address, int port, Exception e) {
		throw new ReactConfigurationException("can't bind to port at " + address + ":" + port, e);
	}
	
	public static void ensurePortIsFree(String address, int port) {
		try {
			Socket socket = new Socket(address, port);
			socket.close();
		} catch (ConnectException e) {
		} catch (SocketException e) {
			if (e.getMessage().equals("Connection reset by peer")) {
				return;
			}
			throwBusyException(address, port, e);
		} catch (UnknownHostException e) {
			throwBusyException(address, port, e);
		} catch (IOException e) {
			throwBusyException(address, port, e);
		}
	}

}
