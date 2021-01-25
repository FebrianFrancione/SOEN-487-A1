package com.example.AlbumAndArtists;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/album")
public class AlbumRest {
    private static ArrayList<Album> albums = new ArrayList<>();

    //getting album by ISRC and title
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test")
    public String getAlbum() {
        return albums.toString();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test1")
    public String test() {
        return "hello";
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{ISRC}/{title}")
    public String getAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title) {
        Album album = albums.stream().filter(album1 -> album1.getTitle().equals(title) && album1.getISRC().equals(ISRC)).findFirst().orElse(null);
        if (album != null) {
            return album.toString();
        }else{
            return "Album not found!";
        }
    }

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public List<Album> findAlbum(@QueryParam("ISRC") String ISRC, @QueryParam("title") String title){
//        Album album = albums.stream().filter(album1 -> album1.getTitle().equals(title) && album1.getISRC().equals(ISRC)).findFirst().orElse(null);
//
//        if (album != null) {
//            return album.toString();
//        }else {
//            return "Album not found!";
//
//        }    }



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

