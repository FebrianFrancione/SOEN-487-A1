package services;

import implementation.ArtistsManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ArtistsServlet", urlPatterns = {"/artists"})
public class ArtistsServlet extends HttpServlet {
    private static final long serialVersionUID = 4L;
    private ArtistsManager artists;

    public void init(){
        artists = new ArtistsManager();
        artists.setServletContext(this.getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
