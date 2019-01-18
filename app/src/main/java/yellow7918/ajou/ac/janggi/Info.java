package yellow7918.ajou.ac.janggi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Info implements Serializable {
    private String content;
    private String date;
    private String id;
    private boolean is_file;
    private String title;
    private String writer;
    private List<HashMap<String, String>> files;

    public Info() {
    }

    public Info(String content, String date, String id, boolean is_file, String title, String writer, List<HashMap<String, String>> files) {
        this.content = content;
        this.date = date;
        this.id = id;
        this.is_file = is_file;
        this.title = title;
        this.writer = writer;
        this.files = files;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIs_file() {
        return is_file;
    }

    public void setIs_file(boolean is_file) {
        this.is_file = is_file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public List<HashMap<String, String>> getFiles() {
        return files;
    }

    public void setFiles(List<HashMap<String, String>> files) {
        this.files = files;
    }

    public boolean haveKeyWord(String tag) {
        return content.contains(tag) || title.contains(tag);
    }

    static class File {
        private String name;
        private String path;

        public File(String name, String path) {
            this.name = name;
            this.path = path;
        }
    }
}
