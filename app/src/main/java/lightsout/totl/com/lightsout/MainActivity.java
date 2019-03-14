package lightsout.totl.com.lightsout;

import android.content.DialogInterface;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    int curBrightnessValue= 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Thread.UncaughtExceptionHandler oldHandler =
                Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(
                            Thread paramThread,
                            Throwable paramThrowable
                    ) {

                     Log.e("AppCrash","Caught Exception");

                        if (oldHandler != null)

                            Toast.makeText(MainActivity.this,"App has Crashed. Please send Log to admin.",Toast.LENGTH_LONG).show();
                            System.out.println("App has Crashed and Exception is caught");
                            handleUncaughtException(paramThread, paramThrowable);

                            System.exit(2); //Prevents the service/app from freezing
                    }
                });

        setContentView(R.layout.activity_main);

       final Button b1 = (Button) findViewById(R.id.button);
       final TextView textView = (TextView) findViewById(R.id.textView);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(b1.getText().equals(R.string.button_name1)){
                    Toast.makeText(MainActivity.this, "Name:1" + b1.getText(), Toast.LENGTH_LONG).show();

                    b1.setText(R.string.button_name);
                    Toast.makeText(MainActivity.this, "Name:1" + b1.getText(), Toast.LENGTH_LONG).show();

                }else {
                    try {
                        curBrightnessValue = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (curBrightnessValue <= 10) {
                        textView.setVisibility(View.INVISIBLE);
                        b1.setText(R.string.button_name1);
                        Toast.makeText(MainActivity.this, "You Win :) ", Toast.LENGTH_LONG).show();
                    } else if (curBrightnessValue > 170) {
                        Toast.makeText(MainActivity.this, "My eyes! My Eyes!", Toast.LENGTH_LONG).show();
                    } else if (curBrightnessValue < 170 && curBrightnessValue > 80) {
                        Toast.makeText(MainActivity.this, "Not Quite there Yet", Toast.LENGTH_LONG).show();
                    } else if (curBrightnessValue < 60 && curBrightnessValue > 10) {
                        Toast.makeText(MainActivity.this, "Almost there.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "You can Still See me :(", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public void handleUncaughtException(Thread thread, Throwable e)
    {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString(); // stack trace as a string


        Intent intent = new Intent (this, Sendlogs.class);
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        intent.putExtra("Logs", stackTrace);
        startActivity (intent);

        System.exit(1); // kill off the crashed app
    }
}
