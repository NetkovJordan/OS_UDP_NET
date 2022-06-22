package finki.ukim.mk.os;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class UDPClient extends Thread{
    private String serverName;
    private DatagramSocket socket;
    private InetAddress inetAddress;
    private byte [] buf = new byte[256];
    private String message;
    private int port;
    public UDPClient(String serverName,int port,String message){
       this.serverName = serverName;
        this.port = port;
       this.message = message;
       try
       {
           socket = new DatagramSocket();
           inetAddress = InetAddress.getByName(serverName);
       } catch (SocketException e) {
           e.printStackTrace();
       } catch (UnknownHostException e) {
           e.printStackTrace();
       }
    }

    @Override
    public void run() {
       buf = message.getBytes();
       DatagramPacket packet = new DatagramPacket(buf,buf.length,inetAddress,port);
       try{
           socket.send(packet);
           packet = new DatagramPacket(buf,buf.length);
           socket.receive(packet);
           System.out.println(new String(packet.getData(),0,packet.getLength()));
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public static void main(String[] args) {
        UDPClient client = new UDPClient("localhost",4500,"Hello OS!");
        client.start();
    }
}
