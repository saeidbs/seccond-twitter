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
import ir.mkp.second_twitter.activities.BaseActivity;
import ir.mkp.second_twitter.activities.BioActivity;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> list;

    public void setList(List<User> list){
        this.list=list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.search_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setUser(list.get(i));
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        User user;
        TextView textView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.search_item_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), BioActivity.class);
                    intent.putExtra(BaseActivity.USER_ID, user.getId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void setUser(User user){
            this.user = user;
            this.textView.setText(user.getUsername());
        }
    }

}
