package com.marklund.pather.maze;

import java.awt.*;

public class ColorTester {
    public final static ColorTester INSTANCE = new ColorTester();

    private ColorTester(){}

    public boolean isWhite(Color color) {
        return color.getRed() > 220 && color.getGreen() > 220 && color.getBlue() > 220;
    }
}
