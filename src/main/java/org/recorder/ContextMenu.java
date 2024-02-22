package org.recorder;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.params.HttpParameterType;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ContextMenu implements ContextMenuItemsProvider{
    private final MontoyaApi api;
    private final GUI gui;
    private final Utils ult = new Utils();
    public static HttpRequest selected_request;

    public ContextMenu(MontoyaApi api, GUI gui){
        this.api = api;
        this.gui = gui;
    }

    public List<Component> provideMenuItems(ContextMenuEvent event) {
        if (event.isFromTool(ToolType.REPEATER, ToolType.PROXY)) {
            Logging log = api.logging();
            List<Component> menuList = new ArrayList<>();
            JMenuItem viewRecorder = new JMenuItem("View Recorder");
            JMenuItem cpHeaderParameter = new JMenuItem("Get header's parameters");
            JMenuItem cpBodyParameter = new JMenuItem("Get body's parameters");

            // Get the selected request for other functions
            if (event.messageEditorRequestResponse().isPresent()) {
                selected_request = event.messageEditorRequestResponse().get().requestResponse().request();
            }
            else {
                selected_request = event.selectedRequestResponses().get(0).request();
            }

            // View recorder option
            viewRecorder.addActionListener(actionEvent -> {
                log.logToOutput("Action: View recorder selected");
                gui.setVisible(true);
            });

            // Copy request header parameters option
            cpHeaderParameter.addActionListener(actionEvent -> {
                log.logToOutput("Action: Get header's parameters selected");

                List<ParsedHttpParameter> param_list;
                param_list = selected_request.parameters();
                for (ParsedHttpParameter param : param_list) {
                    if (param.type() == HttpParameterType.URL || param.type() == HttpParameterType.COOKIE) {
                        Object[] record;
                        String name = param.name();
                        String value = param.value();

                        String position = switch (param.type()) {
                            case URL -> "URL";
                            case COOKIE -> "COOKIE";
                            default -> "";
                        };

                        record = new Object[] {name, value, position, "", true, null, null};
                        if (!ult.isRecordExist(GUI.tbRecord, record)) {
                            ult.AddData(GUI.tbRecord, record);
                        }
                    }
                }
                gui.setVisible(true);
            });

            // Copy request body parameters option
            cpBodyParameter.addActionListener(actionEvent -> {
                log.logToOutput("Action: Get header's parameters selected");

                List<ParsedHttpParameter> param_list;
                param_list = selected_request.parameters();

                for (ParsedHttpParameter param : param_list) {
                    if (param.type() != HttpParameterType.URL || param.type() != HttpParameterType.COOKIE) {
                        Object[] record;
                        String name = param.name();
                        String value = param.value();

                        String position = switch (param.type()) {
                            case JSON -> "JSON";
                            case BODY -> "BODY";
                            case MULTIPART_ATTRIBUTE -> "MULTIPART";
                            case XML -> "XML";
                            case XML_ATTRIBUTE -> "XML ATTRIBUTE";
                            default -> "";
                        };

                        record = new Object[] {name, value, position, "", true, null, null};
                        if (!ult.isRecordExist(GUI.tbRecord, record)) {
                            ult.AddData(GUI.tbRecord, record);
                        }
                    }
                }
                gui.setVisible(true);
            });

            menuList.add(viewRecorder);
            menuList.add(cpHeaderParameter);
            menuList.add(cpBodyParameter);

            return menuList;
        }
        return null;
    }
}
