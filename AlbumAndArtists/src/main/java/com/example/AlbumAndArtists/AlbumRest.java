package com.example.AlbumAndArtists;

import core.Album;
import org.glassfish.jersey.server.monitoring.ResponseMXBean;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/album")
public class AlbumRest {
    private static ArrayList<Album> albums = new ArrayList<>();
    public static ArrayList<Album> arrayTest = new ArrayList<>();
    String message = "";

    //getting album by ISRC and title
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/list")
    public Response getAlbum() {
//        arrayTest.add(new Album("ISRC","title", "description", 1, "artist"));
//        arrayTest.add(new Album("ISRC1","title1", "description1", 2, "artist1"));
//        arrayTest.add(new Album("ISRC2","title2", "description2", 3, "artist2"));

        if(!albums.isEmpty()){
            for(int i = 0; i < albums.size(); i++){
                message += " ISRC: " + albums.get(i).getISRC() + ", Title: " + albums.get(i).getTitle() + "\n";
            }
            return Response.ok(message).build();
        }else{
            message = "Error! No albums to return!";
//            return Response.status(Response.Status.OK).entity(message).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{ISRC}/{title}")
    public Response getAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title) {
        Album album = albums.stream().filter(album1 -> album1.getTitle().equals(title) && album1.getISRC().equals(ISRC)).findFirst().orElse(null);
        if (album != null) {
            return Response.status(Response.Status.OK).entity(album.toString()).build();
        }else{
            message = "Album not found!";
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create/{ISRC}/{title}/{description}/{year}/{artist}")
    public Response createAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title, @PathParam("description") String description, @PathParam("year") int year, @PathParam("artist") String artist){
        if(ISRC == null || title == null || year == 0 || artist == null){
            message = "A Form parameter is incorrect!";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build();
        }
        Album newAlbum  = new Album(ISRC, title, description, year, artist);
        albums.add(newAlbum);
        message = "Album created!\nISRC: " + ISRC + "\nTitle: " + title + "\nDescription: " + description + "\nYear: " + year + "\nArtist: " + artist;
        return Response.ok(message).build();
    }

    //delete uses ISRC
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("{ISRC}")
    public Response deleteAlbum(@PathParam("ISRC") String ISRC){
        albums = albums.stream().filter(album -> album.getTitle() == ISRC).collect(Collectors.toCollection(ArrayList::new));
        message = "Album: " + ISRC + " successfully deleted!";
        return Response.ok(message).build();
    }


    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("{ISRC}/{title}/{description}/{year}/{artist}")
    public Response modifyAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title, @PathParam("description") String description, @PathParam("year") int year, @PathParam("artist") String artist){
        if(ISRC == null || title == null || year == 0 || artist == null){
            message = "A Form parameter is incorrect!";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build();
        }else{
            deleteAlbum(ISRC);
            message = "Album: " + ISRC + " modified!.\nNew Album: " + ISRC + "\nTitle: " + title + "\nArtist: " + artist + "\nYear: " + year + "\nDescription: " + description;
//            createAlbum(ISRC, title, description, year, artist);
            Album newAlbum  = new Album(ISRC, title, description, year, artist);
            albums.add(newAlbum);
            return Response.ok(message).build();
        }

    }


}

