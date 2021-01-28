import implementation.*;
import core.*;
import java.util.ArrayList;
import java.util.Random;

public class TestMain {

    public static void main(String[] args) {

        AlbumsManager albumsManager = new AlbumsManager();
        ArtistsManager artistsManager = new ArtistsManager();
        ArrayList<Album> albums = new ArrayList<Album>();
        ArrayList<Artist> artists = new ArrayList<Artist>();
        int year = (int)(Math.random() * (2020 - 1979 + 1) + 1979);

        //Creating 10 random albums and artists
        for(int i = 0; i < 10; i++) {
            String ISRC = getRandomWord();
            String title = getRandomWord();
            String description = getRandomWord();
            String artist = getRandomWord();
            String nickname = getRandomWord();
            String first_name = getRandomWord();
            String last_name = getRandomWord();
            String biography = getRandomWord();

            Album newAlbum =new Album(ISRC, title, description, year, artist);
            albums.add(newAlbum);
            albumsManager.createAlbum(ISRC, title, description, year, artist);

            Artist newArtist = new Artist(nickname, first_name, last_name, biography);
            artists.add(newArtist);
            artistsManager.createArtist(nickname, first_name, last_name, biography);

            year = (int)(Math.random() * (2020 - 1979 + 1) + 1979);
        }

        //Printing all albums and artists
        System.out.println("\n\nGetting all albums\n" + albumsManager.getAllAlbums());
        System.out.println("\n\nGetting all artists\n" + artistsManager.getAllArtists());

        //Getting a specific album
        String isrc = albums.get(5).getISRC();
        Album testAlbum = albumsManager.getAlbum(isrc);
        System.out.println("\n\nGetting album with ISRC : " + isrc + "\n" + testAlbum);

        //Updating a specific album
        if(albumsManager.updateAlbum(isrc, "Gonzaga 68", "Gonzaga '68 bootleg features the band performing life", 1994, "Led Zeppelin"))
            System.out.println("\n\nAfter modified album " + isrc + ":\n" + albumsManager.getAlbum(isrc));

        //Deleting a specific album
        albumsManager.deleteAlbum(isrc);
        System.out.println("\n\nGetting all albums after deleting album with ISRC " + isrc + "\n" + albumsManager.getAllAlbums());

        //Getting a specific artist
        String nickname = artists.get(5).getNickname();
        Artist testArtist = artistsManager.getArtist(nickname);
        System.out.println("\n\nGetting artist with nickname : " + nickname + "\n" + testArtist);

        //Updating a specific album
        if(artistsManager.updateArtist(nickname, "Cameron", "Diaz", "She's a sexy mamasita"))
            System.out.println("\n\nAfter modified artist " + nickname + ":\n" + artistsManager.getArtist(nickname));

        //Deleting a specific album
        artistsManager.deleteArtist(nickname);
        System.out.println("\n\nGetting all artists after deleting artist with nickname " + nickname + "\n" + artistsManager.getAllArtists());
    }

    //Helper to get some random words
    public static String getRandomWord(){
        Random random = new Random();

        return random.ints(97, 122 + 1)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}