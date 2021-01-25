package com.example.AlbumAndArtists;

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
    ArrayList<Album> arrayTest2 = new ArrayList<>();
    //getting album by ISRC and title
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/list")
    public Response getAlbum() {
        arrayTest.add(new Album("ISRC","title", "description", 1, "artist"));
        arrayTest.add(new Album("ISRC1","title1", "description1", 2, "artist1"));
        arrayTest.add(new Album("ISRC2","title2", "description2", 3, "artist2"));
//       Album album  = arrayTest.stream().findFirst().orElse(null);
        String message = "";
        for(int i = 0; i < arrayTest.size(); i++){
            message += " ISRC: " + arrayTest.get(i).getISRC() + ", Title: " + arrayTest.get(i).getTitle() + "\n";
        }
        return Response.status(Response.Status.OK).entity(message).build();
//        return arrayTest.size();
//       if(!arrayTest.isEmpty()){
//           arrayTest.stream().forEach(b-> System.out.println(b.getISRC() + "" + b.getTitle()));
//           return "got all";
//       }else{
//           return "Album not found!";
//       }
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


    @POST
    @Path("{ISRC}/{title}/{description}/{year}/{artist}")
    public void createAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title, @PathParam("description") String description, @PathParam("year") int year, @PathParam("artist") String artist){
        Album newAlbum =new Album(ISRC, title, description, year, artist);
        albums.add(newAlbum);
    }

    //deelte uses ISRC so that other variables cna be modified
    @DELETE
    @Path("{ISRC}")
    public void deleteAlbum(@PathParam("ISRC") String ISRC){
        albums= albums.stream().filter(album -> album.getTitle() != ISRC).collect(Collectors.toCollection(ArrayList::new));
    }

    @PUT
    @Path("{ISRC}/{title}/{description}/{year}/{artist}")
    public void modifyAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title, @PathParam("description") String description, @PathParam("year") int year, @PathParam("artist") String artist){
        deleteAlbum(ISRC);
        createAlbum(ISRC, title, description, year, artist);
    }


}

