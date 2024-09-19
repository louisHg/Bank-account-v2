package com.example.bank_account_project.utils.validable;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Data
public class Validable<O> {

    public Validable() {
        this.statusList=new ArrayList<>();
    }

    public Validable(O object) {
        this.object=object;
        this.statusList=new ArrayList<>();
    }

    public Validable(O object, List<Status> list) {
        this.object=object;
        this.statusList=list;
    }

    public Validable(O object, Status s) {
        this.object=object;
        this.statusList=new ArrayList<>();
        statusList.add(s);
    }

    public Validable(O object, String message, StatusType type) {
        this.object=object;
        this.statusList=new ArrayList<>();
        statusList.add(new Status(message, type));
    }

    private O object;
    private List<Status> statusList;

    public String statusListToString() {
        StringJoiner sj = new StringJoiner("###", "[", "]");
        statusList.stream().forEach(s -> sj.add(s.toString()));
        return sj.toString();
    }

}
