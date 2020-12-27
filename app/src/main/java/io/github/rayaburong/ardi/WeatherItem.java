package io.github.rayaburong.ardi;

import java.util.Date;

class WeatherItem implements ArdiItem {
    final Date date;
    final WeatherCondition condition;
    final float tempHigh, tempLow;

    public WeatherItem(Date date, WeatherCondition condition, float tempHigh, float tempLow) {
        this.date = date;
        this.condition = condition;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
    }

    @Override
    public Date getDate() {
        return (Date) date.clone();
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public float getTempHigh() {
        return tempHigh;
    }

    public float getTempLow() {
        return tempLow;
    }
}
