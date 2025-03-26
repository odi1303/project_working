package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    private static SimpleServer server;

    public static void main(String[] args) throws IOException {
        server = new SimpleServer(3000);
        System.out.println("Starting server...");
        server.listen();
    }

}
