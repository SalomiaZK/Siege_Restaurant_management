package com.example.local.Enity;

import java.time.LocalDateTime;


public class StatusWithDate {
    private int status;
    private LocalDateTime statusBeginingDate;

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getStatusBeginingDate() {
        return statusBeginingDate;
    }

    public void setStatusBeginingDate(LocalDateTime statusBeginingDate) {
        this.statusBeginingDate = statusBeginingDate;
    }

    public StatusWithDate() {
    }

    public StatusWithDate(int status, LocalDateTime statusBeginingDate) {
        this.status = status;
        this.statusBeginingDate = statusBeginingDate;
    }

    public Status getStatus() {
        switch (status){
            case 1:
                return Status.CREATED;
            case 2 :
                return Status.VALIDATED;
            case 3:
                return Status.IN_PREPARATION;
            case 4:
                return Status.FINISHED;
            case 5:
                return Status.SERVED;
            default:
                throw  new RuntimeException("not a number status");
        }
    }

    public int getIntStatus(){
        return status;
    }




    public int getStatusInt(Status status){
        switch (status){
            case CREATED:
                return 1;
            case VALIDATED:
                return 2;
            case IN_PREPARATION:
                return 3;
            case FINISHED:
                return 4;
            case SERVED:
                return  5;
            default:
                throw  new RuntimeException("not a number status");
        }
    }


}


