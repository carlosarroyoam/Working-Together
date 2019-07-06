package com.workingtogether.android.util;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class firebaseConsoleWS extends AsyncTask<Void, Void, Boolean> {
    private static InputStream inputStream = null;
    private static JSONObject jsonObject = null;
    private static String JSON;

    private JSONObject paramsJSONArray;

    public firebaseConsoleWS(JSONObject paramsJSONArray) {
        this.paramsJSONArray = paramsJSONArray;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            StringEntity params = new StringEntity(paramsJSONArray.toString());
            String key = "key=AAAAQPnjBsA:APA91bGhxtRwfVzHhLc1qNN5t-SHS7g4jL9Z42_g3yytgCMagDWlTrzqyKzPWk1t5DdoImiuBqMVz67vtDq01e9SZbJ21Mb1IH6peESW-OxTEJ3Xpw9PC-JBtXbpz3UXwLZFG_nIiguw";
            String url = "https://fcm.googleapis.com/fcm/send";

            HttpClient httpCliente = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", key);
            httpPost.setEntity(params);

            HttpResponse httpResponse = httpCliente.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (ClientProtocolException cpe) {
            cpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1), 8);

            StringBuilder stringBuilder = new StringBuilder();
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                stringBuilder.append(linea).append("\n");
            }
            inputStream.close();
            JSON = stringBuilder.toString();

        } catch (Exception e) {
            Log.e("error de buffer", "Error ");
        }

        try {
            jsonObject = new JSONObject(JSON);
        } catch (JSONException je) {
            Log.e("JSONConverter", "Error al convertir a JSON" + je.toString());
        }

        return true;
    }
}
