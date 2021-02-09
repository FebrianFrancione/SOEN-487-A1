package client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpClientConnectionOperator;
import org.apache.http.util.EntityUtils;
import services.ArtistsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class ArtistsClient {

    public static ArtistsServlet artistServlet;
//    HttpResponse response = response;


    //from the get method, shows all the artists
//    public void showAll(){
//        try(CloseableHttpClient client = HttpClients.createDefault()){
//            System.out.println("Show all artists here");
//            HttpGet request = new HttpGet("http://localhost:8980/demo_war/artists");
//            ResponseHandler<String> responseHandler = readResponse();
//            String result = client.execute(request, responseHandler);
//            System.out.println("got result: printing: ");
//            System.out.println(result);
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }

//    HttpClient httpclient = new DefaultHttpClient();
//    HttpPost request = new HttpPost("/artists");
//    HttpResponse response = httpclient.execute(request);


    public void showAll() {
//        try {
//            HttpServletRequest request = HttpServletRequest(P);
//            HttpServletResponse response = null;
//
//
//            System.out.println("Show all artists here");
//                artistServlet.doGet(request, response);
////            String result = artistServlet.doGet(request, response);
//            System.out.println();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
///
        try {
            String url = "http://localhost/8980/demo_war/artists";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
//            System.out.println(areadResponse(con));
//            HttpGet httpget = new HttpGet("http://localhost:8980/demo_war/artists");
//            System.out.println("Executing request " + httpget.getRequestLine());
//            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
//
//                @Override
//                public String handleResponse(
//                        final HttpResponse response) throws ClientProtocolException, IOException {
//                    int status = response.getStatusLine().getStatusCode();
//                    if (status >= 200 && status < 300) {
//                        HttpEntity entity = response.getEntity();
//                        return entity != null ? EntityUtils.toString(entity) : null;
//                    } else {
//                        throw new ClientProtocolException("Unexpected response status: " + status);
//                    }
//                }
//
//            };
//            String responseBody = httpclient.execute(httpget, responseHandler);
//            System.out.println("----------------------------------------");
//            System.out.println(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





//
//            HttpGet getRequest = new HttpGet("http://localhost:8980/demo_war/artists");
//            URL url = new URL("http://localhost:8980/demo_war/artists");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("GET");
//
//            artistServlet.doGet();


//
//    public void getArtist(String nickname){
//        try(CloseableHttpClient client = HttpClients.createDefault()){
//            HttpGet request = new HttpGet("http://localhost:8980/demo_war/artists?nickname=" + nickname);
//            ResponseHandler<String> responseHandler = readResponse();
//            String result = client.execute(request, responseHandler);
//            System.out.println();
//            System.out.println(result);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void sendArtist(String nickname, String first_name, String last_name, String biography){
//        try(CloseableHttpClient client = HttpClients.createDefault()){
//            HttpPost request = new HttpPost("http://localhost:8980/demo_war/artists?nickname=" + nickname + "&first_name=" + first_name + "&last_name=" + last_name + "&biography=" + biography);
//            ResponseHandler<String> responseHandler = readResponse();
//            String result = client.execute(request, responseHandler);
//            System.out.println();
//            System.out.println("\n\nresult: " + result);
//            System.out.println("Successfully posted artist");
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void updateArtist(String nickname, String first_name, String last_name, String biography){
//        try(CloseableHttpClient client = HttpClients.createDefault()){
//            HttpPut request = new HttpPut("http://localhost:8980/demo_war/artists");
//            request.setEntity(new StringEntity("nickname=" + nickname + "#first_name=" + first_name + "#last_name=" + last_name + "#biography=" + biography));
//            ResponseHandler<String> responseHandler = readResponse();
//            String result = client.execute(request, responseHandler);
//            System.out.println();
//            System.out.println(result);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteArtist(String nickname){
//        try(CloseableHttpClient client = HttpClients.createDefault()){
//            HttpDelete request = new HttpDelete("http://localhost:8980/demo_war/artists?nickname=" + nickname);
//            ResponseHandler<String> responseHandler = readResponse();
//            String result = client.execute(request, responseHandler);
//            System.out.println();
//            System.out.println(result);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    private static ResponseHandler readResponse() {
//        ResponseHandler<String> responseHandler = response -> {
//            int status = response.getStatusLine().getStatusCode();
//            HttpEntity entity = response.getEntity();
//            return entity != null ? EntityUtils.toString(entity) : null;
//        };
//
//        return responseHandler;
//    }
}
