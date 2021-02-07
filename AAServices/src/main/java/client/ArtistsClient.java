package client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArtistsClient {

    public static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        int choose;

        do {
            choose = menu(sc);

            switch(choose){
                // If the user put a word
                case 0:
                    break;
                case 1:
                    sendGet();
                    break;
                case 2:
                    getArtist(sc);
                    break;
                case 3:
                    sendPost(sc);
                    break;
                case 4:
//                    updateAlbum(sc);
                    break;
                case 5:
//                    deleteAlbum(sc);
                    break;
                case 6:
                    System.out.println("The program is terminated.");
                    break;
                default:
                    System.out.println("You chose the wrong number.");
            }

            Thread.sleep(3000);
        }while(choose != 6);

        System.exit(0);

    }

    private static int menu(Scanner sc){
        System.out.println("Menu (Verb Lists) :");
        System.out.println("1. Show the list of artists");
        System.out.println("2. Return the specific artists info");
        System.out.println("3. Add a new artist to the collection");
        System.out.println("4. Update artist info");
        System.out.println("5. Delete the artist only");
        System.out.println("6. Quit");
        System.out.println();

        System.out.print("Please choose one of the above numbers : ");
        try{
            int choose = sc.nextInt();
            sc.nextLine();
            System.out.println();
            return choose;
        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("Please put a number. Try again.");
            System.out.println();
            return 0;
        }
    }

    private static void sendGet() throws Exception {
        String url = "http://localhost:8980/demo_war/artists";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        System.out.println(readResponse(con));
    }

    private static void getArtist(Scanner sc) throws Exception{
        String nickname;

        System.out.println("Please enter the nickname of the artist.");
        try {
            System.out.print("Nickname: ");
            nickname = sc.nextLine();
            nickname = URLEncoder.encode(nickname);

            String url = "http://localhost:8980/demo_war/artists";
            String urlParameters = String.format("nickname=%s",nickname);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            // send the parameter (query string)
            sendRequest(con, urlParameters);

            System.out.println(readResponse(con));

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void sendPost(Scanner sc) throws Exception{
        String nickname;
        String first_name;
        String last_name;
        String biography;

        System.out.println("Please enter the information of the artist.");
        try {
            System.out.print("Nickname: ");
            nickname = sc.nextLine();
            nickname = URLEncoder.encode(nickname);

            System.out.print("First name: ");
            first_name = sc.nextLine();
            first_name = URLEncoder.encode(first_name);

            System.out.print("Last name: ");
            last_name = sc.nextLine();
            last_name = URLEncoder.encode(last_name);

            System.out.print("Biography: ");
            biography = sc.nextLine();
            biography = URLEncoder.encode(biography);

            String url = "http://localhost:8980/demo_war/artists";
            String urlParameters = String.format("nickname=%s&first_name=%s&last_name=%s&biography=%s",nickname, first_name, last_name, biography);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            // send the parameter (query string)
            sendRequest(con, urlParameters);

            System.out.println(readResponse(con));

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void sendRequest(HttpURLConnection con, String urlParameters) throws IOException{
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

    private static String readResponse(HttpURLConnection con) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append("\n");
        }
        in.close();
        return response.toString();
    }
}
