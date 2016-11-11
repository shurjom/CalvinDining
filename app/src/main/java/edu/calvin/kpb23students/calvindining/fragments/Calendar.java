package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

import edu.calvin.kpb23students.calvindining.R;



/**
 * <p>
 *     This fragment shows the calendar
 * </p>
 */
public class Calendar extends Fragment  {
    public Calendar() {
        // Required empty public constructor
    }

    public class FoodCount {

        public int myCount;

        public FoodCount(int value){this.myCount = value;}

        void changeCount(int newCount){this.myCount = newCount;}

        int getCount(){
            return this.myCount;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }}

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState){

    }
        View v = getView();
        Button configureButton = (Button) v.findViewById(R.id.foodButton);
        Button eatButton = (Button)v.findViewById(R.id.consumeButton);
        Button undoButton = (Button)v.findViewById(R.id.undoButton);


        configureButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        EditText foodCount = (EditText) findViewById(R.id.countText);
                        int value = Integer.parseInt(foodCount.getText().toString());
                        blockPlan.changeCount(value);
                        Toast toast = Toast.makeText(getApplicationContext(),"Block count set to "+value,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
        );

        eatButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        mealCount = blockPlan.getCount();
                        foodPD++;
                        mealCount--;
                        if(mealCount < 0){
                            foodPD--;
                            mealCount = 0;
                            Toast toast = Toast.makeText(getApplicationContext(),"Cannot put in anymore meals. Please add more blocks.",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        blockPlan.changeCount(mealCount);
                        TextView foodPDCount = (TextView) findViewById(R.id.foodPDView);
                        EditText foodCount = (EditText) findViewById(R.id.countText);
                        foodPDCount.setText(Integer.toString(foodPD));
                        foodCount.setText(Integer.toString(mealCount));
                    }
                }
        );

        undoButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        mealCount = blockPlan.getCount();
                        foodPD--;
                        mealCount++;
                        if(foodPD < 0){
                            foodPD++;
                            mealCount--;
                            Toast toast = Toast.makeText(getApplicationContext(),"Nothing to undo.",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        blockPlan.changeCount(mealCount);
                        TextView foodPDCount = (TextView) findViewById(R.id.foodPDView);
                        EditText foodCount = (EditText) findViewById(R.id.countText);
                        foodPDCount.setText(Integer.toString(foodPD));
                        foodCount.setText(Integer.toString(mealCount));
                    }
                }
        );
}*/

