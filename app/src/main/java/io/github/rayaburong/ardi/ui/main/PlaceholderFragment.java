package io.github.rayaburong.ardi.ui.main;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.rayaburong.ardi.DiaryAdapter;
import io.github.rayaburong.ardi.DiaryCollection;
import io.github.rayaburong.ardi.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
//    private final AccountManager accountManager = AccountManager.get(getContext());
//    private final Account[] users = accountManager.getAccounts();
    private final String username = "arunwpm"; // TODO: change later when AccountManager and login is set up
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DiaryAdapter diaryAdapter; // TODO: change later

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        recyclerView = root.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());

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
                        Log.d("PlaceholderFragment", "run: " + diaryAdapter);
                    } catch (IOException | JSONException | ParseException e) {
                        e.printStackTrace();
                    } finally {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(diaryAdapter);
                                recyclerView.setLayoutManager(linearLayoutManager);
                            }
                        });
                    }
                }
            }
        );

        return root;
    }

    public void update(String responseString) throws JSONException, ParseException {
        // TODO: make animation smoother by calling either add or remove
        diaryAdapter.updateCollection(responseString);
        diaryAdapter.notifyDataSetChanged();
    }
}