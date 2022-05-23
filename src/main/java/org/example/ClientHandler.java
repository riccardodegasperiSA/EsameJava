package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{

    private ServerSocket serverSocket;

    private Socket clientSocket = null;

    private BufferedReader in = null;

    private PrintWriter out = null;

    private String userName;

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public String getUserName() {
        return userName;
    }

    public ClientHandler(ServerSocket serverSocket, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;

        creaOut();

        creaIn();

    }

    public void run(){

        comunica();

    }

    private void comunica() {

        String s;

        try {

            while ((s = in.readLine()) != null) {
                out.println(process(s));

            }
        } catch (IOException e) {
            System.err.println("Connection failed " + e);
        }
        finally {
            App.connected.remove(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void creaOut() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creaIn() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("accept failed "+ e);
        }
    }

    private String process(String s) {


//        String result;

        switch (s) {
            case "more_expensive": {
                return more_expensive(App.piatti);
            }
            case "all": {
                return every(App.piatti);
            }
//            case "sorted_by_name": {
//                return sortByName(App.piatti);
//            }
            case "all_sorted": {
                return allSorted(App.piatti);
            }
            default: {
                return "not a command";
            }
        }

    }

//    private String sortByName(ArrayList<Piatto> piatti) {
//        ArrayList<Piatto> sortedPiatti = piatti;
//
//        sortedPiatti.sort((o1, o2) -> {
//            return o1.name.compareTo(o2.name);
//        });
//
////        String [] nameArray = new String[piatti.size()];
////        int i = 0;
////
////        for (City c : piatti) {
////            nameArray[i] = c.name;
////            i++;
////        }
////        Arrays.sort(nameArray);
////
////        for (i = 0; i < nameArray.length; i++) {
////            for (City c : piatti) {
////                if (c.name == nameArray[i]){
////                    sortedCities.add(c);
////                    break;
////                }
////            }
//////            piatti.remove(sortedCities.get(i));
////        }
//
//        Gson gson = new Gson();
//        String jsonString = new String();
//
//        jsonString = gson.toJson(sortedPiatti);
//
//        return jsonString;
//    }

    private String allSorted(ArrayList<Piatto> piatti) {

        ArrayList<Piatto> sortedPiatti = piatti;

        sortedPiatti.sort((o1, o2) -> {
//            return o1.id.compareTo(o2.id); //non capisco l'errore
            if (o2.id > o1.id)
                return -1;
            else
                return 1;
        });

//        ArrayList<City> sortedPiatti = new ArrayList<>();
//        double [] tempArray = new double[piatti.size()];
//        int i = 0;
//
//        for (City c : piatti) {
//            tempArray[i] = c.temp;
//            i++;
//        }
//        Arrays.sort(tempArray);
//
//        for (i = 0; i < tempArray.length; i++) {
//            for (City c : piatti) {
//                if (c.temp == tempArray[i]){
//                    sortedPiatti.add(c);
//                    break;
//                }
//            }
////            piatti.remove(piatti.get(i));
//        }
//
        Gson gson = new Gson();
        String jsonString = new String();

        jsonString = gson.toJson(sortedPiatti);

        return jsonString;
    }

    private String every(ArrayList<Piatto> piatti) {
        Gson gson = new Gson();
        String jsonString = new String();

        jsonString = gson.toJson(piatti);

        return jsonString;

    }

    private String more_expensive(ArrayList<Piatto> piatti) {
        double maxtemp = 0;
        for (Piatto c : piatti) {
            if (c.price > maxtemp) {
                maxtemp = c.price;
            }
        }

        Gson gson = new Gson();
        String jsonString = new String();

        for (Piatto c : piatti){
            if (c.price == maxtemp){
                jsonString = gson.toJson(c);
                break;
            }
        }

        return jsonString;
    }

}
