package asimplemodule.tomateduino;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jimmy on 3/14/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    private String tabtitles[] = new String[] { "Dashboard", "Pour Water" };

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                DashboardFragmentTab fragmenttab1 = new DashboardFragmentTab();
                return fragmenttab1;

            // Open FragmentTab2.java
            case 1:
                PourWaterFragmentTab fragmenttab2 = new PourWaterFragmentTab();
                return fragmenttab2;
/*
            // Open FragmentTab3.java
            case 2:
                FragmentTab3 fragmenttab3 = new FragmentTab3();
                return fragmenttab3;*/
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
