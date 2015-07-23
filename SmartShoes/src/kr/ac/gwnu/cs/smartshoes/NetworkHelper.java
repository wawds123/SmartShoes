package kr.ac.gwnu.cs.smartshoes;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkHelper extends AsyncTask<String, Void, Void>
{
	private DatagramSocket m_sockClient = null;
	private DatagramPacket receivePacket = null;
	private DatagramPacket sendPacket = null;
	private InetAddress inetaddr= null;
	private int port = 8080;
	private String msg = "";

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		try
		{
			inetaddr = InetAddress.getByName(params[1]);
			m_sockClient = new DatagramSocket();
			sendPacket = new DatagramPacket(params[0].getBytes(), params[0].getBytes().length, inetaddr, port);
			m_sockClient.send(sendPacket);
			
			byte[] buffer = new byte[1024];
			receivePacket = new DatagramPacket(buffer, buffer.length);
			m_sockClient.receive(receivePacket);
			
			msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);
			Log.d("NetworkHelper_DEBUG",msg);
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

}
