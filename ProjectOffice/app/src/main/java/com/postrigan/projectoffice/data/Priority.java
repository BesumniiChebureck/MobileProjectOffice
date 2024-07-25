package com.postrigan.projectoffice.data;



public class Priority {
    private final int _id;
    private String name;

    public Priority(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
