package zimkand.de.einkaufszettel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView text = findViewById(R.id.text);
        final EditText eingabe = findViewById(R.id.eingabe);
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage(R.string.erfolg);
        final AlertDialog. Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog2 = builder2.create();
        builder.setMessage(R.string.sicherheitsfrage)
                .setCancelable(false)
                .setPositiveButton("JA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new ActivityDataSource(text,'d',alertDialog2).execute(eingabe.getText().toString());
                    }
                })
                .setNegativeButton("NEIN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        final AlertDialog alertDialog = builder.create();
        Button send = findViewById(R.id.senden);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ActivityDataSource(text,'s',alertDialog2).execute(eingabe.getText().toString());
            }
        });
        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hauptfenster,menu);
        return true;
    }*/
}