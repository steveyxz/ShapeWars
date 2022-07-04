package me.partlysunny.shapewars.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ResizeableProgressBar extends ProgressBar {
    public ResizeableProgressBar(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super(min, max, stepSize, vertical, skin);
    }

    public ResizeableProgressBar(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        super(min, max, stepSize, vertical, skin, styleName);
    }

    public ResizeableProgressBar(float min, float max, float stepSize, boolean vertical, ProgressBarStyle style) {
        super(min, max, stepSize, vertical, style);
    }

    @Override
    public float getPrefWidth() {
        if (isVertical()) {
            Drawable knob = getStyle().knob, bg = getBackgroundDrawable();
            return Math.max(knob == null ? 0 : knob.getMinWidth(), bg == null ? 0 : bg.getMinWidth()) * (getWidth() / 140f);
        } else
            return getWidth();
    }

    @Override
    public float getPrefHeight() {
        if (isVertical())
            return getHeight();
        else {
            Drawable knob = getStyle().knob, bg = getBackgroundDrawable();
            return Math.max(knob == null ? 0 : knob.getMinHeight(), bg == null ? 0 : bg.getMinHeight()) * (getHeight() / 140f);
        }
    }
}
