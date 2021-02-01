package services;

import core.Album;
import implementation.AlbumsManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/album")
public class AlbumRest {
    //private static ArrayList<Album> albums = new ArrayList<>();
    String message = "";
    private static AlbumsManager albumsManager = new AlbumsManager();

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/list")
    public Response getAlbums() {

        if(albumsManager.hasAlbums())
            return Response.ok(albumsManager.getAllAlbums()).build();

        else{
            message = "Error! No albums to return!";
//            return Response.status(Response.Status.OK).entity(message).build();
            return Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{ISRC}/{title}")
    public Response getAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title) {
        Album album = albumsManager.getAlbum(ISRC,title);
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
        else{
            Album album = albumsManager.createAlbum(ISRC, title, description, year, artist);
            message = (album != null) ? "Album created!: \n" + album : " This ISRC already exists, please use a unique ISRC";
            return Response.ok(message).build();
        }

    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("{ISRC}")
    public Response deleteAlbum(@PathParam("ISRC") String ISRC){
        if(albumsManager.deleteAlbum(ISRC)){
            message = "Album " + ISRC + " successfully deleted!";
            return Response.ok(message).build();
        }

        else{
            message = "Album " + ISRC + " was not found";
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("{ISRC}/{title}/{description}/{year}/{artist}")
    public Response modifyAlbum(@PathParam("ISRC") String ISRC, @PathParam("title") String title, @PathParam("description") String description, @PathParam("year") int year, @PathParam("artist") String artist){
        if(ISRC == null || title == null || year == 0 || artist == null){
            message = "A Form parameter is incorrect!";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build();
        }
        else{
            if(albumsManager.updateAlbum(ISRC, title, description, year, artist)){
                message = "Album modified :\n" + albumsManager.getAlbum(ISRC);
                return Response.ok(message).build();
            }
            else{
                message = "Album " + ISRC + " could not be modified";
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }

        }

    }


}

