package markrepedersen.fitnesstrackerapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mark on 16-09-06.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    FitnessLogFragment fitnessFrag;
    MacronutrientFragment macroFrag;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

        fitnessFrag = new FitnessLogFragment();
        macroFrag = new MacronutrientFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return fitnessFrag;
            case 1:
                return macroFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
