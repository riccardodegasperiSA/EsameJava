package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class HTTPApp
{

    static ArrayList<Piatto> piatti = new ArrayList<>();

    public static void main( String[] args )
    {

        buildCitiesList();

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000),0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new HTTPHandler());
        server.setExecutor(null);
        server.start();
    }

    static void buildCitiesList() {
        piatti.add(new Piatto("Il tradizionale risotto allo zafferano", 1,
                "Risotto alla Milanese",12.20));
        piatti.add(new Piatto("La classica crostata di mele", 12,
                "Crostata di mele",22.50));
        piatti.add(new Piatto("Il classico primo", 4,
                "Spaghetti al pomodoro",7.79));
        piatti.add(new Piatto("La famosa pizza con pomodore e mozzarella", 2,
                "Pizza margherita",4.00));
        System.out.println(piatti);
    }
}
