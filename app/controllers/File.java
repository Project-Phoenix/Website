package controllers;

class File {
    public String filename;
    public String content;
    File(String filename, String content) {
        this.filename = filename;
        this.content = content;
    }
    public String getFilename() {
        return filename;
    }
    public String getContent() {
        return content;
    }
}
