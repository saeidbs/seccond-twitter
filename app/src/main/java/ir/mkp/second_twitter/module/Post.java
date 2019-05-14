package ir.mkp.second_twitter.module;

import java.util.ArrayList;
import java.util.List;

import ir.mkp.second_twitter.utility.Values;

public class Post extends Sender {
    List<String> hashtagList = new ArrayList<>();

    public Post(Long id) {
        Post samplepost = Values.dataManager.getPostDAO().getPost(id);
        this.setID(id);
        user = samplepost.getUser();
        text = samplepost.getText();
        hashtagList = Values.dataManager.getHashtagDAO().getHashtags(this);

        // DO: saeidbahmani 1/23/19 1:25 PM ()fill
    }

    public Post(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public Post(User user, String text, List<String> hashtagList) {
        this.user = user;
        this.text = text;
        this.hashtagList = hashtagList;
    }

    public List<String> getHashtagList() {
        return hashtagList;
    }
}
