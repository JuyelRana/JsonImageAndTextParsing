package com.rana.juyelrana.jsonimageandtextparsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView productsListView;

    private JSONObject finalObject;
    private Products products;
    private URL url;
    private InputStream stream;
    private StringBuffer buffer;
    private String line;
    private String finalJson;
    private JSONObject parentObject;
    private JSONArray parentArray;
    private List<Products> productsList;
    //private ListView productsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create global configuration and initialize ImageLoader with this config
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
//        ImageLoader.getInstance().init(config);
        //compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5' this compile wile must add build.gradle

        // Create default option which wile be used for every
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config); // Do it application start
        //compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5' this compile wile must add build.gradle


        productsListView = (ListView) findViewById(R.id.productsListView);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Call JSONTask() method to execute url
        new JSONTask().execute("http://192.168.56.1/Ecommerce/getAllProducts.php");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {

            //new JSONTask().execute("http://192.168.56.1/Ecommerce/getAllProducts.php");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class JSONTask extends AsyncTask<String, String, List<Products>> {

        @Override
        protected List<Products> doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                buffer = new StringBuffer();
                line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                finalJson = buffer.toString();

                parentObject = new JSONObject(finalJson);
                parentArray = parentObject.getJSONArray("result");

                productsList = new ArrayList<>();

                //StringBuffer productStringBuffer = new StringBuffer();

                for (int i = 0; i < parentArray.length(); i++) {

                    finalObject = parentArray.getJSONObject(i);

                    products = new Products();

                    products.setImage(finalObject.getString("image"));
                    products.setTitle(finalObject.getString("title"));
                    products.setSubtitle(finalObject.getString("subtitle"));
                    products.setPrice(finalObject.getString("price"));
                    products.setDescription(finalObject.getString("description"));


                    //Adding the final object to an arrylist
                    productsList.add(products);

                }
                return productsList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                try {

                    if (reader != null) {

                        reader.close();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(List<Products> result) {
            super.onPostExecute(result);
            //Here i can set the all products to listview

            ProductsAdapter adapter = new ProductsAdapter(getApplicationContext(), R.layout.row, result);
            productsListView.setAdapter(adapter);

            productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(getApplicationContext(), "Clicked " + view.getVerticalScrollbarPosition(), Toast.LENGTH_LONG).show();


                }
            });


        }

    }

}
