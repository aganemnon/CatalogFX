package ru.netcracker.ibublig.network;

import java.io.InputStream;

public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);
    void onReceiveByte(TCPConnection tcpConnection, InputStream value);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);
}
