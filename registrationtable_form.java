import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class RegistrationForm extends JFrame {
    private JTextField tfStudentId, tfName, tfMobile, tfDob, tfContact;
    private JTextArea taAddress;
    private JRadioButton rbMale, rbFemale;
    private JButton btnRegister, btnReset, btnExit;
    private JTable table;
    private DefaultTableModel tableModel;
    private DBHelper db;

    public RegistrationForm() {
        super("Registration Form");
        try {
            db = new DBHelper();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database init error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        setLayout(new BorderLayout(10,10));
        JPanel left = createInputPanel();
        JPanel right = createTablePanel();

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        refreshTable();
    }

    private JPanel createInputPanel() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(360, 0));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        p.add(new JLabel("ID:"));
        tfStudentId = new JTextField();
        p.add(tfStudentId);

        p.add(Box.createVerticalStrut(6));
        p.add(new JLabel("Name:"));
        tfName = new JTextField();
        p.add(tfName);

        p.add(Box.createVerticalStrut(6));
        p.add(new JLabel("Mobile:"));
        tfMobile = new JTextField();
        p.add(tfMobile);

        p.add(Box.createVerticalStrut(6));
        p.add(new JLabel("Gender:"));
        JPanel gp = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbMale); bg.add(rbFemale);
        gp.add(rbMale); gp.add(rbFemale);
        p.add(gp);

        p.add(Box.createVerticalStrut(6));
        p.add(new JLabel("DOB (yyyy-mm-dd):"));
        tfDob = new JTextField();
        p.add(tfDob);

        p.add(Box.createVerticalStrut(6));
        p.add(new JLabel("Address:"));
        taAddress = new JTextArea(4, 20);
        taAddress.setLineWrap(true);
        taAddress.setWrapStyleWord(true);
        p.add(new JScrollPane(taAddress));

        p.add(Box.createVerticalStrut(6));
        p.add(new JLabel("Contact (email/phone):"));
        tfContact = new JTextField();
        p.add(tfContact);

        p.add(Box.createVerticalStrut(10));
        btnRegister = new JButton("Register");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bp.add(btnRegister); bp.add(btnReset); bp.add(btnExit);
        p.add(bp);

        // Action Listeners
        btnRegister.addActionListener(e -> onRegister());
        btnReset.addActionListener(e -> resetFields());
        btnExit.addActionListener(e -> System.exit(0));

        return p;
    }

    private JPanel createTablePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Registered Users"));
        String[] cols = {"DB ID", "ID", "Name", "Gender", "Address", "Contact"};
        tableModel = new DefaultTableModel(cols, 0) {
            // make cells not editable
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    private void onRegister() {
        String studentId = tfStudentId.getText().trim();
        String name = tfName.getText().trim();
        String mobile = tfMobile.getText().trim();
        String gender = rbMale.isSelected() ? "Male" : rbFemale.isSelected() ? "Female" : "";
        String dob = tfDob.getText().trim();
        String address = taAddress.getText().trim();
        String contact = tfContact.getText().trim();

        // Basic validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }
        if (mobile.length() < 6) {
            // minimal mobile check
            int res = JOptionPane.showConfirmDialog(this, "Mobile looks short. Continue?", "Check mobile", JOptionPane.YES_NO_OPTION);
            if (res != JOptionPane.YES_OPTION) return;
        }

        try {
            db.insertRegistration(studentId, name, mobile, gender, dob, address, contact);
            JOptionPane.showMessageDialog(this, "Registered successfully.");
            resetFields();
            refreshTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void resetFields() {
        tfStudentId.setText("");
        tfName.setText("");
        tfMobile.setText("");
        tfDob.setText("");
        taAddress.setText("");
        tfContact.setText("");
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
    }

    private void refreshTable() {
        try {
            List<String[]> rows = db.fetchAll();
            tableModel.setRowCount(0);
            for (String[] r : rows) {
                tableModel.addRow(r);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading records: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // set look and feel to system default
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            RegistrationForm rf = new RegistrationForm();
            rf.setVisible(true);
        });
    }
}