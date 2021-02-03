package io.github.rayaburong.ardi;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.rayaburong.ardi.ui.main.PlaceholderFragment;
import io.github.rayaburong.ardi.ui.main.SectionsPagerAdapter;
import io.realm.mongodb.User;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
//    private final AccountManager accountManager = AccountManager.get(getContext());
//    private final Account[] users = accountManager.getAccounts();
    private final String username = "arunwpm"; // TODO: change later when AccountManager and login is set up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // makeshift code for http request
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());
                executorService.execute(
                    new Runnable(){
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            FormBody formBody = new FormBody.Builder()
                                    .add("user", username)
                                    .add("ymd", dateFormat.format(Date.from(Instant.now())))
                                    .add("mood", Integer.toString(4)) // TODO: change later
                                    .add("body", "This is a test, but from the app!") // TODO: change later
                                    .build();
                            Request request = new Request.Builder()
                                    .url(getString(R.string.ardiServer) + "/diary")
                                    .post(formBody)
                                    .build();

                            String responseString = "";
                            try {
                                Response response = client.newCall(request).execute();
                                responseString = response.body().string();
                                Log.d("MainActivity", "run: " + responseString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                String finalResponseString = responseString;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        FragmentManager fragmentManager = getSupportFragmentManager();
                                        List<Fragment> fragmentList = fragmentManager.getFragments();
                                        for (Fragment fragment : fragmentList) {
                                            if (fragment instanceof PlaceholderFragment) {
                                                try {
                                                    ((PlaceholderFragment) fragment).update(finalResponseString);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                );
                Snackbar.make(view, "Added a Diary entry for today!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}