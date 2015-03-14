package asimplemodule.tomateduino;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jimmy on 3/14/15.
 */
public class DashboardFragmentTab extends Fragment {


    public void btnPourWater(View view) {
        EditText txtServerAddress = (EditText) getActivity().findViewById(R.id.txtServerIp);
        EditText txtPlant= (EditText) getActivity().findViewById(R.id.txtPlant);

        new ReadTomateResponseTask().execute(
                "http://" + txtServerAddress.getEditableText().toString() + ":8080/test/api/v1/poor/" +
                        txtPlant.getEditableText().toString());
    }
    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    private class ReadTomateResponseTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                /*JSONObject response =
                        new JSONObject(jsonObject.getString("weatherObservation"));
*/
                Toast.makeText(getActivity().getBaseContext(), jsonObject.getString("response").equals("Ok") ? "Pouring water went ok" : "something went wrong. check state",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.d("ReadTomateResponseTask", e.getLocalizedMessage());
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragement_dashboard,container,false);

        return view;
    }
}
