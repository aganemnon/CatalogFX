package ru.netcracker.ibublig.network;

import java.io.*;
import java.net.Socket;

public class TCPConnection {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;

    private final OutputStream os;
    private final InputStream is;

    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port) throws IOException {
        this(eventListener, new Socket(ipAddr, port));
    }

    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;

        os = socket.getOutputStream();
        is = socket.getInputStream();

        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int c;
                    eventListener.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()) {
                        eventListener.onReceiveByte(TCPConnection.this, is);
                        while ((c = is.read()) != -1) {
                        }
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

    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }

    public synchronized void sendString(String value) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
            bw.write(value);
            bw.flush();
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public synchronized void sendFile(File file) {
        try (InputStream is = new FileInputStream(file)) {
            int count;
            byte[] bytes = new byte[4096];
            while ((count = is.read(bytes)) > 0) {
                os.write(bytes, 0, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendTestMessage() {
        try {
            os.write(1);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendObject(Object object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAuthorization(String text, String s) {
        System.out.println(text + " " + s);
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }


}
