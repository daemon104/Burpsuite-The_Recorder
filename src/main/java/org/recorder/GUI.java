package org.recorder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JPanel rootPanel;
    public static JTable tbRecord;
    private JPanel buttonPanel;
    private JButton btnAutoCopy;
    private JButton btnCopy;
    private JButton btnAdd;
    private JButton btnRecover;
    private JButton btnClear;
    private GroupLayout gl_buttonPanel;
    private JPanel titlePanel;
    private JPanel tablePanel;
    private GroupLayout gl_tablePanel;
    private JLabel lbTitle;
    private JScrollPane tableScrollPanel;
    private final Utils utl = new Utils();
    public static List<Object[]> recoverList = new ArrayList<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GUI frame = new GUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public GUI() {
        JFrame parentFrame = this;

        // Set root frame attribute
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 812, 599);
        setLocationRelativeTo(null);

        // Root panel: have 4 children panel: table, table scroll, title, button
        rootPanel = new JPanel();
        rootPanel.setBorder(new LineBorder(UIManager.getColor("FormattedTextField.selectionBackground")));
        setContentPane(rootPanel);

        // Button panel contains 7 buttons (other 2 will be placed in the record table)
        buttonPanel = new JPanel();
        buttonPanel.setBorder(new LineBorder(UIManager.getColor("FormattedTextField.selectionBackground")));

        // Auto copy button
        btnAutoCopy = new JButton("Auto Copy");
        btnAutoCopy.setForeground(new Color(0, 128, 192));
        btnAutoCopy.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        btnAutoCopy.addActionListener(actionEvent -> utl.AutoCopy());

        // Copy button
        btnCopy = new JButton("Copy");
        btnCopy.setForeground(new Color(0, 128, 192));
        btnCopy.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        btnCopy.addActionListener(actionEvent -> utl.Copy());

        // Add button
        btnAdd = new JButton("Add");
        btnAdd.setForeground(new Color(0, 128, 192));
        btnAdd.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        btnAdd.addActionListener(actionEvent -> {
            InputForm form = new InputForm(parentFrame, true);
            form.setVisible(true);
        });

        // Recover button
        btnRecover = new JButton("Recover");
        btnRecover.setForeground(new Color(0, 128, 192));
        btnRecover.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        btnRecover.addActionListener(actionEvent -> {
            if (!recoverList.isEmpty()) {
                int lastElement = recoverList.size() - 1;
                Object[] record = recoverList.get(lastElement);
                if (!utl.isRecordExist(tbRecord, record)) {
                    utl.AddData(tbRecord, record);
                }
                recoverList.remove(lastElement);
            }
        });

        // Clear button
        btnClear = new JButton("Clear");
        btnClear.setForeground(new Color(0, 128, 192));
        btnClear.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        btnClear.addActionListener(actionEvent -> {
            DefaultTableModel model = (DefaultTableModel) tbRecord.getModel();

            if (model.getRowCount() != 0) {
                int rowCount = model.getRowCount();
                int columnCount = model.getColumnCount();

                for (int row = 0; row < rowCount; row++) {
                    Object[] record = new Object[columnCount];
                    for (int column = 0; column < columnCount; column++) {
                        record[column] = model.getValueAt(row, column);
                    }

                    if (!utl.isRecordExist(recoverList, record)) {
                        recoverList.add(record);
                    }
                }

                model.setRowCount(0);
            }
        });

        gl_buttonPanel = new GroupLayout(buttonPanel);
        gl_buttonPanel.setHorizontalGroup(
                gl_buttonPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_buttonPanel.createSequentialGroup()
                                .addComponent(btnAutoCopy, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                .addComponent(btnCopy, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                .addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                .addComponent(btnRecover, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                .addComponent(btnClear, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                .addGap(1))
        );
        gl_buttonPanel.setVerticalGroup(
                gl_buttonPanel.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnAutoCopy, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(btnCopy, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(btnRecover, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(btnClear, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        buttonPanel.setLayout(gl_buttonPanel);

        // Title panel
        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(0, 1, 0, 0));

        // Record table where to store records
        tbRecord = new JTable();
        tbRecord.setAutoCreateRowSorter(true);
        tbRecord.setRowHeight(24);
        tbRecord.setColumnSelectionAllowed(true);
        tbRecord.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));

        // Table scroll panel (a scrollable table)
        tableScrollPanel = new JScrollPane();
        tableScrollPanel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        tableScrollPanel.setViewportView(tbRecord);

        // Set column, row for record table
        // Set table model (7 columns)
        tbRecord.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Parameter", "Value", "Position", "Note", "", "", ""}
        ));

        // Set up a row sorter
        DefaultTableModel model = (DefaultTableModel) tbRecord.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        tbRecord.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        // Table panel
        tablePanel = new JPanel();
        gl_tablePanel = new GroupLayout(tablePanel);
        gl_tablePanel.setHorizontalGroup(
                gl_tablePanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_tablePanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tableScrollPanel, GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_tablePanel.setVerticalGroup(
                gl_tablePanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_tablePanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tableScrollPanel, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                                .addContainerGap())
        );
        tablePanel.setLayout(gl_tablePanel);

        // Title label for extension title
        lbTitle = new JLabel("Recorder");
        lbTitle.setForeground(new Color(255, 128, 64));
        lbTitle.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitle.setFont(new Font("Segoe UI Symbol", Font.BOLD, 22));
        titlePanel.add(lbTitle);

        // Add sample data to table
        utl.AddData(tbRecord, "Username", "user1", "BODY", "username on system");
        utl.AddData(tbRecord, "Password", "pass123", "BODY", "password of username");

        // Create 2 buttons: delete, duplicate and 1 checkbox for record table
        tbRecord.getColumnModel().getColumn(4).setCellRenderer(tbRecord.getDefaultRenderer(Boolean.class));
        tbRecord.getColumnModel().getColumn(4).setCellEditor(tbRecord.getDefaultEditor(Boolean.class));
        tbRecord.getColumnModel().getColumn(5).setCellRenderer(new DeleteButtonRenderer());
        tbRecord.getColumnModel().getColumn(5).setCellEditor(new DeleteButtonEditor(new JCheckBox()));
        tbRecord.getColumnModel().getColumn(6).setCellRenderer(new DuplicateButtonRenderer());
        tbRecord.getColumnModel().getColumn(6).setCellEditor(new DuplicateButtonEditor(new JCheckBox()));

        // Set frame layout
        setLayout(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(getWidth(), 60));
        titlePanel.setPreferredSize(new Dimension(getWidth(), 75));
        add(titlePanel, BorderLayout.PAGE_START);
        add(buttonPanel, BorderLayout.PAGE_END);
        add(tablePanel, BorderLayout.CENTER);
    }
}

