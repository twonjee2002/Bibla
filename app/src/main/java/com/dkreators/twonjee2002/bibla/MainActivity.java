package com.dkreators.twonjee2002.bibla;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.NetworkOnMainThreadException;
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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
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

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

import static android.R.attr.data;
import static java.lang.Runtime.getRuntime;

public class MainActivity extends AppCompatActivity  {

    //map for bible books and verses
    // public final static String GENESIS = "GENESIS";
    public static String ANDROID_DEVICE_ID = "";

    private static final Map<String, String[]> bibleMap = Collections.unmodifiableMap(
            //http://catholic-resources.org/Bible/OT-Statistics-NAB.htm
            new HashMap<String, String[]>() {{
                put("GENESIS", new String[]{"50","31","25","24","26","32","22","24","22","29","32","32","20","18","24","21","16","27","33","38","18","34","24","20","67","34","35","46","22","35","43","54","33","20","31","29","43","36","30","23","23","57","38","34","34","28","34","31","22","33","26"});
                put("EXODUS", new String[]{"40","22","25","22","31","23","30","29","28","35","29","10","51","22","31","27","36","16","27","25","26","37","30","33","18","40","37","21","43","46","38","18","35","23","35","35","38","29","31","43","38"});
                put("LEVITICUS", new String[]{"27","17","16","17","35","26","23","38","36","24","20","47","8","59","57","33","34","16","30","37","27","24","33","44","23","55","46","34"});
                put("NUMBERS", new String[]{"36","54","34","51","49","31","27","89","26","23","36","35","16","33","45","41","35","28","32","22","29","35","41","30","25","19","65","23","31","39","17","54","42","56","29","34","13"});
                put("DEUTERONOMY", new String[]{"34","46","37","29","49","33","25","26","20","29","22","32","31","19","29","23","22","20","22","21","20","23","29","26","22","19","19","26","69","28","20","30","52","29","12"});
                put("JOSHUA", new String[]{"24","18","24","17","24","15","27","26","35","27","43","23","24","33","15","63","10","18","28","51","9","45","34","16","33"});
                put("JUDGES", new String[]{"21","36","23","31","24","31","40","25","35","57","18","40","15","25","20","20","31","13","31","30","48","25"});
                put("RUTH", new String[]{"4","22","23","18","22"});
                put("1SAMUEL", new String[]{"31","28","36","21","22","12","21","17","22","27","27","15","25","23","52","35","23","58","30","24","42","16","23","28","23","43","25","12","25","11","31","13"});
                put("2SAMUEL", new String[]{"24","27","32","39","12","25","23","29","18","13","19","27","31","39","33","37","23","29","32","44","26","22","51","39","25"});
                put("1KINGS", new String[]{"22","53","46","28","20","32","38","51","66","28","29","43","33","34","31","34","34","24","46","21","43","29","54"});
                put("2KINGS", new String[]{"25","18","25","27","44","27","33","20","29","37","36","20","22","25","29","38","20","41","37","37","21","26","20","37","20","30"});
                put("1CHRONICLES", new String[]{"29","54","55","24","43","41","66","40","40","44","14","47","41","14","17","29","43","27","17","19","8","30","19","32","31","31","32","34","21","30"});
                put("2CHRONICLES", new String[]{"36","18","17","17","22","14","42","22","18","31","19","23","16","23","14","19","14","19","34","11","37","20","12","21","27","28","23","9","27","36","27","21","33","25","33","26","23"});
                put("EZRA", new String[]{"10","11","70","13","24","17","22","28","36","15","44"});
                put("NEHEMIAH", new String[]{"13","11","20","38","17","19","19","72","18","37","40","36","47","31"});
                put("ESTHER", new String[]{"10","22","23","15","17","14","14","10","17","32","3","17","8","30","16","24","10"});
                put("JOB", new String[]{"42","22","13","26","21","27","30","21","22","35","22","20","25","28","22","35","22","16","21","29","29","34","30","17","25","6","14","21","28","25","31","40","22","33","37","16","33","24","41","30","32","26","17"});
                put("PSALMS", new String[]{"150","6","11","9","9","13","11","18","10","20","18","7","9","6","7","5","11","15","51","15","10","14","32","6","10","22","11","14","9","11","13","25","11","22","23","28","13","40","23","14","18","14","12","5","27","18","12","10","15","21","23","21","11","7","9","24","14","12","12","18","14","9","13","12","11","14","20","8","36","37","6","24","20","28","23","11","13","21","72","13","20","17","8","19","13","14","17","7","19","53","17","16","16","5","23","11","13","12","9","9","5","8","29","22","35","45","48","43","14","31","7","10","10","9","8","18","19","2","29","176","7","8","9","4","8","5","6","5","6","8","8","3","18","3","3","21","26","9","8","24","14","10","8","12","15","21","10","20","14","9","6"});
                put("PROVERBS", new String[]{"31","33","22","35","27","23","35","27","36","18","32","31","28","25","35","33","33","28","24","29","30","31","29","35","34","28","28","27","28","27","33","31"});
                put("ECCLESIASTES", new String[]{"12","18","26","22","17","19","12","29","17","18","20","10","14"});
                put("SONGSOFSOLOMON", new String[]{"8","16","24","19","20","23","25","30","21","18","21","26","27","19","31","19","29","21","25","22"});
                put("ISAIAH", new String[]{"66","31","22","26","6","30","13","25","23","20","34","16","6","22","32","9","14","14","7","25","6","17","25","18","23","12","21","13","29","24","33","9","20","24","17","10","22","38","22","8","31","29","25","28","28","25","13","15","22","26","11","23","15","12","17","13","12","21","14","21","22","11","12","19","11","25","24"});
                put("JEREMIAH", new String[]{"52","19","37","25","31","31","30","34","23","25","25","23","17","27","22","21","21","27","23","15","18","14","30","40","10","38","24","22","17","32","24","40","44","26","22","19","32","21","28","18","16","18","22","13","30","5","28","7","47","39","46","64","34"});
                put("LAMENTATION", new String[]{"5","22","22","66","22","22"});
                put("EZEKIEL", new String[]{"48","28","10","27","17","17","14","27","18","11","22","25","28","23","23","8","63","24","32","14","44","37","31","49","27","17","21","36","26","21","26","18","32","33","31","15","38","28","23","29","49","26","20","27","31","25","24","23","35"});
                put("DANIEL", new String[]{"12","21","49","100","34","30","29","28","27","27","21","45","13","64","42"});
                put("HOSEA", new String[]{"14","9","25","5","19","15","11","16","14","17","15","11","15","15","10"});
                put("JOEL", new String[]{"3","20","27","5","21"});
                put("AMOS", new String[]{"9","15","16","15","13","27","14","17","14","15"});
                put("OBADIAH", new String[]{"1", "21"});
                put("JONAH", new String[]{"4","16","11","10","11"});
                put("MICAH", new String[]{"7", "16","13","12","14","14","16","20"});
                put("NAHUM", new String[]{"3","14","14","19"});
                put("HABAKUK", new String[]{"3","17","20","19"});
                put("ZEPHANIAH", new String[]{"3","18","15","20"});
                put("HAGAI", new String[]{"2","15","23"});
                put("ZECHARIAH", new String[]{"14","17","17","10","14","11","15","14","23","17","12","17","14","9","21"});
                put("MALACHI", new String[]{"3", "14","17","24"});
                put("MATHEW", new String[]{"28", "25", "23", "17", "25", "48", "34", "29", "34", "38", "42", "30", "50", "58", "36", "39", "28", "27", "35", "30", "34", "46", "46", "39", "51", "46", "75", "66", "20"});
                put("MARK", new String[]{"16", "45", "28", "35", "41", "43", "56", "37", "38", "50", "52", "33", "44", "37", "72", "47", "20"});
                put("LUKE", new String[]{"24", "80", "52", "38", "44", "39", "49", "50", "56", "62", "42", "54", "59", "35", "35", "32", "31", "37", "43", "48", "47", "38", "71", "56", "53"});
                put("JOHN", new String[]{"21", "51", "25", "36", "54", "47", "71", "53", "59", "41", "42", "57", "50", "38", "31", "27", "33", "26", "40", "42", "31", "25"});
                put("ACTS", new String[]{"28", "26", "47", "26", "37", "42", "15", "60", "40", "43", "48", "30", "25", "52", "28", "41", "40", "34", "28", "40", "38", "40", "30", "35", "27", "27", "32", "44", "31"});
                put("ROMANS", new String[]{"16", "32", "29", "31", "25", "21", "23", "25", "39", "33", "21", "36", "21", "14", "23", "33", "27"});
                put("1CORINTHIANS", new String[]{"16", "31", "16", "23", "21", "13", "20", "40", "13", "27", "33", "34", "31", "13", "40", "58", "24"});
                put("2CORINTHIANS", new String[]{"13", "24", "17", "18", "18", "21", "18", "16", "24", "15", "18", "33", "21", "13"});
                put("GALATIANS", new String[]{"6", "24", "21", "29", "31", "26", "18"});
                put("EPHESIANS", new String[]{"6", "23", "22", "21", "32", "33", "24"});
                put("PHILIPIANS", new String[]{"4", "30", "30", "21", "23"});
                put("COLLOSIANS", new String[]{"4", "29", "23", "25", "18"});
                put("1THESALONIANS", new String[]{"5", "10", "20", "13", "18", "28"});
                put("2THESALONIANS", new String[]{"30", "12", "17", "18"});
                put("1TIMOTHY", new String[]{"30", "20", "15", "16", "16", "25", "21"});
                put("2TIMOTHY", new String[]{"30", "18", "26", "17", "22"});
                put("TITUS", new String[]{"30", "16", "15", "15"});
                put("PHILEMON", new String[]{"30", "25"});
                put("HEBREW", new String[]{"2", "14", "18", "19", "16", "14", "20", "28", "13", "28", "39", "40", "29", "25"});
                put("JAMES", new String[]{"30", "27", "26", "18", "17", "20"});
                put("1PETER", new String[]{"30", "25", "25", "22", "19", "14"});
                put("2PETER", new String[]{"30", "21", "22", "18"});
                put("1JOHN", new String[]{"30", "10", "29", "24", "21", "21"});
                put("2JOHN", new String[]{"30", "13"});
                put("3JOHN", new String[]{"30", "15"});
                put("JUDE", new String[]{"30", "25"});
                put("REVELATION", new String[]{"30", "20", "29", "22", "11", "14", "17", "17", "13", "21", "11", "19", "17", "18", "20", "8", "21", "18", "24", "21", "15", "27", "21"});

            }});
    private static final String DEBUG_TAG = "TKT";
    String sp1 = "", tobe, test=null, toBeRead, tobee;
    Spinner toChapterNumber, fromChapterNumber, fromVerse, toVerse, bookNameField;
    char current;
    TextToSpeech t1,ttsi,ttsm;
    StringBuilder fulldata;
    HttpURLConnection urlConnection = null;
    private TextView textreadView;
    InputStream in;
    boolean flag = false;
    private String server_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CustomActivityOnCrash.install(this);

