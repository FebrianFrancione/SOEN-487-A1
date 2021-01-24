package com.example.AlbumAndArtists;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Path("album")
public class AlbumRest {
    private static ArrayList<Album> albums = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{title}")
    public String getAlbum(@PathParam("title") String title) {
        Album album = albums.stream().filter(album1 -> album1.getTitle().equals(title)).findFirst().orElse(null);

        if (album != null) {
            return album.toString();
        }else{
            return "Album not found!";
        }
    }

    @POST
    @Path("{ISRC}/{title}/{description}/{year}/{artist}")
    public void createAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title, @PathParam("description") String description, @PathParam("year") int year, @PathParam("artist") String artist){
        Album newAlbum =new Album(ISRC, title, description, year, artist);
        albums.add(newAlbum);
    }

    @DELETE
    @Path("{title}")
    public void deleteAlbum(@PathParam("title") String title){
        albums= albums.stream().filter(album -> album.getTitle() != title).collect(Collectors.toCollection(ArrayList::new));
    }

    @PUT
    @Path("{ISRC}/{title}/{description}/{year}/{artist}")
    public void modifyAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title, @PathParam("description") String description, @PathParam("year") int year, @PathParam("artist") String artist){
        deleteAlbum(title);
        createAlbum(ISRC, title, description, year, artist);
    }


}

