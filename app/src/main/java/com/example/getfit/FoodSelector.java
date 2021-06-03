package com.example.getfit;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
public class FoodSelector extends Activity  {
        android.widget.ViewFlipper viewFlipper;
       private Button next;
        private Button previous;
        @Override
        protected void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            viewFlipper = (android.widget.ViewFlipper)findViewById(R.id.viewFlipper);
            next = (Button) findViewById(R.id.next);
            previous = (Button) findViewById(R.id.previous);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewFlipper.showNext();
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 viewFlipper.showPrevious();

                }
            });
        }

        }



