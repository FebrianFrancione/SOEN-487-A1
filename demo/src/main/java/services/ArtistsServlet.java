package services;

import implementation.ArtistsManager;
import core.Artist;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

@WebServlet(name = "ArtistsServlet", urlPatterns = {"/artists"})
public class ArtistsServlet extends HttpServlet {
    private static final long serialVersionUID = 4L;
    private ArtistsManager artistsManager;
    private String message;

    public void init(){
        artistsManager = new ArtistsManager();
        artistsManager.setServletContext(this.getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickname = request.getParameter("nickname");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String biography = request.getParameter("biography");

        //Still need to set the HTTP response status and code
        if(nickname == null || first_name == null || last_name == null || biography == null)
            message = "Error " + HttpServletResponse.SC_BAD_REQUEST + " all fields are mandatory\n";

        //Still need to set the HTTP response status and code
        Artist newArtist = artistsManager.createArtist(nickname, first_name, last_name, biography);
        message = (newArtist != null) ? ("Artist created : \n" + newArtist + "\n")
                : HttpServletResponse.SC_BAD_REQUEST + " This nickname already exists, please use a unique nickname\n";
        sendResponse(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickname = request.getParameter("nickname");

        //Need to handle errors and exceptions
        if(nickname == null || nickname.isEmpty()) {
            ArrayList<Artist> artists = artistsManager.getList();
            message = artists.isEmpty() ? "There are no artists to display\n"
                    : ("Artists:\n" + artistsManager.getAllArtists() + "\n");
        }
        else{
            Artist artist = artistsManager.getArtist(nickname);
            message = (artist != null) ? artist + "\n" : " Artist " + nickname + " not found\n";
        }
        sendResponse(response);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String nickname = null;
        String first_name = null;
        String last_name = null;
        String bio = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();
        String[] args = data.split("#");
        nickname = args[0].split("=")[1];
        first_name = args[1].split("=")[1];
        last_name = args[2].split("=")[1];
        bio = args[3].split("=")[1];

        if(nickname == null || first_name == null || last_name == null || bio == null)
            message = "Error " + HttpServletResponse.SC_BAD_REQUEST + " all fields are mandatory\n";

        if(artistsManager.updateArtist(nickname, first_name, last_name, bio))
            message = "Artist updated :\n" + artistsManager.getArtist(nickname) + "\n";
        else
            message = "Artist " + nickname + " could not be updated\n";

        sendResponse(response);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String nickname = request.getParameter("nickname");

        if(nickname == null || nickname.isEmpty())
            message = "Error " + HttpServletResponse.SC_BAD_REQUEST + " nickname field is missing\n";

        if(artistsManager.deleteArtist(nickname))
            message = "Artist " + nickname + " successfully deleted!\n";
        else
            message = "Artist " + nickname + " was not found\n";

        sendResponse(response);
    }

    private void sendResponse(HttpServletResponse response){
        try{
            OutputStream out = response.getOutputStream();
            out.write(message.getBytes());
            out.flush();
        }
        catch (Exception e){
            System.out.println("Error sending response");
        }
    }
}
