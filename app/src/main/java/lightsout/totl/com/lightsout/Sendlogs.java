package lightsout.totl.com.lightsout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

public class Sendlogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendlogs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String filename = "crashlogs";
                File file = null;
                try {
                    file = File.createTempFile(filename, ".txt", getApplication().getApplicationContext().getExternalCacheDir());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(file);
                String fileContents = getIntent().getExtras().getString("Logs");
                FileOutputStream outputStream;
                try {
                    FileOutputStream f = new FileOutputStream(file);
                    PrintWriter pw = new PrintWriter(f);
                    pw.println(fileContents);
                    pw.flush();
                    pw.close();
                    f.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"navnpate@in.ibm.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Logs from agent crash");

             //   i.putExtra(Intent.EXTRA_TEXT   , getIntent().getExtras().getString("Logs"));
                i.putExtra(i.EXTRA_STREAM, uri);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Sendlogs.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }


}
