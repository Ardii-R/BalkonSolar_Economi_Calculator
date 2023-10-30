package Calculation;
import org.json.JSONArray;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GeoCoding_API {
    private float[] coordinates = new float[2];

    public void geoCoding(String location){
        try {
            URL url = new URL("https://geocode.maps.co/search?q=" + location);
            //System.out.println(url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();
                //System.out.println("scanner output: " + inline);

                JSONArray json = new JSONArray(inline);
                //System.out.println(json);

                String lat = json.getJSONObject(0).get("lat").toString();
                String lon = json.getJSONObject(0).get("lon").toString();
                parseCoodinates(lat, lon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void geoCoding(String location, String plz){
        try {
            URL url = new URL("https://geocode.maps.co/search?q=" + plz + " " + location);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();
                //System.out.println("scanner output: " + inline);

                JSONArray json = new JSONArray(inline);

                String lat = json.getJSONObject(0).get("lat").toString();
                String lon = json.getJSONObject(0).get("lon").toString();
                parseCoodinates(lat, lon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseCoodinates(String lat, String lon){
        float parsedLat = Float.parseFloat(lat);
        float parsedLon = Float.parseFloat(lon);

        coordinates[0] = parsedLat;
        coordinates[1] = parsedLon;
    }


    public float[] getCoordinates() {
        return coordinates;
    }
}
