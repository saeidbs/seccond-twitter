package ir.mkp.second_twitter.activities.adapter.recyclerView;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.CreateCommentActivity;
import ir.mkp.second_twitter.activities.PostActivity;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.utility.Values;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<Post> list;

    public void setList (List<Post> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.post_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        enum State{
            Like,
            UnLike;

            public static State getState(boolean isLike){
                if (isLike)
                    return Like;
                return UnLike;
            }

            public int getImage(){
                if (this == Like)
                    return R.drawable.like_fill_red_24dp;
                return R.drawable.like_stork_gray_24dp;
            }
        }

        Post post;
        TextView post_tv;
        TextView sender;
        TextView likeCounter;
        ImageView like;
        ImageView comment;
        State state;
        RecyclerView hashtags;
        HashtagAdapter adapter = new HashtagAdapter();

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            post_tv = itemView.findViewById(R.id.post_item_post_text_view);
            sender = itemView.findViewById(R.id.post_item_sender_text_view);
            like = itemView.findViewById(R.id.post_item_like_image_view);
            likeCounter = itemView.findViewById(R.id.post_item_like_count_text_view);
            comment = itemView.findViewById(R.id.post_item_comment_image_view);
            hashtags = itemView.findViewById(R.id.post_item_hash_tag_rv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Values.tempPost = post;
                    Intent intent = new Intent(itemView.getContext(), PostActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (state){
                        case Like:
                            Values.dataManager.getPostLikeDAO().delete(Values.loginUser, post);
                            state = State.UnLike;
                            break;
                        case UnLike:
                            Values.dataManager.getPostLikeDAO().add(Values.loginUser, post);
                            state = State.Like;
                            break;
                    }

                    like.setImageResource(state.getImage());
                    likeCounter.setText(Values.dataManager.getPostLikeDAO().likeCounter(post) + "");
                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Values.tempPost = post;
                    Intent intent = new Intent(itemView.getContext(), CreateCommentActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

            hashtags.setAdapter(adapter);
            hashtags.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL, false));
        }

        public void bind(Post post){
            this.post = post;
            post_tv.setText(post.getText());
            sender.setText(post.getUser().getUsername());
            state = State.getState(Values.dataManager.getPostLikeDAO().isLike(Values.loginUser, this.post));
            like.setImageResource(state.getImage());
            likeCounter.setText(Values.dataManager.getPostLikeDAO().likeCounter(post) + "");
            adapter.setList(post.getHashtagList());

            if (post.getUser().getId() == Values.loginUser.getId())
                like.setEnabled(false);
        }
    }
}
