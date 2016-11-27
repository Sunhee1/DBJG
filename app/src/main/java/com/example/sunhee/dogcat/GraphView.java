package com.example.sunhee.dogcat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by sunhee on 2016-11-27.
 */

public class GraphView extends View {

    public static boolean BAR = true;
    public static boolean LINE = false;

    private Paint paint;
    private float[] values;
    private float[] goals;
    private String[] horlabels;
    private String[] verlabels;
    private String title;
    private boolean type;

    public GraphView(Context context, float[] values, float[] goals, String title, String[] horlabels, String[] verlabels, boolean type) {
        super(context);

        if (values == null)
            values = new float[0];
        else
            this.values = values;

        if(goals == null)
            goals = new float[0];
        else
            this.goals = goals;

        if (title == null)
            title = "";
        else
            this.title = title;

        if (horlabels == null)
            this.horlabels = new String[0];
        else
            this.horlabels = horlabels;

        if (verlabels == null)
            this.verlabels = new String[0];
        else
            this.verlabels = verlabels;

        this.type = type;

        paint = new Paint();
    }

    public void setValue(float[] values, String[] horlabels, String[] verlabels)
    {
        this.values = values;
        this.horlabels = horlabels;
        this.verlabels = verlabels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float border = 20;
        float horstart = border * 2;
        float height = getHeight() - border;
        float width = getWidth() - border;
        float max = getMax();
        float min = getMin();
        float diff = max - min;
        float graphheight = height - (2 * border);
        float graphwidth = width - (2 * border);

        paint.setTextAlign(Paint.Align.LEFT);
        int vers = verlabels.length - 1;

        for (int i = 0; i < verlabels.length; i++) {
            paint.setColor(Color.DKGRAY);
            float y = ((graphheight / vers) * i) + border;
            canvas.drawLine(horstart, y, width, y, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            canvas.drawText(verlabels[i], 10, y, paint);
        }

        int hors = horlabels.length;

        for (int i = 0; i < hors + 1; i++) {
            paint.setColor(Color.DKGRAY);
            float x = ((graphwidth / hors) * i) + horstart;
            canvas.drawLine(x, height - border, x, border, paint);
            paint.setTextAlign(Paint.Align.CENTER);

            if (i==4)
                paint.setTextAlign(Paint.Align.RIGHT);

            if (i==0)
                paint.setTextAlign(Paint.Align.LEFT);

            paint.setColor(Color.BLACK);

            if(i != hors || horlabels.length-1 > i)
                canvas.drawText(horlabels[i], x, height + 4, paint);

        }

        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

        if (max != min) {
            paint.setColor(Color.BLUE);
            if (type == BAR) {
                float xplus = (graphwidth / (hors * 3));

                for (int i = 1; i < hors; i++) {
                    if((horlabels.length-1) < i) break;
                    float val = goals[i] - min;
                    float rat = val / diff;
                    float h = graphheight * rat;
                    float x = ((graphwidth / hors) * i) + horstart;
                    paint.setColor(Color.MAGENTA);
                    canvas.drawRect(x - (xplus), (border - h) + graphheight, x + (xplus / 5), height - (border - 1), paint);

                    val = values[i] - min;
                    rat = val / diff;
                    h = graphheight * rat;
                    paint.setColor(Color.BLUE);
                    canvas.drawRect(x - (xplus /5), (border - h) + graphheight, x + (xplus), height - (border - 1), paint);
                }
            } else {
                float lasth = 0;

                for (int i = 1; i < hors; i++) {
                    float val = values[i] - min;
                    float rat = val / diff;
                    float h = graphheight * rat;
                    float x = ((graphwidth / hors) * i) + horstart;

                    canvas.drawLine(x, (border - lasth) + graphheight, ((graphwidth / hors) * (i+1)) + horstart, (border - h) + graphheight, paint);
                    lasth = h;
                }
            }
        }
    }

    private float getMax() {
        float largest = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++)
            if (values[i] > largest)
                largest = values[i];
        return largest;
    }

    private float getMin() {
        float smallest = Integer.MAX_VALUE;
        for (int i = 0; i < values.length; i++)
            if (values[i] < smallest)
                smallest = values[i];
        return smallest;
    }
}