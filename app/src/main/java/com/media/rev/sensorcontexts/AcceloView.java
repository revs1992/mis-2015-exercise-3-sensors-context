package com.media.rev.sensorcontexts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.Vector;



public class AcceloView extends View {

    private Paint x_value,y_value,z_value,m_value;
    private Vector<Float> x_data = new Vector<>(),
               y_data = new Vector<>(),
                      z_data = new Vector<>(),
                       m_data = new Vector<>();
    private int ww, hh;



    public AcceloView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.BLACK);
        m_value = new Paint(0);
        m_value.setColor(Color.WHITE);

        x_value = new Paint(0);
        x_value.setColor(Color.RED);

        y_value = new Paint(0);
        y_value.setColor(Color.GREEN);

        z_value= new Paint(0);
        z_value.setColor(Color.BLUE);




    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint line = new Paint();
        line.setColor(Color.CYAN);

         //width = canvas.getWidth();
         //height = canvas.getHeight();



        for(int i = 1; i<x_data.size()-1; i++){
           canvas.drawLine(i-1,(hh/2),i,(hh/2),line);
           canvas.drawLine(i-1,(hh/2)+(-30*x_data.elementAt(i-1)),
                                        i,(hh/2)+(-30*x_data.elementAt(i)),x_value);
           canvas.drawLine(i-1,(hh/2)+(-30*y_data.elementAt(i-1)), i,(hh/2)+(-30*y_data.elementAt(i)),y_value);
            canvas.drawLine(i-1, (hh/2)+(-30*z_data.elementAt(i-1)),i, (hh/2)+(-30*z_data.elementAt(i)), z_value);

           canvas.drawLine(i-1,(hh/2)+(-30*m_data.elementAt(i-1)),i,(hh/2)+(-30*m_data.elementAt(i)), m_value);

                  }


}

    public void resetData(){
        for(int i = 0; i <= ww; i++){
            x_data.add(0.0f);
            y_data.add(0.0f);
            z_data.add(0.0f);
            m_data.add(0.0f);
        }
    }
    public void addData(float[] values){
        if(x_data.size() > ww && x_data.size()>2) {
            x_data.remove(1);
            y_data.remove(1);
            z_data.remove(1);
            m_data.remove(1);
        }
        x_data.add(values[0]);
        y_data.add(values[1]);
        z_data.add(values[2]);
        float magni = (float)Math.sqrt(  (Math.pow(values[0],2))
                                    +( Math.pow(values[1],2))
                                    +  (Math.pow(values[2],2) ) );
        m_data.add(magni);

        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh){
        super.onSizeChanged(w, h, ow, oh);
        ww = w;
        hh = h;
        resetData();
    }
    private void notify(String message){

        //Source: http://developer.android.com/guide/topics/ui/notifiers/notifications.html

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getContext())
                        .setSmallIcon(R.drawable.abc_ic_go_search_api_mtrl_alpha)
                        .setContentTitle("Context Recognition")
                        .setContentText(message);
        Intent resultIntent = new Intent(this.getContext(), this.getContext().getClass());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.getContext());

        stackBuilder.addParentStack(this.getContext().getClass());
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}



