import ru.netcracker.ibublig.network.TCPConnection;
import ru.netcracker.ibublig.network.TCPConnectionListener;
import ru.netcracker.ibublig.server.contrller.TestWrapper;
import ru.netcracker.ibublig.server.model.User;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MainServer implements TCPConnectionListener{

    public static void main(String[] args) {
        new MainServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private ArrayList<User> users;
    private TestWrapper wrapper;
    private File usersXML;

    private MainServer() {
        usersXML = new File("C:\\Data", "users.xml");
        users = new ArrayList<>();
        users.add(new User("Test","test","test","test", true));
        wrapper = new TestWrapper(users);
        wrapper.setPersonFilePath(usersXML);
        wrapper.savePersonDataToFile(usersXML);
        wrapper.loadPersonDataFromFile(wrapper.getPersonFilePath());

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
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        if(value.equals("TEST_SOUT")){
            System.out.println("Тест удался");
        } else{
            System.out.println("Не удался");
        }
       // sendToAllConnections((String)value);
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value){
        System.out.println(value);
        final int cnt = connections.size();
        for (int i = 0; i < cnt; i++) connections.get(i).sendString(value);
    }
}