// Delete button renderer (render button in table panel)
class DeleteButtonRenderer extends JButton implements TableCellRenderer {
    public JButton btnDelete;

    public DeleteButtonRenderer() {
        setOpaque(true);
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, final int row, final int column) {
        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(255, 255, 255));
        btnDelete.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
        return btnDelete;
    }
}


// Duplicate button renderer
class DuplicateButtonRenderer extends JButton implements TableCellRenderer {
    public JButton btnDuplicate;

    public DuplicateButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object o, boolean isSelected, boolean hasFocus, final int row, final int column) {
        btnDuplicate = new JButton("Duplicate");
        btnDuplicate.setBackground(new Color(255, 255, 255));
        btnDuplicate.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
        return btnDuplicate;
    }
}


// Delete button editor (behaviour of the button when clicked)
class DeleteButtonEditor extends DefaultCellEditor {
    public JButton btnDelete;
    Utils utl = new Utils();
    private boolean isClicked = false;
    private int row = 0;
    private JTable table;
    public DeleteButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        btnDelete = new JButton("Delete");
        btnDelete.setOpaque(true);
        btnDelete.setHorizontalAlignment(SwingConstants.CENTER);
        btnDelete.addActionListener(actionEvent -> fireEditingStopped());
    }


    public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, final int column) {
        // If the button is clicked, change it foreground and background to look like it is clicked
        if (isSelected) {
            btnDelete.setForeground(table.getSelectionForeground());
            btnDelete.setBackground(table.getSelectionBackground());
        } else {
            btnDelete.setForeground(table.getForeground());
            btnDelete.setBackground(table.getBackground());
        }

        this.row = row;
        this.table = table;
        isClicked = true;
        return btnDelete;
    }


    @Override
    public Object getCellEditorValue() {
        if (isClicked) {
            int modelRow = table.convertRowIndexToModel(row);
            if (modelRow != -1) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int columnCount = model.getColumnCount();
                Object[] record = new Object[columnCount];
                for (int index = 0; index < columnCount; index++) {
                    record[index] = model.getValueAt(modelRow, index);
                }

                if (!utl.isRecordExist(GUI.recoverList, record)) {
                    GUI.recoverList.add(record);
                }
                model.removeRow(modelRow);
            }
        }
        isClicked = false;
        return btnDelete;
    }


    @Override
    public boolean stopCellEditing() {
        isClicked = false;
        return super.stopCellEditing();
    }


    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}


// Duplicate button editor
class DuplicateButtonEditor extends DefaultCellEditor {
    public JButton btnDuplicate;
    Utils utl = new Utils();
    private boolean isClicked = false;
    private int row = 0;
    private JTable table;

    public DuplicateButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        btnDuplicate = new JButton("Duplicate");
        btnDuplicate.setOpaque(true);
        btnDuplicate.setHorizontalAlignment(SwingConstants.CENTER);
        btnDuplicate.addActionListener(actionEvent -> fireEditingStopped());
    }


    public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, final int column) {
        // If the button is clicked, change it foreground and background to look like it is clicked
        if (isSelected) {
            btnDuplicate.setForeground(table.getSelectionForeground());
            btnDuplicate.setBackground(table.getSelectionBackground());
        } else {
            btnDuplicate.setForeground(table.getForeground());
            btnDuplicate.setBackground(table.getBackground());
        }

        this.row = row;
        this.table = table;
        isClicked = true;
        return btnDuplicate;
    }


    @Override
    public Object getCellEditorValue() {
        if (isClicked) {
            int modelRow = table.convertRowIndexToModel(row);
            if (modelRow != -1) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int columnCount = model.getColumnCount();
                Object[] record = new Object[columnCount];
                for (int index = 0; index < columnCount; index++) {
                    record[index] = model.getValueAt(modelRow, index);
                }
                utl.AddData(table, record);
            }
        }
        isClicked = false;
        return btnDuplicate;
    }


    @Override
    public boolean stopCellEditing() {
        isClicked = false;
        return super.stopCellEditing();
    }


    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

