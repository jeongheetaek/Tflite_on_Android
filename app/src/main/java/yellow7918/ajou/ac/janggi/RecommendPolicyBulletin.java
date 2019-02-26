package yellow7918.ajou.ac.janggi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecommendPolicyBulletin extends android.support.v4.app.Fragment {
    private FirebaseAuth firebaseAuth;
    private User u;

    private ProgressBar progressBar;
    private InfoAdapter infoAdapter;
    private List<Info> infoList;

    private String[] tag = {"정부정책", "신사업공고", "채용", "입찰정보", "보조금", "창업", "모집공모"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm_pager, container, false);

        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        infoList = new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        u = new User();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference().child("data").child("DEBC");

        DatabaseReference myRef = database.getReference().child("user").child(user.getEmail().replace(".", ""));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                u = dataSnapshot.getValue(User.class);
                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Map<String, Object> map = (Map<String, Object>) d.getValue();
                            if (map.containsKey("title")) {
                                Info info = new Info();
                                info.setContent((String) map.get("content"));
                                info.setDate((String) map.get("date"));
                                info.setId((String) map.get("id"));
                                info.setIs_file((Boolean) map.get("is_file"));
                                info.setTitle((String) map.get("title"));
                                info.setWriter((String) map.get("writer"));

                                if(!(info.haveKeyWord("용인") || info.haveKeyWord("시각")))
                                    continue;

                                else {
                                    List<HashMap<String, String>> file = (List<HashMap<String, String>>) map.get("files");
                                    info.setFiles(file);

                                    infoList.add(info);
                                }
                                /*
                                for (int i = 0; i < tag.length; i++) {
                                    if ((u.getTag().get(i) && info.haveKeyWord(tag[i])) || (u.getKeyword().length() > 0 && info.haveKeyWord(u.getKeyword()))) {
                                        List<HashMap<String, String>> file = (List<HashMap<String, String>>) map.get("files");
                                        info.setFiles(file);

                                        infoList.add(info);
                                        break;
                                    }
                                }*/
                            }
                        }

                        //infoList = infoList.subList(0, 6);

                        System.out.println(infoList.size());
                        infoAdapter = new InfoAdapter(infoList);
                        infoAdapter.setOnItemClickListener(new InfoAdapter.OnItemClickListener<Info>() {
                            @Override
                            public void onItemClick(Info item, int position) {
                                Intent i = new Intent(getContext(), PolicyBulletinDetailActivity.class);
                                i.putExtra("INFO", item);
                                startActivity(i);
                            }
                        });
                        recyclerView.setAdapter(infoAdapter);

                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }
}
