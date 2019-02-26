package yellow7918.ajou.ac.janggi;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
public class APIExamTranslate {

    public class TranslateTask extends AsyncTask<String,Void,String> {

        public String resultText;

        String clientId = "QMxnwSa4bAHoZaa8uCgQ";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "88JUNdhZTv";//애플리케이션 클라이언트 시크릿값";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            String sourceText = strings[0];
            try {
                //String text = URLEncoder.encode("만나서 반갑습니다.", "UTF-8");
                String text = URLEncoder.encode(sourceText, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=en&target=kr&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                return response.toString();
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("background result", s.toString());

            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonElement rootObj = parser.parse(s.toString())
                    .getAsJsonObject().get("message")
                    .getAsJsonObject().get("result");
            TranslatedItem items = gson.fromJson(rootObj.toString(), TranslatedItem.class);

            //tts.speak(items.getTranslatedText()+"로 인식했습니다.", TextToSpeech.QUEUE_FLUSH, null);
            //tvResult.setText(items.getTranslatedText());
        }
        private class TranslatedItem {
            String translatedText;

            public String getTranslatedText() {
                return translatedText;
            }
        }
    }
}