package ir.mkp.second_twitter.activities.adapter.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.module.Reply;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    List<Reply> list;

    public void setList(List<Reply> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.comment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHlder, int i) {
        viewHlder.setReply(list.get(i));
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        Reply reply;
        TextView sender;
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.comment_item_sender_text_view);
            text = itemView.findViewById(R.id.comment_item_comment_text_view);
        }

        public void setReply (Reply reply) {
            this.reply = reply;
            sender.setText(reply.getUser().getUsername());
            text.setText(reply.getText());
        }
    }
}
