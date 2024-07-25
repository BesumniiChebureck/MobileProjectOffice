package com.postrigan.projectoffice.data;

import java.time.LocalDate;

public class Bid {
    private final int _id;
    private int taskId;
    private LocalDate bidDate;
    private int employeeId;

    public Bid(int _id, int taskId, LocalDate bidDate, int employeeId) {

        this._id = _id;
        this.taskId = taskId;
        this.bidDate = bidDate;
        this.employeeId = employeeId;
    }

    public int get_id() {
        return _id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public LocalDate getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDate bidDate) {
        this.bidDate = bidDate;
    }

    public int getEmployeeId(){
        return employeeId;
    }

    public void setEmployeeId(int employeeId){
        this.employeeId = employeeId;
    }
}
