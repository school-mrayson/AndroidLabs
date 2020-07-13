package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    protected ImageView currWeather;
    protected TextView currTemp;
    protected TextView minTemp;
    protected TextView maxTemp;
    protected TextView uvRating;
    protected ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        currWeather = findViewById(R.id.currWeatherImage);
        currTemp = findViewById(R.id.currTemp);
        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        uvRating = findViewById(R.id.uvRating);
        pb = findViewById(R.id.weatherProgressBar);

        pb.setVisibility(View.VISIBLE);
        ForecastQuery newFQ = new ForecastQuery();
        newFQ.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String[]>{
        private String uv;
        private String icon;
        private String sminTemp;
        private String smaxTemp;
        private String scurrTemp;
        private Bitmap currentWeatherPic;

        protected String[] doInBackground(String ... args){
            try {
                URL url = new URL(args[0]);
                URL url2 = new URL(args[1]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection urlConnection2 = (HttpURLConnection) url2.openConnection();

                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput( response  , "UTF-8");
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT
                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_TAG) {
                        if(xpp.getName().equals("temperature")) {
                            scurrTemp = xpp.getAttributeValue(null, "value");
                            Log.e("WeatherForecast",scurrTemp);
                            publishProgress(25);
                            sminTemp = xpp.getAttributeValue(null, "min");
                            Log.e("WeatherForecast",sminTemp);
                            publishProgress(50);
                            smaxTemp = xpp.getAttributeValue(null, "max");
                            Log.e("WeatherForecast",smaxTemp);
                            publishProgress(75);
                        }
                        if(xpp.getName().equals("weather")){
                            icon = xpp.getAttributeValue(null, "icon");
                            Log.i("WeatherForecast","Looking for file: " + icon + ".png");
                            if (fileExists(icon+".png")){
                                Log.i("WeatherForecast","File: " + icon + ".png was found locally");
                            } else{
                                Log.i("WeatherForecast","File: " + icon + ".png was found externally");
                                URL urlImage = new URL("http://openweathermap.org/img/w/"+icon+".png");
                                urlConnection = (HttpURLConnection) urlImage.openConnection();
                                urlConnection.connect();
                                int responseCode = urlConnection.getResponseCode();
                                if (responseCode == 200){
                                    currentWeatherPic = BitmapFactory.decodeStream(urlConnection.getInputStream());
                                    FileOutputStream outputStream = openFileOutput(icon+".png", Context.MODE_PRIVATE);
                                    currentWeatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                            }
                            publishProgress(100);
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

                InputStream response2 = urlConnection2.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response2, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject uvReport = new JSONObject(result);
                uv = Double.toString(uvReport.getDouble("value"));

            } catch (Exception e){
                Log.e("WeatherForcast", e.getMessage());
            }

            return new String[]{scurrTemp, sminTemp, smaxTemp, uv, icon};
        }
        public void onProgressUpdate(Integer ... args){
            pb.setProgress(args[0], true);
        }
        public void onPostExecute(String[] fromDoInBackground){
            currTemp.setText("Current Temp:" + fromDoInBackground[0]);
            minTemp.setText("Minimum Temp: " + fromDoInBackground[1]);
            maxTemp.setText("Maximum Temp: " + fromDoInBackground[2]);
            uvRating.setText("UV Rating: " + fromDoInBackground[3]);
            FileInputStream fis;
            String icon = fromDoInBackground[4];
            try{
                fis = openFileInput(icon+".png");
                currentWeatherPic = BitmapFactory.decodeStream(fis);
                currWeather.setImageBitmap(currentWeatherPic);
            } catch (FileNotFoundException e){
                Log.e("WeatherForecast", e.getMessage());
            }
            pb.setVisibility(View.INVISIBLE);

        }
        public boolean fileExists(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


    }
}