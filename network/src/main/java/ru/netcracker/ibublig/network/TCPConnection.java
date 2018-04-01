package ru.netcracker.ibublig.network;

import java.io.*;
import java.net.Socket;

public class TCPConnection {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final PrintStream printStream;
    private final OutputStream testOut;
    private final InputStream testTestInput;
    private final OutputStream testOutPut;

    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port) throws IOException {
        this(eventListener, new Socket(ipAddr, port));
    }

    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;
        File file = new  File("C:\\Data","testServer.xml");
        testTestInput = new FileInputStream(file);
        testOutPut = socket.getOutputStream();
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(socket.getOutputStream());
        printStream = new PrintStream(new BufferedOutputStream(socket.getOutputStream(), 1024), false);
        testOut = socket.getOutputStream();
        final int[] cout = new int[1];
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()) {
                        byte[] bytes = new byte[1024];
                        cout[0] = testTestInput.read(bytes);
                        while ((cout[0])>0){
                            out.write(bytes,0,cout[0]);
                        }
                        String outMessage = in.readUTF();
                        String test = outMessage.substring(0,9);
                        if(test.equals("TEST_SOUT")){
                            eventListener.onReceiveString(TCPConnection.this, "Тест удался 2");
                        }
                        System.out.println("RRRRRRRR");
                        eventListener.onReceiveString(TCPConnection.this,test);
                        eventListener.onReceiveString(TCPConnection.this, outMessage);
                    }
                } catch (IOException e) {
                    eventListener.onException(TCPConnection.this, e);
                } finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void sendString(String value) {
        try {
            out.writeUTF(value);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public synchronized void sendXml(File file){

    }

    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(this, e);
        }
    }

    public InputStream getTestTestInput() {
        return testTestInput;
    }

    public OutputStream getTestOutPut() {
        return testOutPut;
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
