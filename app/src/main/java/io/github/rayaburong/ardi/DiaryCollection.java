package io.github.rayaburong.ardi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class DiaryCollection implements ArdiCollection {
    final List<DiaryItem> diaryItems;

    private DiaryCollection(List<DiaryItem> diaryItems) {
        this.diaryItems = diaryItems;
    }

    public static DiaryCollection parseFromDB(String filename) {
        // TODO: change later
        return new DiaryCollection(new ArrayList<>());
    }

    @Override
    public int getLength() {
        return diaryItems.size();
    }

    @Override
    public ArdiItem getItem(int index) {
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
