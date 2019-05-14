package ir.mkp.second_twitter.activities.adapter.recyclerView;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.CommentActivity;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.utility.Values;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    List<Comment> list;

    public void setList(List<Comment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.comment_item, viewGroup, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHlder, int i) {
        viewHlder.setComment(list.get(i));
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        Comment comment;
        TextView sender;
        TextView text;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Values.tempComment = comment;
                    Intent intent = new Intent(itemView.getContext(), CommentActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
            sender = itemView.findViewById(R.id.comment_item_sender_text_view);
            text = itemView.findViewById(R.id.comment_item_comment_text_view);
        }

        public void setComment(Comment comment) {
            this.comment = comment;
            sender.setText(comment.getUser().getUsername());
            text.setText(comment.getText());
        }
    }
}
