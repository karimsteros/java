package udp_s_c;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Initialize client socket
            socket = new DatagramSocket();

            // Data to be sent to the server
            int number = 8;
            byte[] sendData = String.valueOf(number).getBytes();

            // Server details (address and port)
            InetAddress serverAddress = InetAddress.getLocalHost();
            int serverPort = 9999;

            // Sending the data to the server
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // Buffer to receive the response from the server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Receive the response from the server
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Result from server: " + response);
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
