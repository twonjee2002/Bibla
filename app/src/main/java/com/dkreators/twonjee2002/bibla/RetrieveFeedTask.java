package com.dkreators.twonjee2002.bibla;


import android.os.AsyncTask;
import android.view.View;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static android.R.attr.data;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.in;

class RetrieveFeedTask extends AsyncTask<String, Void, String> {
    private static final String DEBUG_TAG = "TKT";
    TextToSpeech t1,ttsi,ttsm;
    private Exception exception;
    private int data2;
    private BufferedReader reader;

    //  private  progressBar;

    protected void onPreExecute() {
       // progressBar.setVisibility(View.VISIBLE);
       // responseView.setText("");
    }

    protected String doInBackground(String... urls) {
        // toBeRead = bookNameField.getSelectedItem().toString() + " " + fromChapterNumber.getSelectedItem().toString() + ":" + fromVerse.getSelectedItem().toString()+'-'+ toVerse.getSelectedItem().toString();
        Log.d(DEBUG_TAG, "OUTCOME IS " + urls[0]);
        // Do some validation here
        URL url = null;
        try {
            url = new URL("https://bible-api.com/" + urls[0]);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } //vuteurl is a string containing a proper URL


        HttpURLConnection urlConnection;
        urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.d(DEBUG_TAG, "TRIED " + urlConnection);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        try {
            Log.d(DEBUG_TAG, "INPUTSTREAM 1 " + urlConnection);
            InputStream in = null;
           // in = urlConnection.getInputStream();
            in = urlConnection.getInputStream();

            Log.d(DEBUG_TAG, "INPUTSTREAM 2 " + in);
            StringBuffer buffer = new StringBuffer();
            if (in == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
                Log.d(DEBUG_TAG, "INPUTSTREAM LINE2 " + line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(DEBUG_TAG, "INPUTSTREAM ERROR "+ e.toString());
        }

        try {

            InputStreamReader isw = new InputStreamReader(in);
            Log.d(DEBUG_TAG, "INPUTSTREAM " + urlConnection);
            try {
                data2 = isw.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            Log.d(DEBUG_TAG, "BUFFERED " + urlConnection);
            while (data2 != -1) {
                //    line = (char) data;
                stringBuilder.append(data2);
                Log.d(DEBUG_TAG, "LINES " + line);
            }
            //  bufferedReader.close();
            Log.d(DEBUG_TAG, "RETURNED  " + stringBuilder.toString());
            return stringBuilder.toString();
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
   //     progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
        ttsm.setLanguage (Locale.UK);
        //   ttsm.setLanguage(Locale.getDefault());
        //   ttsm.setPitch(1.3f);
        //   ttsm.setSpeechRate(1f);
        ttsm.speak("tobeEEEEEEEEEE", TextToSpeech.QUEUE_FLUSH, null);
    }
}