package com.example.sam_tp1_seminario2;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewGameActivity extends AppCompatActivity {

    private final static String[] tablero = {" 3 x 3 "," 4 x 4 "," 5 x 5 ",
            " 6 x 6 "," 7 x 7 "," 8 x 8 "," 9 x 9 "};
    /*private final static String[] tablero = {"[ 3 x 3 ]","[ 4 x 4 ]","[ 5 x 5 ]",
        "[ 6 x 6 ]","[ 7 x 7 ]","[ 8 x 8 ]","[ 9 x 9 ]"};*/
    private EditText et;
    private Spinner spTablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newgame);

        et = (EditText) findViewById(R.id.nJugador);
        spTablero = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, tablero);

        spTablero.setAdapter(adapter);
        spTablero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.earlygameboy);
                ((TextView) adapterView.getChildAt(0)).setTypeface(font);
                /* Esta forma siguiente solo funciona en API 26 */
                /*((TextView) adapterView.getChildAt(0)).setTypeface(getResources().getFont(R.font.earlygameboy)); */
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorSpinner));
                ((TextView) adapterView.getChildAt(0)).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                Object item = adapterView.getItemAtPosition(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void Iniciar(View v) {
        if(et.getText().toString().isEmpty()) {

            // Toast Customizado...
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Jugador desconocido...");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

        } else {
            Intent intent = new Intent(NewGameActivity.this, MainActivity.class);
            intent.putExtra("nombre", et.getText().toString());

            if (et.getText().toString().equals("wolf3d")) {
                intent.putExtra("tamTablero", "wolf3d");
            } else {
                intent.putExtra("tamTablero", spTablero.getSelectedItem().toString());
            }

            startActivity(intent);
            finish();
        }
    }


}
