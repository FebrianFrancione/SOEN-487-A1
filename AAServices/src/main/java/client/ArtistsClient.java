package client;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArtistsClient {

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
                    showAll();
                    break;
                case 2:
                    getArtist(sc);
                    break;
                case 3:
                    sendArtist(sc);
                    break;
                case 4:
                    updateArtist(sc);
                    break;
                case 5:
                    deleteArtist(sc);
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

    private static void showAll(){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet request = new HttpGet("http://localhost:8980/demo_war/artists");
            CloseableHttpResponse response = client.execute(request);
            System.out.println(readResponse(response));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void getArtist(Scanner sc){
        String nickname;

        System.out.println("Please enter the nickname of the artist.");
        try{
            System.out.print("Nickname: ");
            nickname = sc.nextLine();
            nickname = URLEncoder.encode(nickname);

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpGet request = new HttpGet("http://localhost:8980/demo_war/artists?nickname=" + nickname);
                CloseableHttpResponse response = client.execute(request);
                System.out.println(readResponse(response));
            }catch(IOException e){
                e.printStackTrace();
            }

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void sendArtist(Scanner sc){
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

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpPost request = new HttpPost("http://localhost:8980/demo_war/artists?nickname=" + nickname + "&first_name=" + first_name + "&last_name=" + last_name + "&biography=" + biography);
                CloseableHttpResponse response = client.execute(request);
                System.out.println(readResponse(response));
            }catch(IOException e){
                e.printStackTrace();
            }

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void updateArtist(Scanner sc){
        String nickname;
        String first_name;
        String last_name;
        String biography;

        System.out.println("Which artist's information do you want to update?");
        try {
            System.out.print("Nickname: ");
            nickname = sc.nextLine();

            System.out.print("First name: ");
            first_name = sc.nextLine();

            System.out.print("Last name: ");
            last_name = sc.nextLine();

            System.out.print("Biography: ");
            biography = sc.nextLine();

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpPut request = new HttpPut("http://localhost:8980/demo_war/artists");
                request.setEntity(new StringEntity("nickname=" + nickname + "#first_name=" + first_name + "#last_name=" + last_name + "#biography=" + biography));
                CloseableHttpResponse response = client.execute(request);
                System.out.println(readResponse(response));
            }catch(IOException e){
                e.printStackTrace();
            }

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void deleteArtist(Scanner sc){
        String nickname;

        System.out.println("Please enter the nickname of the artist to delete.");
        try{
            System.out.print("Nickname: ");
            nickname = sc.nextLine();
            nickname = URLEncoder.encode(nickname);

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpDelete request = new HttpDelete("http://localhost:8980/demo_war/artists?nickname=" + nickname);
                CloseableHttpResponse response = client.execute(request);
                System.out.println(readResponse(response));
            }catch(IOException e){
                e.printStackTrace();
            }

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static String readResponse(CloseableHttpResponse response) throws IOException{
        Scanner sc = new Scanner(response.getEntity().getContent());
        StringBuilder result = new StringBuilder();
        while(sc.hasNext()){
            result.append(sc.nextLine());
            result.append("\n");
        }
        response.close();
        return result.toString();
    }
}
