package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;

public class App {

    static ArrayList<Piatto> piatti = new ArrayList<>();

    private static ServerSocket serverSocket = null;

    private static Socket clientSocket = null;

    public static ArrayList<ClientHandler> connected = new ArrayList<>();

    public static void main(String[] args) {

        String hostName = "127.0.0.1";
        int portNumber = 1234;

        if (args.length > 0) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }




        creaServerSocket(portNumber);

        System.out.println("Server Started Hostname: " + hostName + " " + Instant.now());

        buildCitiesList();

        while (true){
            ciclo(portNumber, hostName);
        }

    }

    private static void ciclo(int portNumber, String hostName) {

        connettiClient();

        connected.add(new ClientHandler(serverSocket, clientSocket));

        connected.get(connected.size()-1).start();

        int count = connected.size();

    }

    private static void connettiClient() {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("accept failed " + e);
        }
    }

    private static void creaServerSocket(int portNumber) {

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println("reader failed "+ e);
        }

    }

    static void buildCitiesList() {
        piatti.add(new Piatto("Il tradizionale risotto allo zafferano", 1,
                "Risotto alla Milanese",12.20));
        piatti.add(new Piatto("La classica crostata di mele", 12,
                "Crostata di mele",22.50));
        piatti.add(new Piatto("Il classico primo", 4,
                "Spaghetti al pomodoro",7.79));
        piatti.add(new Piatto("La famosa pizza con pomodore e mozzarella", 2,
                "Pizza margherita",7.79));
        System.out.println(piatti);
    }

}