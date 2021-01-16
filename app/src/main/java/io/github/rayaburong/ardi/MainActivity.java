package io.github.rayaburong.ardi;

import android.location.Location;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.github.rayaburong.ardi.ui.main.SectionsPagerAdapter;

import com.mongodb.ConnectionString;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;

import org.bson.Document;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Testing some interactions with MongoDB
                final String uriString = "mongodb+srv://" + getResources().getString(R.string.mongoDBUsername) + ":" +
                        getResources().getString(R.string.mongoDBPassword) + "@" +
                        getResources().getString(R.string.mongoDBClusterAddress) + "/" +
                        getResources().getString(R.string.mongoDBDatabaseName) + "retryWrites=true&w=majority";
                MongoClient mongoClient = MongoClients.create(uriString);
                MongoDatabase database = mongoClient.getDatabase(getResources().getString(R.string.mongoDBDatabaseName));
                MongoCollection<Document> collection = database.getCollection("Diary");

                // Writing to the collection
                Document diaryItem = new Document("ymd", 20210116)
                        .append("mood", 4)
                        .append("content", "This is a test.");
                collection.insertOne(diaryItem);

                // Reading from the collection
                FindIterable<Document> findIterable = collection.find(new Document());
                for (Document document : findIterable) {
                    Log.d("mongoDB", document.toJson());
                }
                mongoClient.close();
            }
        });
    }
}