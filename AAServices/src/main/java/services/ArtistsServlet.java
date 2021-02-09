package services;

import implementation.ArtistsManager;
import core.Artist;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;

@WebServlet(name = "ArtistsServlet", urlPatterns = {"/artists"})
public class ArtistsServlet extends HttpServlet {

    private static final long serialVersionUID = 4L;
    private ArtistsManager artistsManager;
    private String message;
    private int status = HttpServletResponse.SC_OK; //By default 200 OK

    public void init(){
        artistsManager = new ArtistsManager();
        artistsManager.setServletContext(this.getServletContext());
    }

    public static final String BASE_URI = "http://localhost:8080/core/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */

    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example.rest package
        final ResourceConfig rc = new ResourceConfig().packages("services");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickname = (request.getParameter("nickname") == null || request.getParameter("nickname").isEmpty())
                ? null : request.getParameter("nickname");
        String first_name = (request.getParameter("first_name") == null) ? null : request.getParameter("first_name");
        String last_name = (request.getParameter("last_name") == null) ? null : request.getParameter("last_name");
        String biography = (request.getParameter("biography") == null) ? null : request.getParameter("biography");

        //Still need to set the HTTP response status and code
        if(nickname == null || first_name == null || last_name == null || biography == null){
            message = "Error!: all fields are mandatory!doPost\n";
            status = HttpServletResponse.SC_BAD_REQUEST;
//            sendResponse(response, status);

            //
//            response.setStatus(status);
//            PrintWriter out = response.getWriter();
//            out.println("message");
//            out.flush();
//            out.close();
        }
        else{
            Artist newArtist = artistsManager.createArtist(nickname, first_name, last_name, biography);

            if(newArtist != null){
                message = "Artist created : \n" + newArtist + "\n TESTING";
                status = HttpServletResponse.SC_CREATED;
            }
            else{
                message = nickname + " already exists, please use a unique nickname\n";
                status = HttpServletResponse.SC_BAD_REQUEST;
            }
        }

        sendResponse(response);

    }

//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String nickname = request.getParameter("nickname");
//
//        if(nickname == null || nickname.isEmpty()) {
//            CopyOnWriteArrayList<Artist> artists = artistsManager.getList();
//            message = artists.isEmpty() ? "There are no artists to display\n"
//                    : ("Artists testing:\n"  + "\n");
//            status = HttpServletResponse.SC_OK;
//        }
//
//        else{
//            Artist artist = artistsManager.getArtist(nickname);
//            if(artist != null){
//                message = artist + "\n";
//                status = HttpServletResponse.SC_OK;
//            }
//            else{
//                message = " Artist " + nickname + " not found\n";
//                status = HttpServletResponse.SC_NOT_FOUND;
//            }
//        }
//
//        sendResponse(response);
//    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String all

        try{

            String message = artistsManager.getAllArtists();
//            response.setStatus(status);
//            response.setStatus();
            OutputStream out = response.getOutputStream();
            out.write(message.getBytes());
            out.flush();
        }
        catch (Exception e){
            System.out.println("Error sending response");
        }

//
//        String nickname = request.getParameter("nickname");
//
//        if(nickname == null || nickname.isEmpty()) {
//            CopyOnWriteArrayList<Artist> artists = artistsManager.getList();
//            message = artists.isEmpty() ? "There are no artists to display\n"
//                    : ("Artists testing:\n"  + "\n");
//            status = HttpServletResponse.SC_OK;
//        }
//
//        else{
//            Artist artist = artistsManager.getArtist(nickname);
//            if(artist != null){
//                message = artist + "\n";
//                status = HttpServletResponse.SC_OK;
//            }
//            else{
//                message = " Artist " + nickname + " not found\n";
//                status = HttpServletResponse.SC_NOT_FOUND;
//            }
//        }
//
//        sendResponse(response);
//        sendResponse(response);
    }



    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ArrayList<String> fields = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();
        String[] args = data.split("#");

        if(args.length != 4){
            message = "Error: all fields are mandatory!1\n";
            status = HttpServletResponse.SC_BAD_REQUEST;
            sendResponse(response);
            return;
        }

        else{
            for (String arg : args) {

                if(!validateValues(arg)){
                    sendResponse(response);
                    return;
                }

                fields.add(arg.split("=")[1]);
            }
        }

        if (artistsManager.updateArtist(fields.get(0), fields.get(1), fields.get(2), fields.get(3))) {
            message = "Artist updated :\n" + artistsManager.getArtist(fields.get(0)) + "\n";
            status = HttpServletResponse.SC_OK;
        } else {
            message = " Artist " + fields.get(0) + " not found\n";
            status = HttpServletResponse.SC_NOT_FOUND;
        }

        sendResponse(response);

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String nickname = request.getParameter("nickname");

        if(nickname == null || nickname.isEmpty()){
            message = "Error: nickname field is missing\n";
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        else{
            if(artistsManager.deleteArtist(nickname)){
                message = "Artist " + nickname + " successfully deleted!\n";
                status = HttpServletResponse.SC_OK;
            }

            else{
                message = "Artist " + nickname + " was not found\n";
                status = HttpServletResponse.SC_NOT_FOUND;
            }
        }

        sendResponse(response);
    }

    private boolean validateValues(String arg){

        String[] keyValues = arg.split("=");

        if(keyValues.length != 2){
            message = "Error: all fields are mandatory!validateValues\n";
            status = HttpServletResponse.SC_BAD_REQUEST;
            return false;
        }

        String value = keyValues[1];

        if (value == null || value.isEmpty()) {
            message = "Error: all fields are mandatory!validateValues2\n";
            status = HttpServletResponse.SC_BAD_REQUEST;
            return false;
        }

        return true;
    }

    private void sendResponse(HttpServletResponse response){
        try{
            response.setStatus(status);
            OutputStream out = response.getOutputStream();
            out.write(message.getBytes());
            out.flush();
        }
        catch (Exception e){
            System.out.println("Error sending response");
        }
    }
//
//    private void sendResponse(HttpServletResponse response, int status){
//        try{
//            response.setStatus(status);
//            OutputStream out = response.getOutputStream();
//            out.write(message.getBytes());
//            out.write(Integer.parseInt("status"));
//            out.write(status);
//            out.flush();
//        }
//        catch (Exception e){
//            System.out.println("Error sending response");
//        }
//    }
}
//curl -v -d "nickname=&first_name=feb&last_name=francione&biography=he is a complete person" http://localhost:8980/demo_1_0_SNAPSHOT_war/artists
//get all artists by nickname curl -v http://localhost:8980/demo_war/artists