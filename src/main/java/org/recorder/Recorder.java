package org.recorder;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.BurpExtension;
import burp.api.montoya.extension.ExtensionUnloadingHandler;

public class Recorder implements BurpExtension {
    public static MontoyaApi publicAPI;
    public void initialize(MontoyaApi api) {
        // Public Montoya API for public uses
        publicAPI = api;

        // Set extension basic attribute
        api.extension().setName("Recorder");
        api.logging().logToOutput("Recorder: An extension for copy, audit, take note on http request parameter");

        // Main application's GUI & Context menu
        GUI gui = new GUI();

        // Register ContextMenu (3 options)
        api.userInterface().registerContextMenuItemsProvider(new ContextMenu(api, gui));

        // Register an unload handler
        ExtensionUnloadingHandler unloadHandler = gui::dispose;
        api.extension().registerUnloadingHandler(unloadHandler);
    }
}