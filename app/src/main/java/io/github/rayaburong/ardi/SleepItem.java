package io.github.rayaburong.ardi;

import java.util.Date;
import java.util.List;

class SleepItem implements ArdiItem {
    Date date;
    List<Date> upTime;
    List<Date> sleepTime;

    @Override
    public Date getDate() {
        return (Date) date.clone();
    }
}
