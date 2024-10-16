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

        imagen.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("---------------------------------","ACTION_DRAG_STARTED");
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("---------------------------------","ACTION_DRAG_ENTERED");
                        // Code to execute when the drag enters the drop area
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("---------------------------------","ACTION_DRAG_EXITED");
                        // Code to execute when drag exits the drop area
                        return true;
                    case DragEvent.ACTION_DROP:
                        Log.d("---------------------------------","ACTION_DROP");
                        // Code to execute when dropped
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("---------------------------------","ACTION_DRAG_ENDED");
                        // Code to execute when drag ends
                        return true;
                    default:
                        return false;
                }
            }
        });


        // Set an OnDragListener on the drop target (TextView)
        dropTarget.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // Get the action type of the drag event
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Change the background color when drag starts
                        v.setBackgroundColor(Color.YELLOW);
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Change color when the dragged view enters the target area
                        v.setBackgroundColor(Color.GREEN);
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        // Reset color when dragged view exits the target area
                        v.setBackgroundColor(Color.YELLOW);
                        return true;

                    case DragEvent.ACTION_DROP:
                        // Perform an action when the drop happens
                        Toast.makeText(MainActivity.this, "Dropped!", Toast.LENGTH_SHORT).show();
                        dX = v.getX() - event.getX();
                        dY = v.getY() - event.getY();
                        imagen.setX(dX);
                        imagen.setY(dY);
                        v.setBackgroundColor(Color.BLUE);
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        // Reset the background color to default
                        v.setBackgroundColor(Color.DKGRAY);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
