package com.example.pruebas2;

import static android.view.MotionEvent.INVALID_POINTER_ID;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {
    float dX, dY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imagen = findViewById( R.id.imagensita);
        LinearLayout dropTarget = findViewById(R.id.droptarget);

        imagen.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Create a ClipData item holding the image's tag for data transfer
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                // Create a ClipData object
                ClipData dragData = new ClipData(
                        (CharSequence) v.getTag(),
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                        item);

                // Create a drag shadow
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(imagen);

                // Start the drag operation
                v.startDragAndDrop(dragData, myShadow, null, 0);

                return true;
            }
        });

        imagen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("-- v.getX()------------------------", Float.toString( v.getX()));
                Log.d("-- v.getY()------------------------", Float.toString( v.getY()));
                Integer cubox = v.getWidth();
                Integer cuboy = v.getHeight();
                imagen.setBackgroundColor(000000);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Capture the difference between touch coordinates and view position
                        dX = (v.getWidth() / 2) - event.getX();
                        dY = (v.getHeight() / 2) - event.getY();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        // Update the view's position as the user moves their finger
                        dropTarget.setBackgroundColor(Color.YELLOW);
                        v.animate()
                                .x(event.getRawX() - v.getWidth() / 2) // Adjust for center
                                .y(event.getRawY() - v.getHeight() / 2)
                                .setDuration(0)
                                .start();
                        break;

                    case MotionEvent.ACTION_UP:
                        // Get the position relative to the parent (usually a layout)
                        float finalX = v.getX(); // X relative to the parent
                        float finalY = v.getY(); // Y relative to the parent

                        if((finalX <= ((dropTarget.getX() + dropTarget.getWidth())-cubox) && finalX >= dropTarget.getX()) &&
                                (finalY >= dropTarget.getY() && finalY <= ((dropTarget.getY() + dropTarget.getHeight())-cuboy))){
                            dropTarget.setBackgroundColor(Color.RED);
                        }

                        Log.d("-- getRawX---", Float.toString( event.getRawX()));
                        Log.d("-- getRawY++++++++++", Float.toString( event.getRawY()));
                        Log.d("-- v.getX()---", Float.toString( v.getX()));
                        Log.d("-- v.getY()---", Float.toString( v.getY()));


                        Log.d("-- ANCHOSTART---", Float.toString( dropTarget.getX()));
                        Log.d("-- ALTURA START---", Float.toString( dropTarget.getY()));

                        Log.d("-- ANCHO---", Float.toString( dropTarget.getWidth()));
                        Log.d("-- ALTURA---", Float.toString( dropTarget.getHeight()));
                        Log.d("-- finalx---", Float.toString( finalX));
                        Log.d("-- finaly---", Float.toString( finalY));

                        //dropTarget.setBackgroundColor(Color.GREEN);
                        System.out.println(finalX);
                        System.out.println(finalY);
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });
    }
}
