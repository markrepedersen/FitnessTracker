package markrepedersen.fitnesstrackerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by mark on 16-09-11.
 */
public class SettingsFragment extends PreferenceFragment {
    //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
