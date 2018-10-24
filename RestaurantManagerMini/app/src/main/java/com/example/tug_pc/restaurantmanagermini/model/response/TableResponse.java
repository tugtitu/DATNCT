package com.example.tug_pc.restaurantmanagermini.model.response;

import com.example.tug_pc.restaurantmanagermini.model.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableResponse {
    @SerializedName("json_table")
    private List<Table> listTable;
    private boolean success;
    private String message;

    public TableResponse(List<Table> listTable, boolean success, String message) {
        this.listTable = listTable;
        this.success = success;
        this.message = message;
    }

    public List<Table> getListTable() {
        return listTable;
    }

    public void setListTable(List<Table> listTable) {
        this.listTable = listTable;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
