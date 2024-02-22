package org.recorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class InputForm extends JDialog {
    private JPanel contentPane;
    private JLabel lbParam;
    private JTextField tfParam;
    private JLabel lbValue;
    private JTextField tfValue;
    private JLabel lbPosition;
    private JTextField tfPosition;
    private JLabel lbNote;
    private JTextField tfNote;
    private JButton btnClearRecord;
    private JButton btnAddRecord;
    private final Utils utl = new Utils();


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InputForm frame = new InputForm(new JFrame(), true);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public InputForm(JFrame owner, boolean modal) {
        super(owner, modal);
        InputForm inputForm = this;

        // Set root frame attribute
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Input Form");
        setBounds(100, 100, 646, 274);
        setLocationRelativeTo(owner);

        // Content panel (root panel)
        contentPane = new JPanel();
        contentPane.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Labels for param, value, position and note
        lbParam = new JLabel("Parameter:");
        lbParam.setHorizontalAlignment(SwingConstants.TRAILING);
        lbParam.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));

        lbValue = new JLabel("Value:");
        lbValue.setHorizontalAlignment(SwingConstants.TRAILING);
        lbValue.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));

        lbPosition = new JLabel("Position:");
        lbPosition.setHorizontalAlignment(SwingConstants.TRAILING);
        lbPosition.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));

        lbNote = new JLabel("Note:");
        lbNote.setHorizontalAlignment(SwingConstants.TRAILING);
        lbNote.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));

        // Text fields for param, value, position and note
        tfParam = new JTextField();
        tfParam.setColumns(10);

        tfValue = new JTextField();
        tfValue.setColumns(10);

        tfPosition = new JTextField();
        tfPosition.setColumns(10);

        tfNote = new JTextField();
        tfNote.setColumns(10);

        // Button add and clear text field
        btnAddRecord = new JButton("Add");
        btnAddRecord.setForeground(new Color(0, 128, 192));
        btnAddRecord.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        btnAddRecord.addActionListener(actionEvent -> {
            Object[] record = new Object[] {tfParam.getText(), tfValue.getText(), tfPosition.getText(), tfNote.getText(), true, null, null};
            if (!utl.isRecordExist(GUI.tbRecord, record)) {
                utl.AddData(GUI.tbRecord, record);
                inputForm.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(inputForm, "ERROR: Record already existed!!", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnClearRecord = new JButton("Clear");
        btnClearRecord.setForeground(new Color(0, 128, 192));
        btnClearRecord.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        btnClearRecord.addActionListener(event -> {
            tfParam.setText("");
            tfNote.setText("");
            tfValue.setText("");
            tfPosition.setText("");
        });

        // Set layout for buttons, labels and text fields
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lbParam, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lbValue, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lbPosition, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lbNote, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
                                                .addGap(10)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(tfNote, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                                                        .addComponent(tfPosition, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                                                        .addComponent(tfValue, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                                                        .addComponent(tfParam, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(193)
                                                .addComponent(btnAddRecord, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                .addGap(44)
                                                .addComponent(btnClearRecord, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lbParam)
                                        .addComponent(tfParam, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lbValue, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfValue, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lbPosition, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfPosition, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lbNote, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfNote, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnAddRecord, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnClearRecord, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }
}
