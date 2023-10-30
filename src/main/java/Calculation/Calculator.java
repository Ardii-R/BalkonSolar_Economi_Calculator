package Calculation;
import java.util.Arrays;

public class Calculator {

    // Constants for calculation
    protected float CO2Emissionsfaktor = 0.42f;
    protected int sonneneinstrahlung = 1000;            // Sonneneinstrahlung in Deutschland
    protected float verlust = 0.10f;                    // Verlust wegen Umwandlung


    // user input
    private float betrachtungsZeitraum;
    private int stromVerbrauchProJahr;
    private int leistungSolarmodul;                    // in watt
    private float neigungswinkel;
    private float strompreis;
    private float anschaffungskosten;
    private float wirkungsgrad;


    // calculated results
    float ErzeugterStromMax;
    float ErzeugterStromMin;
    float selbstversorgung;                           //pro Jahr
    float ErsparnisProJahrMin;
    float ErsparnisProJahrMax;
    float ErsparnisBetrachtungszeitraum;
    float Amortisationszeit;
    float GesparteCO2Emissionen;
    float monthlyValuesErsparnis[] = new float[12];
    float monthlyValuesErzeugterStrom[] = new float[12];




    public Calculator(float betrachtungsZeitraum, int stromVerbrauchProJahr, int leistungSolarmodul,
                           float neigungswinkel, float strompreis, float anschaffungskosten, float wirkungsgrad) {

        this.betrachtungsZeitraum = betrachtungsZeitraum;
        this.stromVerbrauchProJahr = stromVerbrauchProJahr;
        this.leistungSolarmodul = leistungSolarmodul;
        this.neigungswinkel = neigungswinkel;
        this.strompreis = strompreis;
        this.anschaffungskosten = anschaffungskosten;

        if(wirkungsgrad != 0){
            this.wirkungsgrad = wirkungsgrad;
        }else {
            this.wirkungsgrad = 0.19f;
        }
    }



    public void calculateErzeugtenStrom(){

         ErzeugterStromMax = ( this.leistungSolarmodul * wirkungsgrad * sonneneinstrahlung * neigungswinkel * (1-verlust) ) / 100;
         ErzeugterStromMin = ErzeugterStromMax * 0.9f;

         System.out.println("Erzeugter Strom ca. : " + ErzeugterStromMin + " bis " +  ErzeugterStromMax );
    }


    public void calculateSelbstversorgung(){
        selbstversorgung = ((ErzeugterStromMax + ErzeugterStromMin) / 2) / (this.stromVerbrauchProJahr / 100);
        System.out.println("Selbstversorgung pro Jahr in %: " + selbstversorgung + "%");
    }


    public void calculateErsparnisProJahr(){
         ErsparnisProJahrMin = ErzeugterStromMin * this.strompreis;
         ErsparnisProJahrMax = ErzeugterStromMax * this.strompreis;
        System.out.println("Ersparnis pro Jahr : " + ErsparnisProJahrMin  + " bis " +  ErsparnisProJahrMax );
    }


    public void calculateErsparnisBetrachtungsZeitraum(){
        ErsparnisBetrachtungszeitraum = (((ErsparnisProJahrMin + ErsparnisProJahrMax) /2 ) * this.betrachtungsZeitraum) - this.anschaffungskosten;
        System.out.println("Ersparnis Betrachtungszeitraum ca. : " + ErsparnisBetrachtungszeitraum);

    }


    public void calculateAmortisationszeit(){
        float var = this.anschaffungskosten / ((ErsparnisProJahrMin + ErsparnisProJahrMax) /2 );
        Amortisationszeit = Math.round(var);
        System.out.println("Amortisationszeit ca in : " + Amortisationszeit + " Jahren");
    }


    public void calculateCO2Emissionen() {
        GesparteCO2Emissionen = ((ErzeugterStromMin + ErzeugterStromMax) /2) * CO2Emissionsfaktor;
        System.out.println("Ersparte CO2 Emissionen: " + GesparteCO2Emissionen + " KG CO2");
    }


    public void calculcateMonatlicheErsparnis(int[] values){
        float sum = 0.0f;
        float sum2 = 0.0f;

        for(int i = 0; i <= 11; i++){

            float stromErzeugt = ( this.leistungSolarmodul * wirkungsgrad * values[i] * neigungswinkel ) / 100;                     // (1-verlust) weggelassen, da API bereits loss mitberechnet
            monthlyValuesErzeugterStrom[i] = stromErzeugt;

            float ersparnis = stromErzeugt * this.strompreis;
            monthlyValuesErsparnis[i] = ersparnis;

            System.out.println("API Values pro Monat: "+ values[i] + " Ersparnis pro Monat: " + ersparnis + " Strom pro Monat: " + stromErzeugt + " für den Monat: " + (i+1));
            sum += ersparnis;
            sum2 += stromErzeugt;
        }
        System.out.println("Summe Ersparnis: " +sum);
        System.out.println("Summe Strom: " +sum2);
    }


    public void printMonthlyValuesErsparnis(){
        for (float f : monthlyValuesErsparnis){
            System.out.println(f);
        }
    }

    public void printMonthlyValuesErzeugterStrom(){
        for (float f : monthlyValuesErzeugterStrom){
            System.out.println(f);
        }
    }
    
    public int getstromVerbrauchProJahr() {
    	return stromVerbrauchProJahr;
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "CO2Emissionsfaktor=" + CO2Emissionsfaktor +
                ", sonneneinstrahlung=" + sonneneinstrahlung +
                ", verlust=" + verlust +
                ", betrachtungsZeitraum=" + betrachtungsZeitraum +
                ", stromVerbrauchProJahr=" + stromVerbrauchProJahr +
                ", leistungSolarmodul=" + leistungSolarmodul +
                ", neigungswinkel=" + neigungswinkel +
                ", strompreis=" + strompreis +
                ", anschaffungskosten=" + anschaffungskosten +
                ", wirkungsgrad=" + wirkungsgrad +
                ", ErzeugterStromMax=" + ErzeugterStromMax +
                ", ErzeugterStromMin=" + ErzeugterStromMin +
                ", selbstversorgung=" + selbstversorgung +
                ", ErsparnisProJahrMin=" + ErsparnisProJahrMin +
                ", ErsparnisProJahrMax=" + ErsparnisProJahrMax +
                ", ErsparnisBetrachtungszeitraum=" + ErsparnisBetrachtungszeitraum +
                ", Amortisationszeit=" + Amortisationszeit +
                ", GesparteCO2Emissionen=" + GesparteCO2Emissionen +
                ", monthlyValuesErsparnis=" + Arrays.toString(monthlyValuesErsparnis) +
                ", monthlyValuesErzeugterStrom=" + Arrays.toString(monthlyValuesErzeugterStrom) +
                '}';
    }
}
