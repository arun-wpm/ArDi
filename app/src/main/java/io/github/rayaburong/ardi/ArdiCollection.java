package io.github.rayaburong.ardi;

import java.util.ArrayList;
import java.util.Date;

interface ArdiCollection {
    public int getLength();
    public ArdiItem getItem(int index);
    public ArrayList<ArdiItem> getItems(Date date);
    public void addItem(ArdiItem item);
    public void removeItem(int index);
}
