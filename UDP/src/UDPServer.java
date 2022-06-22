import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer extends Thread{
    private DatagramSocket socket;
    private byte [] buf = new byte[256];

    public UDPServer(int port){
        try{
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buf,buf.length);
        while(true){
            try{
                socket.receive(packet);
                String receivedMSG = new String(packet.getData(),0,packet.getLength());
                System.out.println("RECEIVED: " + receivedMSG);
                InetAddress inetAddress = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf,buf.length,inetAddress,port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        UDPServer udpServer = new UDPServer(4500);
        udpServer.start();
    }
}
