package implementation;

import core.Artist;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArtistsManager {
    private ArrayList<Artist> artists;
    private ServletContext servletContext;

    public ArtistsManager() {
        this.artists = new ArrayList<>();
        populate();
    }

    public Artist createArtist(String nickname, String first_name, String last_name, String biography){
        if(getArtist(nickname) == null)
        {
            Artist newArtist =new Artist(nickname, first_name, last_name, biography);
            artists.add(newArtist);
            return newArtist;
        }

        else
            return null;
    }

    public ArrayList<Artist> getList(){
        if(servletContext == null)
            return null;
        if(!hasArtists())
            populate();
        return this.artists;
    }

    public void setServletContext(ServletContext sctx){
        this.servletContext = sctx;
    }

    public boolean addArtistObject(Artist artist){
        if(getArtist(artist.getNickname()) == null){
            return artists.add(artist);
        }

        return false;
    }

    public void deleteArtist(String nickname) {
        artists.removeIf(p -> (p.getNickname().equals(nickname)));
    }

    public String getAllArtists(){
        return artists.stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public boolean hasArtists(){
        return !artists.isEmpty();
    }

    public Artist getArtist(String nickname){

        return artists.stream().filter(artist1 -> artist1.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }

    public Artist getArtist(int index){
        if(index < 0 || index >= artists.size())
            return null;
        else
            return artists.get(index);
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

    private void populate() {
        String[] nicknames = {"Ronaldo", "Tiger Woods", "Rocky", "Iron Man"};
        String[] first_names = {"Ronaldo Luis", "Eldrick", "Rocco", "Anthony Edward"};
        String[] last_names = {"Nazario de Lima", "Tont Woods", "Francis Marchegiano", "Stark"};
        String[] bios = {"Brazilian soccer player", "American professional golfer", "American professional boxer",
                "fictional super hero"};

        for (int i = 0; i < nicknames.length; i++) {
            createArtist(nicknames[i], first_names[i], last_names[i], bios[i]);
        }
    }
}
