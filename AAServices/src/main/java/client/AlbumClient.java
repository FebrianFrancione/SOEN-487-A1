package client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Scanner;

public class AlbumClient {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int choose;

        do {
            System.out.println("Menu (Verb Lists) :");
            System.out.println("1. Show the list of albums by ISRC and title");
            System.out.println("2. Return the album info");
            System.out.println("3. Add a new album to the collection");
            System.out.println("4. Update album info");
            System.out.println("5. Delete the album only");
            System.out.println("6. Quit");
            System.out.println();

            System.out.print("Please choose one of the above numbers : ");
            choose = sc.nextInt();


            if(choose == 1){
                showAll();
            }
            else if(choose == 2){
//                albumInfo();
            }
            else if(choose == 3){
                String ISRC;
                String title;
                String description;
                int year;
                String artist;

                System.out.println("Please enter the information of the album.");
                System.out.print("ISRC: ");
                ISRC = sc.next();
                System.out.print("Title: ");
                title = sc.next();
                System.out.print("Description: ");
                description = sc.next();
                System.out.print("Year: ");
                year = sc.nextInt();
                System.out.print("Artist: ");
                artist = sc.next();

                addAlbum(ISRC, title, description, year, artist);
            }
            else if(choose == 4){
//                updateAlbum();
            }
            else if(choose == 5){
//                deleteAlbum();
            }
            else if (choose == 6) {
                System.out.println("The program is terminated.");
            }
            else{
                System.out.println("Please choose the number from 1 to 6.");
            }

            System.out.println();
        }while(choose != 6);

        System.exit(0);
    }

    private static String showAll(){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet request = new HttpGet("http://localhost:8080/core/album/list");
            CloseableHttpResponse response = client.execute(request);
            return readResponse(response);
        }catch(IOException e){
            e.printStackTrace();
            return "Failed to get a title";
        }
    }

    private static void addAlbum(String ISRC, String title, String description, int year, String artist){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost request = new HttpPost(String.format("http://localhost:8080/core/album/create/%s/%s/%s/%d/%s", ISRC, title, description, year, artist));
            CloseableHttpResponse response = client.execute(request);
            response.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String readResponse(CloseableHttpResponse response) throws IOException{
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
