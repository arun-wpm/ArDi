package io.github.rayaburong.ardi;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.rayaburong.ardi.ui.main.SectionsPagerAdapter;
import io.realm.mongodb.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private String username = "arunwpm"; // TODO: change this
    private RecyclerView recyclerView; // shared recycler view
    private LinearLayoutManager linearLayoutManager; // shared linear layout manager
    private DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        // makeshift code for http request
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(
            new Runnable(){
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(getString(R.string.ardiServer) + "/diary")
                            .addHeader("user", username)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();
                        diaryAdapter = new DiaryAdapter(DiaryCollection.parseString(responseString));
                        Log.d("MainActivity", "run: " + diaryAdapter);
                    } catch (IOException | JSONException | ParseException e) {
                        e.printStackTrace();
                    } finally {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(diaryAdapter);
                            }
                        });
                    }
                }
            }
        );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}