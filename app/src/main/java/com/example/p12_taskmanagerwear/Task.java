package com.example.p12_taskmanagerwear;

import java.io.Serializable;

public class Task implements Serializable{
    private int id;
    private String taskName;
    private String desc;

    public Task(int id, String taskName, String desc) {
        this.id = id;
        this.taskName = taskName;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return id + " " + taskName + '\n' +
                desc;
    }
}
