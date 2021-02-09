package implementation;

import core.Artist;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArtistsManager {
    private static CopyOnWriteArrayList<Artist> artists;
    private ServletContext servletContext;

    public ArtistsManager() {
        artists = new CopyOnWriteArrayList<>();
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

    public CopyOnWriteArrayList<Artist> getList(){
        if(servletContext == null)
            return null;
        if(!hasArtists())
            populate();

        return artists;
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

    public boolean deleteArtist(String nickname) {
        return artists.removeIf(p -> (p.getNickname().equals(nickname)));
    }

    public String getAllArtists(){
        StringBuilder albumsString= new StringBuilder();
        Iterator<Artist> itr = artists.iterator();
        while(itr.hasNext()) {
            albumsString.append(itr.next().toString()).append("\n");
//            albumsString.append("Nickname: " + itr.next().getNickname() + ", Full Name: " + itr.next().getFirst_name() + ", " + itr.next().getLast_name()).append("\n");
//            albumsString.append(itr.next().getNickname().toString()).append("\n");
        }
        return albumsString.toString();
        //return artists.stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public String getAllArtistsByNickname() {
        StringBuilder albumsString= new StringBuilder();
        artists.stream()
                .forEach(ar -> {
                    albumsString.append("Nickname: " + ar.getNickname() + ", Full Name: " +  ar.getFirst_name() +", " + ar.getLast_name() + "\n");
                });


        return albumsString.toString();
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
        String[] nicknames = {"Ronaldo", "Tiger_Woods", "Rocky", "Iron_Man"};
        String[] first_names = {"Ronaldo Luis", "Eldrick", "Rocco", "Anthony Edward"};
        String[] last_names = {"Nazario de Lima", "Tont Woods", "Francis Marchegiano", "Stark"};
        String[] bios = {"Brazilian soccer player", "American professional golfer", "American professional boxer",
                "fictional super hero"};

        for (int i = 0; i < nicknames.length; i++) {
            createArtist(nicknames[i], first_names[i], last_names[i], bios[i]);
        }
    }
}