package client;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AlbumClient {

    public void showAll(){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet request = new HttpGet("http://localhost:8080/core/album/list");
            ResponseHandler<String> responseHandler = readResponse();
            String result = client.execute(request, responseHandler);
            System.out.println(result);
            System.out.println();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getAlbums(String ISRC, String title){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet request = new HttpGet(String.format("http://localhost:8080/core/album/%s/%s", ISRC, title));
            ResponseHandler<String> responseHandler = readResponse();
            String result = client.execute(request, responseHandler);
            System.out.println(result);
            System.out.println();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addAlbum(String ISRC, String title, String description, String artist, int year){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost request = new HttpPost(String.format("http://localhost:8080/core/album/create/%s/%s/%s/%d/%s", ISRC, title, description, year, artist));
            ResponseHandler<String> responseHandler = readResponse();
            String result = client.execute(request, responseHandler);
            System.out.println(result);
            System.out.println();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void updateAlbum(String ISRC, String title, String description, String artist, int year){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPut request = new HttpPut(String.format("http://localhost:8080/core/album/%s/%s/%s/%d/%s", ISRC, title, description, year, artist));
            ResponseHandler<String> responseHandler = readResponse();
            String result = client.execute(request, responseHandler);
            System.out.println(result);
            System.out.println();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void deleteAlbum(String ISRC){
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpDelete request = new HttpDelete(String.format("http://localhost:8080/core/album/%s", ISRC));
            ResponseHandler<String> responseHandler = readResponse();
            String result = client.execute(request, responseHandler);
            System.out.println(result);
            System.out.println();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static ResponseHandler readResponse() {
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };

        return responseHandler;
    }
}
