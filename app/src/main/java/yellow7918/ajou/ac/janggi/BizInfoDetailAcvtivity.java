package yellow7918.ajou.ac.janggi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class BizInfoDetailAcvtivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bizinfo);

        Intent intent = getIntent();
        Info info = (Info) intent.getSerializableExtra("INFO");

        String[] titles = info.getTitle().split("\\|");


        TextView category = findViewById(R.id.detail_category2);
        category.setText(titles[0].trim());
        TextView title = findViewById(R.id.detail_title2);
        title.setText(titles[1].trim());

        TextView date = findViewById(R.id.detail_date2);
        date.setText(info.getDate());

        TextView writer = findViewById(R.id.detail_writer2);
        writer.setText(info.getWriter());

        TextView content = findViewById(R.id.detail_content2);
        content.setText(info.getContent());

        List<HashMap<String, String>> files = info.getFiles();

        RecyclerView recyclerView = findViewById(R.id.detail_recycler_view2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(new RecyclerView.Adapter<BizInfoDetailAcvtivity.File>() {
            @NonNull
            @Override
            public File onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_file, viewGroup, false);
                return new File(view);
            }


            @Override
            public void onBindViewHolder(@NonNull File viewHolder, int i) {
                viewHolder.bindItem(files.get(i));
            }

            @Override
            public int getItemCount() {
                return files.size();
            }
        });
    }

    static class File extends RecyclerView.ViewHolder {
        private TextView link;

        public File(@NonNull View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.file_name);
        }

        public void bindItem(HashMap<String, String> file) {
            link.setText(Html.fromHtml("<a href=" + file.get("path").trim() + ">" + file.get("name") + "</a>"));
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
