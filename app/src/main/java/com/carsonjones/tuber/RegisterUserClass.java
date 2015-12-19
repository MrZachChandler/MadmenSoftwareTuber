package com.carsonjones.tuber;

/**
 * Created by macuser on 12/17/15.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RegisterUserClass {
    // Sends the post request to the register.php file which looks through the database to check
    // if the entered values are already in the User table of the madmen7_tuber database and then returns
    // a response read from the register.php file in string form.
    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {


        URL url;
        String response = "";
        try {
            // Creates a new URL
            url = new URL(requestURL);

            // Creates the connection which is in the form of sending a URL to the internet
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Sets the maximum time to wait for an input stream read to complete before giving up.
            conn.setReadTimeout(15000);

            // Sets the maximum time in milliseconds to wait while connecting.
            // Connecting to a server will fail with a SocketTimeoutException
            conn.setConnectTimeout(15000);

            // Tells the server that it is sending a HTTP POST method.
            conn.setRequestMethod("POST");

            //  Sets the flag indicating whether this URLConnection allows input.
            // It cannot be set after the connection is established.
            conn.setDoInput(true);

            // Sets the flag indicating whether this URLConnection allows output.
            // It cannot be set after the connection is established.
            conn.setDoOutput(true);


            // OutputStream - A writable sink for bytes.
            // getOutputStream() - Returns an output stream that is connected to the standard
            // input stream of the native process represented by our HttpURLConnection object.
            // So this is used to edit what we send to the server.
            OutputStream os = conn.getOutputStream();

            // A writer is a means of writing data to a target in a character-wise manner.
            // With this writer we can write on what we send to the server
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            // This writer writes the POST data string to the
            // connection which uses the getPostDataString() connection
            writer.write(getPostDataString(postDataParams));

            // Most output streams expect the flush() method to be called before closing the stream,
            // to ensure all data is actually written out.
            writer.flush();
            writer.close();

            // Close the output stream
            os.close();

            // Returns the response code returned by the remote HTTP server.
            int responseCode=conn.getResponseCode();

            // if the server says that works
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                // getInputStream() - Returns an InputStream for reading data from the resource
                // pointed by this URLConnection. It throws an UnknownServiceException by default.
                // This creates a reader that can read what the register.php file says back to us
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                // Reads the first line in the response from the register.php file
                response = br.readLine();
            }
            else {
                response="Error Registering";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    // Creates the string needed to send the post request to the server
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) {
                first = false;
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

            }
            else {
                result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }

        return result.toString();
    }
}