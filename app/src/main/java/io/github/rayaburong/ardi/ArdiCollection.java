package io.github.rayaburong.ardi;

import java.util.Date;
import java.util.List;

interface ArdiCollection {
    public int getLength();
    public ArdiItem getItem(int index);
    public List<ArdiItem> getItems(Date date);
    public void addItem(ArdiItem item);
    public void removeItem(int index);
}