        if (android.os.Build.VERSION.SDK_INT > 4) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onFeedback(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.agricmania.com.ng/contact"));
        startActivity(intent);
    }

    public void onSetBibleClick(View view) {

        setContentView(R.layout.activity_verses);

        bookNameField = (Spinner) findViewById(R.id.spinner1);
        fromChapterNumber = (Spinner) findViewById(R.id.spinner2);
        fromVerse = (Spinner) findViewById(R.id.spinner3);
        fromVerse.getBackground().getConstantState().newDrawable();

     //   Drawable spinnerDrawable = spinnerDrawable.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
   //     if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
     //       fromVerse.setBackground(spinnerDrawable);
    //    }
     //   else{ fromVerse.setBackgroundDrawable(spinnerDrawable);
        toVerse = (Spinner) findViewById(R.id.spinner5);

        bookNameField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);
           //     Log.d(DEBUG_TAG, "Spinner1 dropdown " + bookNameField.getSelectedItem());
                sp1 = String.valueOf(bookNameField.getSelectedItem());

                List<String> items2 = new ArrayList<String>();
                String chapt = bibleMap.get(sp1)[0];
                Log.d(DEBUG_TAG, "chapter number is " + chapt);
                for (int i = 1; i <= Integer.parseInt(chapt); i++) {
                    items2.add(i + "");
                }
              //  Log.d(DEBUG_TAG, "items are " + items2.toString());

