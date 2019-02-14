package yellow7918.ajou.ac.janggi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        ImageView bizinfo = v.findViewById(R.id.image_bizinfo);
        MainActivity mainActivity = (MainActivity)getActivity();
        bizinfo.setContentDescription("정책공고정보");
        bizinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(new BizInfo());
            }
        });

        ImageView dream = v.findViewById(R.id.image_dream);
        dream.setContentDescription("장애인채용정보");
        dream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(new Dream365());
            }
        });

        ImageView policy = v.findViewById(R.id.image_policy);
        policy.setContentDescription("모집공고정보");
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(new PolicyBulletin());
            }
        });

        ImageView outlet = v.findViewById(R.id.image_outlet);
        outlet.setContentDescription("정부지원정보");
        outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(new OutletPolicy());
            }
        });

        return v;
    }
}
