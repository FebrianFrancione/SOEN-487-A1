package implementation;

import core.Artist;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArtistsManager {
    private ArrayList<Artist> artists;

    public ArtistsManager() {
        this.artists = new ArrayList<>();
    }

    public Artist createArtist(String nickname, String first_name, String last_name, String biography){
        Artist newArtist =new Artist(nickname, first_name, last_name, biography);
        artists.add(newArtist);
        return newArtist;
    }

    public void deleteArtist(String nickname) {
        artists.removeIf(p -> (p.getNickname().equals(nickname)));
    }

    public String getAllArtists(){
        return artists.stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public Artist getArtist(String nickname){

        Artist artist = artists.stream().filter(artist1 -> artist1.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        return artist;
    }

    public boolean updateArtist(String nickname, String first_name, String last_name, String biography){

        Artist artist = getArtist(nickname);

        if(artist != null){
            artists.stream().filter(artist1 -> artist1.getNickname().equals(nickname))
                    .forEach(ar -> {
                        ar.setFirst_name(first_name);
                        ar.setLast_name(last_name);
                        ar.setBiography(biography);
                    });
            return true;
        }

        return false;
    }
}
