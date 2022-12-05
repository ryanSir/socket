package org.example.c5.server;


import org.example.c5.constants.TCPConstants;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        ServerProvider.start(TCPConstants.PORT_SERVER);

        try {
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerProvider.stop();
    }
}
