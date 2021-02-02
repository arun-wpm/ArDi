package io.github.rayaburong.ardi;

import java.util.Date;
import java.util.List;

interface ArdiCollection {
    final static String MONGO_ENTRYPOINT = "https://ardi-server.arunwpm.repl.co";
    public int getLength();
    public ArdiItem getItem(int index);
    public List<ArdiItem> getItems(Date date);
    public void addItem(ArdiItem item);
    public void removeItem(int index);
}
