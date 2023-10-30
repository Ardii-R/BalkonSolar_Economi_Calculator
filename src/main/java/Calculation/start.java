package Calculation;

public class start {

    static String lat;
    static String lon;
    static String loss;
    static String peakPower;

    static GeoCoding_API geoCode = new GeoCoding_API();
    static PVGIS_API pvgis = new PVGIS_API();
    static Calculator calculation;

    public static void main(String[] args) {

        geoCode.geoCoding("Berlin", "10115");
        parseUserInput();

        pvgis.getData(lat, lon, loss, peakPower);

        Calculator calc = new Calculator(12, 2700, 600, 0.45f, 0.45f, 1100f, 0);
        calc.calculateErzeugtenStrom();
        calc.calculateSelbstversorgung();
        calc.calculateErsparnisProJahr();
        calc.calculateErsparnisBetrachtungsZeitraum();
        calc.calculateAmortisationszeit();
        calc.calculateCO2Emissionen();
        calc.calculcateMonatlicheErsparnis(pvgis.getMonthlyValues());
        System.out.println(calc.toString());


    }

    private static String createResponseString(Calculator calc) {

		// ...

		return "";
	}

    public static void parseUserInput(){
       float[] t =  geoCode.getCoordinates();
       lat = Float.toString(t[0]);
       lon = Float.toString(t[1]);

       loss = "10";
       peakPower = "1";

    }
}
