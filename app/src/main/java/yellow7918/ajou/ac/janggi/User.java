package yellow7918.ajou.ac.janggi;

import java.util.Arrays;
import java.util.List;

public class User {

    private String email;
    private String userName;
    private String comName;
    private String num;
    private String ceo;
    private String keyword;
    private List<Boolean> tag;

    public User() {
    }

    public User(String email, String userName, String comName, String num, String ceo, String keyword, List<Boolean> tag) {
        this.email = email;
        this.userName = userName;
        this.comName = comName;
        this.num = num;
        this.ceo = ceo;
        this.keyword = keyword;
        this.tag = tag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Boolean> getTag() {
        return tag;
    }

    public void setTag(List<Boolean> tag) {
        this.tag = tag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }
}
