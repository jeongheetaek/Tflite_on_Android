package yellow7918.ajou.ac.janggi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingFragment extends Fragment {
    private FirebaseAuth firebaseAuth;

    private User u;
    private ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        progress = v.findViewById(R.id.progress);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        u = new User();

        TextView email = v.findViewById(R.id.email);
        TextView name = v.findViewById(R.id.name);
        TextView com_name = v.findViewById(R.id.com_name);
        TextView com_num = v.findViewById(R.id.com_num);
        TextView ceo_name = v.findViewById(R.id.ceo_name);
        TextView keyword = v.findViewById(R.id.keyword);

        CheckBox tag1 = v.findViewById(R.id.tag1);
        CheckBox tag2 = v.findViewById(R.id.tag2);
        CheckBox tag3 = v.findViewById(R.id.tag3);
        CheckBox tag4 = v.findViewById(R.id.tag4);
        CheckBox tag5 = v.findViewById(R.id.tag5);
        CheckBox tag6 = v.findViewById(R.id.tag6);
        CheckBox tag7 = v.findViewById(R.id.tag7);
        CheckBox tag8 = v.findViewById(R.id.tag8);
        CheckBox tag9 = v.findViewById(R.id.tag9);

        ImageView edit = v.findViewById(R.id.edit_button);

        CheckBox[] checkBoxes = {tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9};

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("user").child(user.getEmail().replace(".", ""));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                u = dataSnapshot.getValue(User.class);
                email.setText(u.getEmail());
                name.setText(u.getUserName());
                com_name.setText(u.getComName());
                com_num.setText(u.getNum());
                ceo_name.setText(u.getCeo());
                keyword.setText(u.getKeyword());
                for (int i = 0; i < checkBoxes.length; i++) {
                    checkBoxes[i].setChecked(u.getTag().get(i));
                    checkBoxes[i].setEnabled(false);
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Boolean> tags = u.getTag();
                boolean[] tag = new boolean[tags.size()];

                for (int i = 0; i < tags.size(); i++)
                    tag[i] = tags.get(i);

                Intent i = new Intent(getActivity(), SignUpActivity2.class);
                i.putExtra("tag", tag);
                i.putExtra("keyword", u.getKeyword());
                i.putExtra("edit", true);
                i.putExtra("email",u.getEmail());
                startActivity(i);
            }
        });

        Button logout = v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return v;
    }
}
