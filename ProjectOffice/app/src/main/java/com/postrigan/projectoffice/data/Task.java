package com.postrigan.projectoffice.data;

import java.time.LocalDate;

public class Task {
    private final int _id;
    private int priorityId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int employeeId;
    private int statusId;

    public Task(int _id, int priorityId, String name,
                LocalDate startDate, LocalDate endDate,
                int employeeId, int statusId) {
        this._id = _id;
        this.priorityId = priorityId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employeeId = employeeId;
        this.statusId = statusId;
    }

    public int get_id() {
        return _id;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {

        if(startDate == null)
            return LocalDate.of(2090, 1, 1);

        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        if(endDate == null)
            return LocalDate.of(2090, 1, 1);

        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
