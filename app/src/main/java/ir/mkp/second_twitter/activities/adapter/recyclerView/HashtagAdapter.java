package ir.mkp.second_twitter.activities.adapter.recyclerView;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.PostListActivity;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.ViewHolder> {
    List<String> list;

    public void setList(List<String> list){
        this.list=list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(R.layout.hashtag_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        String hashtag;
        TextView textView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.hashtag_item_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), PostListActivity.class);
                    intent.putExtra(PostListActivity.HashTag, hashtag);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        void bind(String hashtag){
            this.hashtag = hashtag;
            textView.setText(this.hashtag);
        }
    }
}


