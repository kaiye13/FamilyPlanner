package the.family.planner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import the.family.planner.tabs.FridayFragment;
import the.family.planner.tabs.MondayFragment;
import the.family.planner.tabs.SaturdayFragment;
import the.family.planner.tabs.SundayFragment;
import the.family.planner.tabs.ThursdayFragment;
import the.family.planner.tabs.TuesdayFragment;
import the.family.planner.tabs.WedsdayFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);

        this.numOfTabs = numOfTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new MondayFragment();
            case 1:
                return new TuesdayFragment();

            case 2:
                return new WedsdayFragment();

            case 3:
                return new ThursdayFragment();

            case 4:
                return new FridayFragment();

            case 5:
                return new SaturdayFragment();

            case 6:
                return new SundayFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
