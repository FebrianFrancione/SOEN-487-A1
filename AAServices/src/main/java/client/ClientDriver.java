package client;


import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientDriver {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int choose=0;
        boolean correct = true;

        System.out.println("Menu : ");
        System.out.println("1. Album");
        System.out.println("2. Artist");
        System.out.println();

        do {
            System.out.print("Please choose one of the above numbers : ");
            try {
                choose = sc.nextInt();
                System.out.println();

                switch(choose){
                    case 1:
                        AlbumClient album = new AlbumClient();
                        break;
                    case 2:
                        ArtistsClient artist = new ArtistsClient();
                        break;
                    default:
                        System.out.println("Please choose a number between 1 or 2.");
                }

            } catch (InputMismatchException | InterruptedException e) {
                sc.nextLine();
                System.out.println("Please put a correct number. Try again.");
            }

            System.out.println();

            if(choose == 1 || choose == 2)
                correct = false;
        }while(correct);
    }
}
