package io.github.rayaburong.ardi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class DiaryCollection implements ArdiCollection {
    final List<DiaryItem> diaryItems;

    private DiaryCollection(List<DiaryItem> diaryItems) {
        this.diaryItems = diaryItems;
    }

    public static DiaryCollection parseString(String jsonString) throws JSONException, ParseException {
        Log.d("DiaryCollection", "parseString: " + jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray resultArray = jsonObject.getJSONArray("result");
        List<DiaryItem> diaryItems = new ArrayList<>();
        for (int i = 0; i < resultArray.length(); i++) {
            diaryItems.add(DiaryItem.parseJSON(resultArray.getJSONObject(i)));
        }
        DiaryCollection diaryCollection = new DiaryCollection(diaryItems);
        return diaryCollection;
    }

    @Override
    public int getLength() {
        return diaryItems.size();
    }

    @Override
    public DiaryItem getItem(int index) {
        return diaryItems.get(index);
    }

    @Override
    public List<ArdiItem> getItems(Date date) {
        return diaryItems.stream().filter((element) -> element.getDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public void addItem(ArdiItem item) {

    }

    @Override
    public void removeItem(int index) {

    }
}
