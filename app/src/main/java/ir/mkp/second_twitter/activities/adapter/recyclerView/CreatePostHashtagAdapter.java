package ir.mkp.second_twitter.activities.adapter.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.mkp.second_twitter.R;

public class CreatePostHashtagAdapter extends RecyclerView.Adapter<CreatePostHashtagAdapter.ViewHolder> {
    private List<String> hashTagList = new ArrayList<>();

    public void addHashtag(String text) {
        if (hashTagList.contains(text))
            return;

        hashTagList.add(text);
        this.notifyItemInserted(hashTagList.size());
    }

    public void removeHashtag(int index) {
        if (hashTagList.size() <= index)
            return;

        hashTagList.remove(index);
        this.notifyItemRemoved(index);
    }

    public List<String> getHashTagList(){
        return hashTagList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.hashtag_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.hashtag.setText(hashTagList.get(i));
    }

    @Override
    public int getItemCount() {
        return hashTagList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView hashtag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hashtag = itemView.findViewById(R.id.hashtag_item_text_view);
            hashtag.setOnLongClickListener(new deleteListener());
        }

        private class deleteListener implements View.OnLongClickListener {
            @Override
            public boolean onLongClick(View view) {
                removeHashtag(getAdapterPosition());
                return true;
            }
        }
    }
}
