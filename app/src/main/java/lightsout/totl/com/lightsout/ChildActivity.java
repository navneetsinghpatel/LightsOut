package lightsout.totl.com.lightsout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChildActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   final Button b2 = (Button) findViewById(R.id.button1);
        setContentView(R.layout.activity_child);
        final Button b3 = (Button)findViewById(R.id.button1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });*/
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ChildActivity.this,"App has Crashed. Please send Log to admin.",Toast.LENGTH_LONG).show();
                int x =10/0;

            }
        });


    }

}
