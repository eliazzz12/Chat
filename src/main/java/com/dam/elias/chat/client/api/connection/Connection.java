package com.dam.elias.chat.client.api.connection;

import javafx.application.Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

public class Connection {
    private Socket server;
    protected InetAddress address;
    protected int port;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    public Connection(Application.Parameters params) throws UnknownHostException {
        List<String> parameterList = params.getRaw();
        Iterator<String> iterator = parameterList.iterator();
        while (iterator.hasNext()) {
            String parameter = iterator.next();
            if(parameter.equalsIgnoreCase("-ip")) {
                String ip = iterator.next();
                System.out.println("Direccion ip: "+ip);
                setAddress(InetAddress.getByName(ip));
            }
            if(parameter.equalsIgnoreCase("-port")) {
                String port = iterator.next();
                System.out.println("Port: "+port);
                setPort(Integer.parseInt(port));
            }
        }
    }

    public void connect() throws IOException {
        System.out.println("Conectando a " + address.getCanonicalHostName() + ":" + port);
        server = new Socket(address, port);
        out = new ObjectOutputStream(server.getOutputStream());
        in = new ObjectInputStream(server.getInputStream());
    }

    /**
     * Closes the server connection
     *
     * @throws IOException if an I/ O error occurs when closing this socket.
     */
    public void disconnect() throws IOException {
        in.close();
        out.close();
        server.close();
    }

    private void setAddress(InetAddress address) {
        if(address == null) {
            throw new IllegalArgumentException("address cannot be null");
        }
        this.address = address;
    }

    private void setPort(int port) {
        if(port < 0 || port > 65535) {
            throw new IllegalArgumentException("invalid port (" + port + ")");
        }
        this.port = port;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }
}