                toChapterNumber = (Spinner) findViewById(R.id.spinner4);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items2);
                fromChapterNumber.setAdapter(adapter);
                toChapterNumber.setAdapter(adapter);
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });

        /*Here we check the chapter number that is picked
        so we can populate the verse
        */
        fromChapterNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
           //     Log.d(DEBUG_TAG, "Read from chapter " + fromChapterNumber.getSelectedItem());
                String chapter = String.valueOf(fromChapterNumber.getSelectedItem());
                String index = bibleMap.get(sp1)[Integer.parseInt(chapter)];
                List<String> items3 = new ArrayList<String>();
                for (int j = 1; j <= Integer.parseInt(index); j++) {
                    items3.add("" + j);
                }
             //   Log.d(DEBUG_TAG, "items3 are " + items3.toString());
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items3);
                fromVerse.setAdapter(adapter3);
 //toVerse = null;
               toVerse.setAdapter(adapter3);
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });

/*Here we check the chapter number that is picked
        so we can populate the verse
        */
        toChapterNumber = (Spinner) findViewById(R.id.spinner4);
        toChapterNumber.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        toChapterNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                Log.d(DEBUG_TAG, "Read from chapter " + toChapterNumber.getSelectedItem());
                String sp2 = String.valueOf(fromVerse.getSelectedItem());
                String chapter2 = String.valueOf(toChapterNumber.getSelectedItem());
                String index = bibleMap.get(sp1)[Integer.parseInt(chapter2)];
                List<String> items4 = new ArrayList<String>();
                for (int l = 1; l <= Integer.parseInt(index); l++) {
                    items4.add("" + l);

                }
               // items4 = sp2;
                Log.d(DEBUG_TAG, "items4 are " + items4.toString());

                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items4);
                      //       toVerse.setAdapter(adapter3);
                toVerse = toVerse;
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
    }

    public void onSetBackClick(View view){

        ttsm.stop();
        setContentView(R.layout.activity_main);
    }



    TextToSpeech tts;
    public void onSetBookClick (View view) {
         tts = new TextToSpeech (this, new TextToSpeech.OnInitListener () {


            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //leftToRead = speakFull(res);
                    tts.setLanguage (Locale.UK);
                    tts.speak ("This feature is not yet available", TextToSpeech.QUEUE_FLUSH, null);

                }}
        });

        }



    public void onSetReadClick(View view) throws JSONException, ParseException{

        toBeRead = bookNameField.getSelectedItem().toString() + " " + fromChapterNumber.getSelectedItem().toString() + ":" + fromVerse.getSelectedItem().toString()+'-'+ toVerse.getSelectedItem().toString();

        if (Integer.parseInt(fromVerse.getSelectedItem().toString()) > Integer.parseInt(toVerse.getSelectedItem().toString())){
            ttsi = new TextToSpeech (this, new TextToSpeech.OnInitListener () {


                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        //leftToRead = speakFull(res);
                        ttsi.setLanguage (Locale.UK);
                        ttsi.speak ("Wrong input, you cannot read from verse "+Integer.parseInt(fromVerse.getSelectedItem().toString())+" to verse "+ Integer.parseInt(toVerse.getSelectedItem().toString())+"", TextToSpeech.QUEUE_FLUSH, null);

                    }}
            });
        }else{
         //+ "," + toChapterNumber.getSelectedItem().toString() + ":" + toVerse.getSelectedItem().toString();
    //    Log.e(DEBUG_TAG, "WHAT WAS ENTERED " + toBeRead);

         //
          //  Log.e(DEBUG_TAG, "CALLING ASYNC RUNNER " + toBeRead);

            final AsyncTaskRunner myTask = new AsyncTaskRunner();
            myTask.execute(toBeRead);
            ttsm = new TextToSpeech (this, new TextToSpeech.OnInitListener () {


                @Override
                public void onInit(int status2) {
                    if (status2 == TextToSpeech.SUCCESS) {
                     
                        //does not work at all.
                        ttsm.setLanguage(Locale.UK);
                        ttsm.setPitch(1.1f);
                        ttsm.setSpeechRate(0.8f);
                        ttsm.speak (myTask.tobet, TextToSpeech.QUEUE_FLUSH, null);

                    }
                    //this part works for emulator android 5...
                    ttsm.setLanguage(Locale.UK);
                    ttsm.setPitch(1.1f);
                    ttsm.setSpeechRate(0.8f);
                  //  ttsm.speak (myTask.tobet, TextToSpeech.QUEUE_FLUSH, null);
                }
            });



    }}

    public String doInBackgrounda(String params)   {


     //   URL url;

        int data = 0;

        try {

           // URL url = new URL("https://bible-api.com/"+params);
            String urll = "https://bible-api.com/"+params;
                  //  String urll = "https://bible-api.com/john 3:16";
            Log.d(DEBUG_TAG,"TOBEE after objectp "+params);
            URL url= new URL(urll);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            String urls = uri.toASCIIString();
            URL urlb = new URL(urls);

           urlConnection = (HttpURLConnection) urlb.openConnection();
          //  Toast.makeText(this, "connecting...", Toast.LENGTH_SHORT).show();
                 } catch (Exception e) {
        //    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        try{
                  in = new BufferedInputStream(urlConnection.getInputStream());

                    Reader isw = new InputStreamReader(in);
                     fulldata = new StringBuilder();

            String inputLine;
            int c;
            while ((c = isw.read()) != -1) {
             //   Log.d(DEBUG_TAG, "CALLING data... " + (char) c);
                fulldata.append((char) c);
             }
                          //      Log.d(DEBUG_TAG, "RETURNING FIRST FULL DATA " + fulldata.toString());
           // return fulldata.toString();
        } catch (RuntimeException ee) {

        //    Toast.makeText(this, ee.toString(), Toast.LENGTH_SHORT).show();
          //  throw new RuntimeException("Custom Message");
          //  Log.d(DEBUG_TAG, "failed to get inputstream ee "+ ee.toString() );
             ee.printStackTrace();
        } catch (IOException e) {
         //   Log.d(DEBUG_TAG, "failed to get inputstream put toast here "+ e.toString() );
         //  Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(DEBUG_TAG, "RETURNING SECOND FULL DATA " + fulldata.toString());
        return fulldata.toString();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;
        public String tobet;

        @Override
        protected String doInBackground(String... params) {

                android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
                android.os.StrictMode.setThreadPolicy(policy);
               tobe = doInBackgrounda(params[0]);

            try{
               JSONObject objectp = null;
                JSONParser jsonParser = new JSONParser();
                 objectp = new JSONObject(tobe);//
            //    Log.d(DEBUG_TAG,"TOBEE after objectp "+tobe);
                  tobet = objectp.getString("text");
//flag = false;
            } catch (JSONException e) {
                e.printStackTrace();
              //  Log.d(DEBUG_TAG,"THERE WAS AN ERROR "+tobet);
            }
          //  Log.d(DEBUG_TAG,"I HAVE DONE MY PART NOW "+tobet);

            return tobet;
        }


            @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
                progressDialog.dismiss();
           Log.d(DEBUG_TAG, "RESULT IS " + result);
            setContentView(R.layout.activity_reading);
            textreadView = (TextView) findViewById(R.id.readBook);
            textreadView.setText(result);
               //This part works for android 4...
                ttsm.setLanguage(Locale.UK);
                ttsm.setPitch(1.1f);
                ttsm.setSpeechRate(0.8f);
                ttsm.speak (result, TextToSpeech.QUEUE_FLUSH, null);

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Searching for "+toBeRead.toString()+ " ...");
        }

        @Override
        protected void onProgressUpdate(String... text) {
           progressDialog = ProgressDialog.show(MainActivity.this,
                   "ProgressDialog",
                  "About to read "+text+ " ...");


        }

    }


}



