package com.panicgame.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.panicgame.managers.InputManager;
import com.panicgame.utils.Util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class TCPClient extends Thread {
	public Socket socket = null;
	public DataOutputStream dataOutput = null;
	public InputStreamReader inputstream = null;
	public BufferedReader input = null;
	public Handler h;
	boolean cancel = false;
	boolean connecting = true;
	static public Context context;
	static public ApplicationObject app;
	
	
	
	public TCPClient(Context context) {
		h = new Handler();
		this.context = context;
		app = (ApplicationObject) context.getApplicationContext();
	}

	@Override
	public void run() {
		while (connecting) {
			// Conecting..
			try {
				socket = new Socket();
				SocketAddress socketAddress = new InetSocketAddress(Util.SERVER_URL, Util.SERVER_PORT);
				socket.connect(socketAddress, 10000);
				Log.i("TCP", "After connect..");
				InputManager.getInstance(context).fireConnected();
				connecting = false;
			} catch (IllegalArgumentException e) {
				Log.i("NETWORK", "IllegalArgumentException");
			} catch (IOException e) {
				Log.i("NETWORK", "IOException");
				InputManager.getInstance(context).fireDisconnected();
				connecting = false;
				cancel = true;
				this.interrupt();
				e.printStackTrace();
			} finally {
				Log.i("NETWORK", "connection finally block");
			}
		}
		try {
			// Connected!
			dataOutput = new DataOutputStream(socket.getOutputStream());
			inputstream = new InputStreamReader(socket.getInputStream());
			input = new BufferedReader(inputstream);

			while (!interrupted() && !cancel) {
				Log.i("TCP_MESSAGE", "READING..");
				String s = input.readLine();
				if (s == null)break;
				InputManager.getInstance(context).handleInput(s);

			}

			input.close();
			dataOutput.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
			connecting = false;
			cancel = true;
			this.interrupt();
			InputManager.getInstance(context).fireDisconnected();
		} finally {

		}
	}

	public void write(final String message) {
		h.post(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i("TCP_MESSAGE", "SENDING..");
					String byte_message = message + "\n";
					dataOutput.write(byte_message.getBytes());
					Log.i("TCP_MESSAGE", "Message: " + byte_message + " SENT");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
