package udp_s_c;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Initialize server socket
            socket = new DatagramSocket(9999);
            System.out.println("Server is running...");

            // Run the server to handle multiple client requests
            while (true) {
                // Buffer to receive data from clients
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Receive packet from client
                socket.receive(receivePacket);
                String receivedString = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Received from client: " + receivedString);

                try {
                    int number = Integer.parseInt(receivedString);
                    int result = number * number;

                    // Prepare response data
                    byte[] sendData = String.valueOf(result).getBytes();

                    // Send response to client
                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);

                    System.out.println("Sent result to client: " + result);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format received from client.");
                }
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
