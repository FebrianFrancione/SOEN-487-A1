package implementation;

import core.Album;

import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlbumsManager {
    private static ArrayList<Album> albums;

    public AlbumsManager() {
        this.albums = new ArrayList<>();
    }

    public Album createAlbum(String ISRC, String title, String description, int year, String artist){
        Album newAlbum = new Album(ISRC, title, description, year, artist);
        albums.add(newAlbum);
        return newAlbum;
    }

    public void deleteAlbum(String ISRC) {
        albums.removeIf(p -> (p.getISRC().equals(ISRC)));
    }

    public String getAllAlbums(){
        return albums.stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public Album getAlbum(String ISRC){

        Album album = albums.stream().filter(album1 -> album1.getISRC().equals(ISRC))
                .findFirst()
                .orElse(null);

        return album;
    }

    public boolean hasAlbums(){
        return albums.isEmpty();
    }

    public Album getAlbum(String ISRC, String title){

        Album album = albums.stream().filter(album1 -> album1.getISRC().equals(ISRC) && album1.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        return album;
    }

    public boolean updateAlbum(String ISRC, String title, String description, int year, String artist){

        Album album = getAlbum(ISRC);

        if(album != null){
            albums.stream().filter(album1 -> album1.getISRC().equals(ISRC))
                    .forEach(al -> {
                        al.setTitle(title);
                        al.setDescription(description);
                        al.setYear(year);
                        al.setArtist(artist);
                    });
            return true;
        }

        return false;
    }
}
