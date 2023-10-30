package Calculation;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PVGIS_API {

    static int[] monthlyValues = new int[12];

    public void getData(String lat, String lon, String loss, String peakPower){
        try {
            String urlString = String.format("https://re.jrc.ec.europa.eu/api/v5_2/PVcalc?raddatabase=PVGIS-SARAH2&lat=%s&lon=%s&loss=%s&peakpower=%s&mountingplace=building&outputformat=json", lat, lon, loss, peakPower);
            URL url = new URL(urlString);
            System.out.println(urlString);

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

                JSONObject json = new JSONObject(inline);
                getMonthlyValues(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getMonthlyValues(JSONObject json){
        try{
            for(int i = 0; i <= 11; i++){
                JSONObject values = new JSONObject(json.getJSONObject("outputs").getJSONObject("monthly").getJSONArray("fixed").getString(i));
                int value = values.getInt("H(i)_m");                                                                                               // "Average monthly sum of global irradiation per square meter received by the modules of the given system"
                monthlyValues[i] = value;
            }
        }catch (Exception e){
            System.out.println("handle ...");
        }
    }


    public int[] getMonthlyValues() {
        return monthlyValues;
    }


    public void printMonthlyValues(){
        for (int i : monthlyValues){
            System.out.println(i);
        }
    }

}

