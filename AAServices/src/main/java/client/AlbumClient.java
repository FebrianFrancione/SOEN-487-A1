package client;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AlbumClient {

    public AlbumClient() throws InterruptedException{
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
                    getAlbums(sc);
                    break;
                case 3:
                    addAlbum(sc);
                    break;
                case 4:
                    updateAlbum(sc);
                    break;
                case 5:
                    deleteAlbum(sc);
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
        System.out.println("1. Show the list of albums");
        System.out.println("2. Return the specific album info");
        System.out.println("3. Add a new album to the collection");
        System.out.println("4. Update album info");
        System.out.println("5. Delete the album only");
        System.out.println("6. Quit");
        System.out.println();

        System.out.print("Please choose one of the above numbers : ");
        try{
            int choose = sc.nextInt();
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
            HttpGet request = new HttpGet("http://localhost:8080/core/album/list");
            CloseableHttpResponse response = client.execute(request);
            System.out.println(readResponse(response));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void getAlbums(Scanner sc){
        String ISRC;
        String title;

        System.out.println("Please enter the ISRC and title of the album.");
        try{
            System.out.print("ISRC: ");
            ISRC = sc.next();
            sc.nextLine();

            System.out.print("Title: ");
            title = sc.nextLine();
            title = URLEncoder.encode(title);

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpGet request = new HttpGet(String.format("http://localhost:8080/core/album/%s/%s", ISRC, title));
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

    private static void addAlbum(Scanner sc){
        String ISRC;
        String title;
        String description;
        int year;
        String artist;

        System.out.println("Please enter the information of the album.");
        try {
            System.out.print("ISRC: ");
            ISRC = sc.next();
            sc.nextLine();

            System.out.print("Title: ");
            title = sc.nextLine();
            title = URLEncoder.encode(title);

            System.out.print("Description: ");
            description = sc.nextLine();
            description = URLEncoder.encode(description);

            System.out.print("Year: ");
            year = sc.nextInt();
            sc.nextLine();

            System.out.print("Artist: ");
            artist = sc.nextLine();
            artist = URLEncoder.encode(artist);

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpPost request = new HttpPost(String.format("http://localhost:8080/core/album/create/%s/%s/%s/%d/%s", ISRC, title, description, year, artist));
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

    private static void updateAlbum(Scanner sc){
        String ISRC;
        String title;
        String description;
        int year;
        String artist;

        System.out.println("Please enter the new information of the existed album.");
        try {
            System.out.print("ISRC: ");
            ISRC = sc.next();
            sc.nextLine();

            System.out.print("Title: ");
            title = sc.nextLine();
            title = URLEncoder.encode(title);

            System.out.print("Description: ");
            description = sc.nextLine();
            description = URLEncoder.encode(description);

            System.out.print("Year: ");
            year = sc.nextInt();
            sc.nextLine();

            System.out.print("Artist: ");
            artist = sc.nextLine();
            artist = URLEncoder.encode(artist);

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpPut request = new HttpPut(String.format("http://localhost:8080/core/album/%s/%s/%s/%d/%s", ISRC, title, description, year, artist));
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

    private static void deleteAlbum(Scanner sc){
        String ISRC;

        System.out.println("What album do you want to delete?");
        try{
            System.out.print("ISRC: ");
            ISRC = sc.next();

            try(CloseableHttpClient client = HttpClients.createDefault()){
                HttpDelete request = new HttpDelete(String.format("http://localhost:8080/core/album/%s", ISRC));
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
