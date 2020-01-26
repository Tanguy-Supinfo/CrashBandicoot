package com.supinfo.project.crashbandicoot.graphics;

public class Colors {

    public static final float[] WHITE = new Colors(1, 1, 1, 1).getColor();
    public static final float[] BLACK = new Colors(0, 0, 0, 1).getColor();

    public static final float[] RED = new Colors(1, 0, 0, 1).getColor();
    public static final float[] GREEN = new Colors(0, 1, 0, 1).getColor();
    public static final float[] BLUE = new Colors(0, 0, 1, 1).getColor();

    public float r, g, b, a;

    public Colors(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float[] getColor() {
        return new float[]{r, g, b, a};
    }

}
