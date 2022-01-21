package com.marklund.pather.maze;

import java.awt.*;

public class ColorTester {
    public final static ColorTester INSTANCE = new ColorTester();

    private ColorTester(){}

    public boolean isBlack(Color color) {
        if(((color.getRed() == color.getBlue()) || (color.getRed() == color.getGreen()))
                && (color.getRed() < 160 || color.getBlue() < 160)
        )
            return true;
        if(color.getRed() < 50 && color.getGreen() < color.getRed() && color.getBlue() < color.getRed())
            return true;
        if(color.getGreen() < 50 && color.getGreen() > color.getRed() && color.getBlue() < color.getGreen())
            return true;
        return (color.getBlue() < 50 && color.getBlue() > color.getRed() && color.getBlue() > color.getGreen());
    }

    public boolean isWhite(Color color) {
        return color.getRed() > 220 && color.getGreen() > 220 && color.getBlue() > 220;
    }

    public boolean isRed(Color color) {
        return color.getBlue() < 100 && color.getGreen() < 100 && color.getRed() > 150;
    }

    public boolean isBlue(Color color) {
        return (color.getRed() < 100 || color.getBlue() < 100) && color.getBlue() > 150;
    }
}
