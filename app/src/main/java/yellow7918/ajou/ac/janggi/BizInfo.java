package yellow7918.ajou.ac.janggi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

public class BizInfo extends Fragment {
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_biz_info, container, false);

        progressBar = v.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        List<Info> infoList = new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view2);
        recyclerView.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("data").child("DEBC");

        myRef.addValueEventListener(new ValueEventListener() {
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

                        if(!(info.haveKeyWord("채용") || info.haveKeyWord("경영")))
                            continue;
                        else {

                            List<HashMap<String, String>> file = (List<HashMap<String, String>>) map.get("files");
                            info.setFiles(file);

                            infoList.add(info);
                        }
                    }
                }
                System.out.println(infoList.size());
                Collections.sort(infoList, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
                InfoAdapter infoAdapter = new InfoAdapter(infoList);
                infoAdapter.setOnItemClickListener(new InfoAdapter.OnItemClickListener<Info>() {
                    @Override
                    public void onItemClick(Info item, int position) {
                        Intent i = new Intent(getContext(), BizInfoDetailAcvtivity.class);
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

        return v;
    }
}
//비즈인포