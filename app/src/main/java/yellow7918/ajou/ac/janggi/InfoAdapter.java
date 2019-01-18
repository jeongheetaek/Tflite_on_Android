package yellow7918.ajou.ac.janggi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {

    private List<Info> item;

    private OnItemClickListener<Info> onItemClickListener;

    public interface OnItemClickListener<Info> {
        void onItemClick(yellow7918.ajou.ac.janggi.Info item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener<Info> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InfoAdapter(List<Info> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_view, viewGroup, false);
        return new InfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder infoHolder, int i) {
        infoHolder.bindItem(item.get(i));
        infoHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item.get(i), i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    static class InfoHolder extends RecyclerView.ViewHolder {
        private TextView category;
        private TextView title;

        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.item_category);
            title = itemView.findViewById(R.id.item_title);
        }

        public void bindItem(Info info) {
            String[] titles = info.getTitle().split("\\|");
            category.setText(titles[0].trim());
            title.setText(titles[1].trim());
        }
    }
}
