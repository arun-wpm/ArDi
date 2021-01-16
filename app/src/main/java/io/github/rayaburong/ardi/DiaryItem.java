package io.github.rayaburong.ardi;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public static DiaryItem parseFromString(String string) {
        // TODO: change later
        return new DiaryItem(new Date(), Mood.NEUTRAL, new ArrayList<>(), "");
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
