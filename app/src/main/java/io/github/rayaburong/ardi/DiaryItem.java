package io.github.rayaburong.ardi;

import android.location.Location;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

class DiaryItem implements ArdiItem {
    Date date;
    Mood mood;
    List<Location> locations;
    String body;

    private DiaryItem(Date date, Mood mood, List<Location> locations, String body) {
        this.date = date;
        this.mood = mood;
        this.locations = locations;
        this.body = body;
    }

    public static DiaryItem parseJSON(JSONObject jsonObject) throws ParseException {
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        Date date = format.parse(jsonObject.optString("ymd", ""));
        return new DiaryItem(
                date,
                Mood.values()[Integer.parseInt(jsonObject.optString("mood", ""))],
                Arrays.stream(jsonObject.optString("locations", "").split("!")).map(Location::new).collect(Collectors.toList()),
                jsonObject.optString("body", ""));
    }

    @Override
    public Date getDate() {
        return date;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public void removeLocation(int index) {
        locations.remove(index);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryItem diaryItem = (DiaryItem) o;
        return date.equals(diaryItem.date) &&
                mood == diaryItem.mood &&
                locations.equals(diaryItem.locations) &&
                body.equals(diaryItem.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, mood, locations, body);
    }

    @Override
    public String toString() {
        return "DiaryItem{" +
                "date=" + date +
                ", mood=" + mood +
                ", locations=" + locations +
                ", body='" + body + '\'' +
                '}';
    }
}
