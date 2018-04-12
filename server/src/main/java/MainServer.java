import ru.netcracker.ibublig.network.TCPConnection;
import ru.netcracker.ibublig.network.TCPConnectionListener;
import ru.netcracker.ibublig.server.contrller.TestWrapper;
import ru.netcracker.ibublig.server.model.User;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MainServer implements TCPConnectionListener{

    public static void main(String[] args) {
        new MainServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private ArrayList<User> users;
    private TestWrapper wrapper;
    private File usersXML = new File("C:\\Data","test.xml");

    private MainServer() {
        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(8189)) {
            while(true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("Connect " + tcpConnection);
    }

    @Override
    public void onReceiveByte(TCPConnection tcpConnection, InputStream is) {
        try(OutputStream os = new FileOutputStream(usersXML)){
            int i;
            byte[] bytes = new byte[4096];
            while ((i = is.read()) != -1)
                os.write(bytes,0, i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        System.out.println("Disconnect: " + tcpConnection);
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }
}
