package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

public class HTTPHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();

        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();

        //legge POST
        String s = read(is);

        if (s != null) {
            query = s;
        }

        String risultato = processa(query);

        String response = new String();

        if (s == null) {
            response = "<!doctype html>\n" +
                    "<html lang=en>\n" +
                    "<head>\n" +
                    "<meta charset=utf-8>\n" +
                    "<title>MyJava Sample</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    risultato + "\n" +
                    "</body>\n" +
                    "</html>\n";
        } else {
            response = risultato + "\n";
        }

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String processa(String query) {

        if (query.contains("=")){
            query = query.split("=")[1];
        }
        switch (query) {
            case "more_expensive": {
                return more_expensive(HTTPApp.piatti);
            }
            case "all": {
                return every(HTTPApp.piatti);
            }
//            case "sorted_by_name": {
//                return sortByName(App.piatti);
//            }
            case "all_sorted": {
                return allSorted(HTTPApp.piatti);
            }
            default: {
                return "not a command";
            }
        }
    }

//    private String sortByName(ArrayList<City> cities) {
//        ArrayList<City> sortedCities = cities;
//
//        sortedCities.sort((o1, o2) -> {
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
//        jsonString = gson.toJson(sortedCities);
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


    private String read(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        System.out.println("\n");
        String recieved = null;

        while (true) {
            String s = null;
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            recieved += s;
        }
        return recieved;
    }
}
