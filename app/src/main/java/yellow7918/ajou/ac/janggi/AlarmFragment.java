package yellow7918.ajou.ac.janggi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        tabLayout = v.findViewById(R.id.tab);
        viewPager = v.findViewById(R.id.view_pager);
        initViewPager();

        return v;
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new AlarmPolicyBulletin());
        fragmentList.add(new AlarmBizinfo());
        fragmentList.add(new AlarmDream365());
        fragmentList.add(new AlarmOutletPolicy());

        AlarmPagerAdapter adapter = new AlarmPagerAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);


        tabLayout.addTab(tabLayout.newTab().setText("정책공고"));
        tabLayout.addTab(tabLayout.newTab().setText("장애인채용"));
        tabLayout.addTab(tabLayout.newTab().setText("모집공고"));
        tabLayout.addTab(tabLayout.newTab().setText("정부지원"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
