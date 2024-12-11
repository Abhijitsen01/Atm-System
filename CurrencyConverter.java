import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_KEY = "515b58c62d720d0b06b72c87"; 
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.print("Enter base currency (e.g., USD, EUR): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        
        System.out.print("Enter target currency (e.g., USD, EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        
        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        try {
           
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
            if (exchangeRate == -1) {
                System.out.println("Error fetching exchange rate. Please check the currency codes.");
                return;
            }

            
            double convertedAmount = amount * exchangeRate;

            
            System.out.printf("%.2f %s = %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            URL url = new URL(API_URL + baseCurrency);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            String targetRateKey = "\"" + targetCurrency + "\":";
            int startIndex = jsonResponse.indexOf(targetRateKey) + targetRateKey.length();
            int endIndex = jsonResponse.indexOf(",", startIndex);
            String rateString = jsonResponse.substring(startIndex, endIndex).trim();

            return Double.parseDouble(rateString);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}