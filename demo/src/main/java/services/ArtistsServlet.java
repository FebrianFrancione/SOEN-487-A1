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
import java.io.IOException;
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
        if(nickname == null || first_name == null || last_name == null || biography == null){
            message = "Error " + HttpServletResponse.SC_BAD_REQUEST + " all fields are mandatory";
            sendResponse(response);
        }

        Artist newArtist = artistsManager.createArtist(nickname, first_name, last_name, biography);
        message = (newArtist != null) ? "Artist created!: \n" + newArtist : " This nickname already exists, please use a unique nickname";
        sendResponse(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Artist> artists = artistsManager.getList();
        message = artists.isEmpty() ? "There are no artists to display\n" : "Artists:\n" + artistsManager.getAllArtists() + "\n";
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
