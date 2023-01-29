import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javafx.concurrent.Task;

public class UtilsHTTP {

    public static void sendGET(String GET_URL, Consumer<String> callBack) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Task<String> task = new Task<>() {
            @Override 
            protected String call() {
                try {
                    URL obj = new URL(GET_URL);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
                    int responseCode = con.getResponseCode();
    
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
    
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
    
                        callBack.accept(response.toString());
                    } else {
                        System.out.println("GET request did not work.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "{ \"result\": \"error\" }";
            }
        };
        task.setOnSucceeded(event -> {
            callBack.accept(task.getValue());
            executorService.shutdownNow();
        });
        task.setOnCancelled(event -> {
            callBack.accept("{ \"result\": \"error\" }");
            executorService.shutdownNow();
        });
        task.setOnFailed(event -> {
            callBack.accept("{ \"result\": \"error\" }");
            executorService.shutdownNow();
        });
        executorService.execute(task);
	}

    public static void sendPOST(String POST_URL, String POST_PARAMS, Consumer<String> callBack) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Task<String> task = new Task<>() {
            @Override 
            protected String call() {
                try {
                    URL obj = new URL(POST_URL);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
            
                    // For POST only - START
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    os.write(POST_PARAMS.getBytes());
                    os.flush();
                    os.close();
                    // For POST only - END
            
                    int responseCode = con.getResponseCode();
                    // System.out.println("POST Response Code :: " + responseCode);
            
                    if (responseCode == HttpURLConnection.HTTP_OK) { //success
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
            
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        return response.toString();
                    } else {
                        System.out.println("POST request did not work.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "{ \"result\": \"error\" }";
            }
        };
        task.setOnSucceeded(event -> {
            callBack.accept(task.getValue());
            executorService.shutdownNow();
        });
        task.setOnCancelled(event -> {
            callBack.accept("{ \"result\": \"error\" }");
            executorService.shutdownNow();
        });
        task.setOnFailed(event -> {
            callBack.accept("{ \"result\": \"error\" }");
            executorService.shutdownNow();
        });
        executorService.execute(task);       
	}
}
