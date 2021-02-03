package io.github.rayaburong.ardi;

import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {
    private static final Map<Mood, Integer> MOOD_TO_COLOR = new HashMap<Mood, Integer>() {
        {
            put(Mood.EXTREMELY_BAD, R.color.cedar_chest); // TODO: change
            put(Mood.VERY_BAD, R.color.cedar_chest);
            put(Mood.BAD, R.color.apricot);
            put(Mood.NEUTRAL, R.color.cultured);
            put(Mood.GOOD, R.color.powder_blue);
            put(Mood.VERY_GOOD, R.color.morning_blue);
            put(Mood.EXTREMELY_GOOD, R.color.morning_blue); // TODO: change
        }
    };
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
    private DiaryCollection diaryCollection;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView diaryDate;
        private final TextView diaryLocations;
        private final View diaryMood;
        private final TextView diaryBody;

        public ViewHolder(View view) {
            super(view);

            diaryDate = (TextView) view.findViewById(R.id.diaryDate);
            diaryLocations = (TextView) view.findViewById(R.id.diaryLocations);
            diaryMood = view.findViewById(R.id.diaryMood);
            diaryBody = (TextView) view.findViewById(R.id.diaryBody);
        }

        public TextView getDiaryDate() {
            return diaryDate;
        }

        public TextView getDiaryLocations() {
            return diaryLocations;
        }

        public View getDiaryMood() {
            return diaryMood;
        }

        public TextView getDiaryBody() {
            return diaryBody;
        }
    }

    public DiaryAdapter(DiaryCollection diaryCollection) {
        this.diaryCollection = diaryCollection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDiaryDate().setText(DATE_FORMAT.format(diaryCollection.getItem(position).getDate()));
        holder.getDiaryLocations().setText(diaryCollection.getItem(position).getLocations().stream().map(Location::getProvider).reduce("", String::concat));
        holder.getDiaryMood().setBackgroundColor(holder.getDiaryMood().getContext().getColor(MOOD_TO_COLOR.get(diaryCollection.getItem(position).getMood())));
        holder.getDiaryBody().setText(diaryCollection.getItem(position).getBody());
    }

    @Override
    public int getItemCount() {
        return diaryCollection.getLength();
    }

    public void updateCollection(String responseString) throws JSONException, ParseException {
        // TODO: make this better
        diaryCollection = DiaryCollection.parseString(responseString);
    }
}
