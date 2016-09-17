package markrepedersen.fitnesstrackerapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mark on 16-09-06.
 */
public class FitnessLogFragment extends Fragment {
    public static String[] food = new String[]{
            "Select a food",
            "Apple - Large", "Apple - Medium", "Apple - Small",
            "Banana - Large", "CHEERIOS CEREAL - 1 OZ", "CHEDDAR CHEESE, SHREDDED - 1 CUP",
            "Apple Pie - 1 Pie", "Apple Pie - 1 Piece", "Applesauce, Canned, Sweetened - 1 Cup",
            "BEEF GRAVY, CANNED - 1 CUP", "Applesauce, Canned, Unsweetened - 1 Cup", "APRICOTS, RAW - 3 APRICOTS",
            "BEEF HEART, BRAISED - 3 OZ", "AVOCADOS, CALIFORNIA - 1 AVOCADO", "BAGELS, PLAIN - 1 BAGEL",
            "BEEF LIVER, FRIED - 3 OZ", "BAKING POWDER, LOW SODIUM - 1 TSP", "BARBECUE SAUCE - 1 TBSP",
            "BEEF NOODLE SOUP, CANNED - 1 CUP", "BEAN SPROUTS, MUNG, RAW - 1 CUP", "LUCKY CHARMS CEREAL - 1 oz",
            "BEER, LIGHT - 12 FL OZ", "BEER, REGULAR - 12 FL OZ", "BLUEBERRIES, FROZEN, SWEETENED - 1 CUP",
            "BLUEBERRIES, RAW - 1 CUP"
    };

    public static int[] foodCalories = new int[]{
            0,
            130, 72, 55,
            105, 110, 455,
            2420, 405, 195,
            125, 105, 50,
            150, 305, 200,
            185, 5, 10,
            85, 30, 110,
            95, 150, 185,
            80};
    ImageButton addFoodButton, getInfoButton;
    TextView totalCalsText;
    LinearLayout spinner_family;
    View rootView;
    HashMap<String, Integer> foodMap;
    int totalCals = 0;      //amount of calories consumed in the day
    int numSpinners;        //number of spinners
    List<Spinner> spinners; //a list of all dynamically created spinners to be restored on state change
    Spinner initSpinner;    //Initial created spinner

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fitness_log, container, false);
        addFoodButton = (ImageButton) rootView.findViewById(R.id.add_food);
        totalCalsText = (TextView) rootView.findViewById(R.id.total_cals);
        spinner_family = (LinearLayout) rootView.findViewById(R.id.spinner_family);
        getInfoButton = (ImageButton) rootView.findViewById(R.id.info);
        spinners = new ArrayList<>();
        foodMap = new HashMap<>();

        for (int i = 0; i < foodCalories.length; i++) {
            foodMap.put(food[i], foodCalories[i]);
        }
        initSpinner = duplicateSpinner();
        spinners.add(initSpinner);
        spinner_family.addView(initSpinner);
        spinner_family.addView(addDivisor());

        //if fragment is being reloaded, get spinner's previous selection
        //above spinner will be the first in spinner list
        if (savedInstanceState != null) {
            initSpinner.setSelection(savedInstanceState.getInt("Spinner0"));
        }

        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = duplicateSpinner();
                spinner_family.addView(spinner);
                spinners.add(spinner);
                numSpinners++;
                spinner_family.addView(addDivisor());
            }
        });

        getInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Your BMR (Basal Metabolic Rate) is the amount of energy expended while at rest.");
                AlertDialog msg = builder.create();
                msg.show();
            }
        });
        return rootView;
    }

    public Spinner duplicateSpinner() {
        final Spinner newSpinner = new Spinner(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(), R.layout.support_simple_spinner_dropdown_item, food);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        newSpinner.setAdapter(adapter);
        newSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            int lastSelectIndex = newSpinner.getSelectedItemPosition();

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getSelectedItem();
                if (position != lastSelectIndex) {
                    totalCals -= foodMap.get(newSpinner.getItemAtPosition(lastSelectIndex));
                }
                lastSelectIndex = position;
                totalCals += foodMap.get(item);
                String text = "\n" + String.valueOf(totalCals) + " calories today";
                totalCalsText.setText(text);
                totalCalsText.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return newSpinner;
    }

    //Adds a line below each spinner
    public View addDivisor() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1);
        View view = new View(getContext());
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(Color.parseColor("#000000"));
        return view;
    }

    //Saves all dynamically created spinners and their selections on state change
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            //loop starts at i = 1 since first spinner had already been reloaded
            for (int i = 1; i < numSpinners; i++) {
                if (spinners.isEmpty()) break;
                Spinner spinner = spinners.get(i);
                outState.putInt("Spinner" + i, spinner.getSelectedItemPosition());
            }
            outState.putInt("numSpinners", numSpinners);
            outState.putInt("totalCals", totalCals);
        }
    }

    //Restores dynamically created spinners and selections
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            for (int i = 0; i < savedInstanceState.getInt("numSpinners"); i++) {
                Spinner spinner = duplicateSpinner();
                spinner_family.addView(spinner);
                spinner.setSelection(savedInstanceState.getInt("Spinner" + i));
                spinner_family.addView(addDivisor());
            }
            String text = "\n" + String.valueOf(savedInstanceState.getInt("totalCals")) + " calories today";
            totalCalsText.setText(text);
        }
    }


}
