package org.recorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;

public class Utils {
    private HttpRequest selectedRequest;
    private List<ParsedHttpParameter> parameterList;
    private List<Object[]> pasteList;

    public Utils() {
    }


    public Boolean isRecordExist(JTable table, Object[] record) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = model.getRowCount();
        int column = model.getColumnCount();
        Object[] ex_record = new Object[column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                ex_record[j] = model.getValueAt(i, j);
            }
            if (Arrays.equals(ex_record, record)) {
                return true;
            }
            ex_record = new Object[column];
        }
        return false;
    }


    public Boolean isRecordExist(List<Object[]> objectList, Object[] record) {
        for (Object[] object : objectList) {
            if (Arrays.equals(object, record)) {
                return true;
            }
        }
        return false;
    }


    public void AddData(JTable table, String name, String value, String position, String note) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[] {name, value, position, note, true, null, null});
    }


    public void AddData(JTable table, Object[] record) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(record);
    }


    public void Copy() {
        selectedRequest = ContextMenu.selected_request;
        parameterList = selectedRequest.parameters();
        int copy = 0;
        for (ParsedHttpParameter parameter : parameterList) {
            Object[] record;
            String name = parameter.name();
            String value = parameter.value();
            String position = switch (parameter.type()) {
                case URL -> "URL";
                case COOKIE -> "COOKIE";
                case BODY -> "BODY";
                case MULTIPART_ATTRIBUTE -> "MULTIPART";
                case JSON -> "JSON";
                case XML -> "XML";
                case XML_ATTRIBUTE -> "XML ATTRIBUTE";
            };

            record = new Object[] {name, value, position, "", true, null, null};
            if (!isRecordExist(GUI.tbRecord, record)) {
                AddData(GUI.tbRecord, record);
                copy = 1;
            }

            if (copy == 1) {
                break;
            }
        }
    }

    public void AutoCopy() {
        selectedRequest = ContextMenu.selected_request;
        parameterList = selectedRequest.parameters();
        for (ParsedHttpParameter parameter : parameterList) {
            Object[] record;
            String name = parameter.name();
            String value = parameter.value();
            String position = switch (parameter.type()) {
                case URL -> "URL";
                case COOKIE -> "COOKIE";
                case BODY -> "BODY";
                case MULTIPART_ATTRIBUTE -> "MULTIPART";
                case JSON -> "JSON";
                case XML -> "XML";
                case XML_ATTRIBUTE -> "XML ATTRIBUTE";
            };

            record = new Object[] {name, value, position, "", true, null, null};
            if (!isRecordExist(GUI.tbRecord, record)) {
                AddData(GUI.tbRecord, record);
            }
        }
    }

}
