package client;

import java.net.URLEncoder;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientDriver {

    public static AlbumClient albumClient;
    public static ArtistsClient artistClient;

    public static void main(String[] args){
        albumClient = new AlbumClient();
        artistClient = new ArtistsClient();
        printMainMenu();
    }

    private static void printMainMenu(){
        Scanner sc = new Scanner(System.in);
        int choose=0;
        boolean correct = true;

        System.out.println("\nMenu : ");
        System.out.println("1. Album");
        System.out.println("2. Artist");
        System.out.println("3. Quit");
        System.out.println();

        do {
            System.out.print("Please choose one of the above numbers : ");
            try {
                choose = sc.nextInt();
                System.out.println();

                switch(choose){
                    case 1:
                        albumsDriver();
                        break;
                    case 2:
                        artistsDriver();
                        break;
                    case 3:
                        System.out.println("The program is terminated.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please choose a number between 1 or 2.");
                }

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please put a correct number. Try again.");
            }

            System.out.println();

            if(choose == 1 || choose == 2)
                correct=false;

        }while(correct);


    }

    private static void albumsDriver(){
        Scanner sc = new Scanner(System.in);
        int choose;

        do {
            choose = AlbumsMenu(sc);

            switch(choose){
                case 1:
                    displayAlbums();
                    break;
                case 2:
                    retrieveAlbum(sc);
                    break;
                case 3:
                    addOrUpdateAlbum(true, false, sc);
                    break;
                case 4:
                    addOrUpdateAlbum(false, true, sc);
                    break;
                case 5:
                    deleteAlbum(sc);
                    break;
                case 6:
                    System.out.println("Exiting albums driver.\n");
                    break;
                default:
                    System.out.println("You chose the wrong number.\n");
            }

        }while(choose != 6);

        printMainMenu();
    }

    private static void artistsDriver(){
        Scanner sc = new Scanner(System.in);
        int choose;

        do {
            choose = ArtistsMenu(sc);

            switch(choose){
                case 1:
                    displayArtists();
                    break;
                case 2:
                    retrieveArtist(sc);
                    break;
                case 3:
                    addOrUpdateArtist(true, false, sc);
                    break;
                case 4:
                    addOrUpdateArtist(false, true, sc);
                    break;
                case 5:
                    deleteArtist(sc);
                    break;
                case 6:
                    System.out.println("Exiting artists driver.\n");
                    break;
                default:
                    System.out.println("You chose the wrong number.\n");
            }

        }while(choose != 6);

        printMainMenu();
    }

    private static int AlbumsMenu(Scanner sc){
        System.out.println("\n\nMenu (Verb Lists) :");
        System.out.println("1. Show the list of albums");
        System.out.println("2. Return the specific album info");
        System.out.println("3. Add a new album to the collection");
        System.out.println("4. Update existing album info");
        System.out.println("5. Delete an existing album");
        System.out.println("6. Return to main menu");
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

    private static int ArtistsMenu(Scanner sc){
        System.out.println("\n\nMenu (Verb Lists) :");
        System.out.println("1. Show the list of artists");
        System.out.println("2. Return the specific artists info");
        System.out.println("3. Add a new artist to the collection");
        System.out.println("4. Update existing artist info");
        System.out.println("5. Delete an existing artist");
        System.out.println("6. Return to main menu");
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

    private static void displayAlbums(){
        albumClient.showAll();
    }

    private static void displayArtists(){
        artistClient.showAll();
    }

    private static void retrieveAlbum(Scanner sc){
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

            albumClient.getAlbums(ISRC, title);

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void retrieveArtist(Scanner sc){
        String nickname;

        System.out.println("Please enter the nickname of the artist.");
        try{
            System.out.print("Nickname: ");
            nickname = sc.nextLine();
            nickname = URLEncoder.encode(nickname);

            artistClient.getArtist(nickname);

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void addOrUpdateAlbum(boolean add, boolean update, Scanner sc){
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

            if(add)
                albumClient.addAlbum(ISRC, title, description, artist, year);
            else if(update)
                albumClient.updateAlbum(ISRC, title, description, artist, year);

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

    private static void addOrUpdateArtist(boolean add, boolean update, Scanner sc){
        String nickname;
        String first_name;
        String last_name;
        String biography;

        System.out.println("Please enter the information of the artist.");
        try {
            System.out.print("Nickname: ");
            nickname = sc.nextLine();
            System.out.print("First name: ");
            first_name = sc.nextLine();
            System.out.print("Last name: ");
            last_name = sc.nextLine();
            System.out.print("Biography: ");
            biography = sc.nextLine();

            if(add){
                nickname = URLEncoder.encode(nickname);
                first_name = URLEncoder.encode(first_name);
                last_name = URLEncoder.encode(last_name);
                biography = URLEncoder.encode(biography);
                if(nickname.isEmpty()){
                    throw new InputMismatchException("Nickname Error! Nickname field cannot be left blank!");
                }else if(first_name.isEmpty()){
                    throw new InputMismatchException("First Name Error! First Name field cannot be left blank!");
                }else if (last_name.isEmpty()){
                    throw new InputMismatchException("Last Name Error! Last Name field cannot be left blank!");
                }
                artistClient.sendArtist(nickname, first_name, last_name, biography);
                System.out.println("addOrUpdateArtist: add");
            }
            else if(update)
                artistClient.updateArtist(nickname, first_name, last_name, biography);
            System.out.println("addOrUpdateArtist: update");


        }catch(InputMismatchException e){
//            sc.nextLine();
            System.out.println("You put the wrong information: " + e.getMessage() + " Try again.");
            System.out.println();
        }
    }

    private static void deleteAlbum(Scanner sc){
        String ISRC;

        System.out.println("What album do you want to delete?");
        try{
            System.out.print("ISRC: ");
            ISRC = sc.next();
            albumClient.deleteAlbum(ISRC);

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
            artistClient.deleteArtist(nickname);

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("You put the wrong information. Try again.");
            System.out.println();
        }
    }

}
