package com.example.sam_tp1_seminario2;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String nombreJugador;
    private String tamTablero;
    private Button buttonUp, buttonDown, buttonLeft, buttonRight;
    ImageView[] imageViews = null;

    private MazeBoard board;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maze);

        getDatos();

        tv = findViewById(R.id.nJugador);
        String jugador = String.format("1P %s", nombreJugador);
        tv.setText(jugador);

        Toast toast = new Toast(getApplicationContext());

        if(tamTablero.equals(" 3 x 3 ")) {
            /* Homenaje Wolfenstein 3d */
            String saludo = String.format("Homenaje a Wolfenstein 3D");
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText(saludo);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } else {
            /* SALUDO */
            String saludo = String.format("Bienvenido %s ", nombreJugador + "!");
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText(saludo);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            /* ------ */
        }

        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);

        buttonUp.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);

        GameView mazeView = (GameView)findViewById(R.id.gameView);
        mazeView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mazeView.setZOrderMediaOverlay(true);
        mazeView.setZOrderOnTop(true);

        setupMazeBoard(mazeView.getBoard());
    }

    // TODO this must probably be moved to GameView, or helper.
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupMazeBoard(MazeBoard theBoard) {

        board = theBoard;
        int height = board.getHeight();
        int width = board.getWidth();

        imageViews = new ImageView[width * height];

        int resId = 0;

        TableLayout table = findViewById(R.id.mazeBoard);
        for (int i=0; i<height; i++){
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams();
            rowParams.width = TableRow.LayoutParams.MATCH_PARENT;
            rowParams.height = TableRow.LayoutParams.MATCH_PARENT;
            rowParams.weight = 1;
            rowParams.gravity = Gravity.CENTER;
            row.setLayoutParams(rowParams);
            table.addView(row);

            for (int j=0; j<width; j++){
                BoardPiece piece = board.getPiece(j, i);

                resId = lookupResource(piece);

                ImageView imageView = new ImageView(this);
                imageView.setBackgroundResource(resId);

                TableRow.LayoutParams imageViewParams = new TableRow.LayoutParams();
                imageViewParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                imageViewParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                imageViewParams.weight = 1;
                imageView.setLayoutParams(imageViewParams);

                row.addView(imageView);

                imageViews[(j%board.getWidth())+ board.getHeight()*i] = imageView;

                if(piece.isWinFlag() && !piece.isWolf3d()) {
                    imageView.setForeground(getDrawable(R.drawable.winflag_d));
                }
                if(piece.isWinFlag() && piece.isWolf3d()) {
                    imageView.setForeground(getDrawable(R.drawable.winflag_e));
                }

            }
        }
    }

    private int lookupResource(BoardPiece piece) {
        int iconIndex = 0b1000 * (piece.isOpen(MazeBoard.Direction.WEST)? 1:0) +
                0b0100 * (piece.isOpen(MazeBoard.Direction.NORTH)? 1:0) +
                0b0010 * (piece.isOpen(MazeBoard.Direction.EAST)? 1:0) +
                0b0001 * (piece.isOpen(MazeBoard.Direction.SOUTH)? 1:0);

        int[] iconLookupTable = { 0,
                R.drawable.m1b,
                R.drawable.m1r,
                R.drawable.m2rb,
                R.drawable.m1t,
                R.drawable.m2v,
                R.drawable.m2tr,
                R.drawable.m3l,
                R.drawable.m1l,
                R.drawable.m2bl,
                R.drawable.m2h,
                R.drawable.m3t,
                R.drawable.m2lt,
                R.drawable.m3r,
                R.drawable.m3b,
                R.drawable.m4};

        return iconLookupTable[iconIndex];
    }

    @Override
    public void onClick(View v) {
    // complete with event listener logic
        if (v == buttonUp) {
            board.setNewDirection(MazeBoard.Direction.NORTH);
        }
        else if (v == buttonDown) {
            board.setNewDirection(MazeBoard.Direction.SOUTH);
        }
        else if (v == buttonLeft) {
            board.setNewDirection(MazeBoard.Direction.WEST);
        }
        else if (v == buttonRight) {
            board.setNewDirection(MazeBoard.Direction.EAST);
        }
    }

    private void getDatos() {
        Bundle extras = getIntent().getExtras();
        this.nombreJugador = extras.getString("nombre");
        this.tamTablero = extras.getString("tamTablero");
    }


}
