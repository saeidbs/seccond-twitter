package ir.mkp.second_twitter.module;

public class Sender {
    protected User user;
    protected Long ID;
    protected String text;
    protected int likeCounter = 0;

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public int getLikeCounter() {
        return likeCounter;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
