
import java.sql.*;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.*;
import java.util.Date;
import java.util.Calendar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Nash,Jet
 */
public class RegForm extends javax.swing.JFrame {

    static HashMap<String, String> monthMap = new HashMap<>();
    static int[] spaceCount;
    static int yearNow;
    static int monthNow;
    static int dateNow;
    String[] dateYearSetup;

    /**
     * Creates new form RegForm
     */
    public RegForm() {
        // setting up date checkers
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        yearNow = cal.get(Calendar.YEAR) + 1;
        monthNow = cal.get(Calendar.MONTH) + 1;
        dateNow = cal.get(Calendar.DAY_OF_MONTH) + 1;

        // setup months for changing days
        RegForm.setUpMonth();
        dateYearSetup = new String[2010 - 1990];

        int y = 0;
        for (int x = 1990; x < 2010; x++) {
            dateYearSetup[y] = Integer.toString(x);
            y++;
        }
        initComponents();

        // turning off highlight on change
        Commands.attachDeHighlightListener(txtFname);
        Commands.attachDeHighlightListener(txtLname);
        Commands.attachDeHighlightListener(txtEmail);
        Commands.attachDeHighlightListener(txtContact);
        Commands.attachDeHighlightListener(txtAddress);
        Commands.attachDeHighlightListener(txtPassword);
        Commands.attachDeHighlightListener(txtCPW);
        Commands.attachDeHighlightListener(txtGfname);
        Commands.attachDeHighlightListener(txtGcontact);
        Commands.attachDeHighlightListener(txtGemail);
        Commands.attachDeHighlightListener(txtUname);

        setResizable(false);
        setLocationRelativeTo(null);
        updateDay();
    }

    static Connection con;

    private static void setUpMonth() {
        monthMap.put("January", "01");
        monthMap.put("February", "02");
        monthMap.put("March", "03");
        monthMap.put("April", "04");
        monthMap.put("May", "05");
        monthMap.put("June", "06");
        monthMap.put("July", "07");
        monthMap.put("August", "08");
        monthMap.put("September", "09");
        monthMap.put("October", "10");
        monthMap.put("November", "11");
        monthMap.put("December", "12");
    }

    public static boolean existingEmail(String email) {
        String query = "SELECT email FROM registration WHERE email = ?";
        PreparedStatement pst;
        ResultSet rs;

        try {
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.isBeforeFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return false;
    }

    public static boolean validEmail(String email) {
        int atIndex = email.indexOf('@');
        int periodIndex = email.lastIndexOf('.');
        int space = email.indexOf(' ');

        if (atIndex == -1 || periodIndex == -1 || space != -1) {
            return false;
        } else if (atIndex > periodIndex) {
            return false;
        } else if (periodIndex == (email.length() - 1)) {
            return false;
        } else if (periodIndex == (atIndex + 1)) {
            return false;
        }
        return true;

    }

    private String GenderSelect() {
        if (radioFemale.isSelected()) {
            return "Female";
        } else if (radioMale.isSelected()) {
            return "Male";
        } else {
            return "";
        }
    }

    private void updateDay() {
        String dob = comboDobMonth.getSelectedItem().toString();
        String doby = comboDobYear.getSelectedItem().toString();
        if (dob.equals("January") || dob.equals("March") || dob.equals("May") || dob.equals("July")
                || dob.equals("August") || dob.equals("October") || dob.equals("December")) {
            comboDobDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6",
                    "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                    "24", "25", "26", "27", "28", "29", "30", "31" }));
        } else if (dob.equals("February")) {
            if (Integer.parseInt(doby) % 4 == 0) {
                if (Integer.parseInt(doby) % 100 == 0) {
                    if (Integer.parseInt(doby) % 400 == 0) {
                        comboDobDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4",
                                "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }));
                    } else {
                        comboDobDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4",
                                "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                                "20", "21", "22", "23", "24", "25", "26", "27", "28" }));
                    }

                } else {
                    comboDobDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5",
                            "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
                            "22", "23", "24", "25", "26", "27", "28", "29" }));
                }

            } else {
                comboDobDay.setModel(new javax.swing.DefaultComboBoxModel<>(
                        new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28" }));
            }

        } else {
            comboDobDay.setModel(new javax.swing.DefaultComboBoxModel<>(
                    new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                            "30" }));
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLname = new javax.swing.JTextField();
        txtContact = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUname = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboDobMonth = new javax.swing.JComboBox<>();
        comboDobYear = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblSex = new javax.swing.JLabel();
        txtGfname = new javax.swing.JTextField();
        txtGcontact = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtGemail = new javax.swing.JTextField();
        lblSport = new javax.swing.JLabel();
        comboYear = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        comboDobDay = new javax.swing.JComboBox<>();
        switchLogin = new javax.swing.JButton();
        comboCourse = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JPasswordField();
        txtCPW = new javax.swing.JPasswordField();
        txtUsernameError = new javax.swing.JLabel();
        comboSport = new javax.swing.JComboBox<>();
        comboCampus = new javax.swing.JComboBox<>();
        pnlGender = new javax.swing.JPanel();
        radioMale = new javax.swing.JRadioButton();
        radioFemale = new javax.swing.JRadioButton();
        txtError = new javax.swing.JLabel();
        txtGEmailError = new javax.swing.JLabel();
        txtEmailError = new javax.swing.JLabel();
        txtGContactError = new javax.swing.JLabel();
        txtPasswordError = new javax.swing.JLabel();
        txtContactError = new javax.swing.JLabel();
        txtDobError = new javax.swing.JLabel();
        txtContactError2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(204, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 35)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ATHLETE PORTAL");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SIGN UP");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FIRST NAME");

        txtFname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtFname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFnameActionPerformed(evt);
            }
        });
        txtFname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFnameKeyTyped(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("LAST  NAME");

        txtLname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtLname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLnameActionPerformed(evt);
            }
        });
        txtLname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLnameKeyTyped(evt);
            }
        });

        txtContact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContactActionPerformed(evt);
            }
        });
        txtContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContactKeyTyped(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CONTACT");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("EMAIL");

        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("USERNAME");

        txtUname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtUname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnameActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("ADDRESS");

        txtAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAddressKeyTyped(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("DATE OF BIRTH");

        comboDobMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October", "November ", "Decmber" }));
        comboDobMonth.setBorder(null);
        comboDobMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDobMonthActionPerformed(evt);
            }
        });

        comboDobYear.setModel(new javax.swing.DefaultComboBoxModel<>(dateYearSetup));
        comboDobYear.setBorder(null);
        comboDobYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDobYearActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(204, 0, 0));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("GUARDIAN INFORMATION");
        jLabel11.setOpaque(true);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("FULL NAME");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("CONTACT NO.");

        lblSex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSex.setText("SEX");

        txtGfname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGfnameActionPerformed(evt);
            }
        });
        txtGfname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGfnameKeyTyped(evt);
            }
        });

        txtGcontact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGcontact.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }

            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtGcontactInputMethodTextChanged(evt);
            }
        });
        txtGcontact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGcontactActionPerformed(evt);
            }
        });
        txtGcontact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGcontactKeyPressed(evt);
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGcontactKeyTyped(evt);
            }
        });

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("EMAIL");

        txtGemail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGemailActionPerformed(evt);
            }
        });

        lblSport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSport.setText("TYPES OF SPORT");

        comboYear.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "1ST YEAR", "2ND YEAR", "3RD YEAR", "4TH YEAR" }));
        comboYear.setBorder(null);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("COURSE");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("CAMPUS");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("PASSWORD");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("CONFIRM PASSWORD");

        btnRegister.setBackground(new java.awt.Color(204, 0, 0));
        btnRegister.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("REGISTER");
        btnRegister.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegister.setOpaque(true);
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        comboDobDay.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboDobDay.setBorder(null);

        switchLogin.setText("Login Instead");
        switchLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchLoginActionPerformed(evt);
            }
        });

        comboCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "COA",
                "COArch",
                "CAS",
                "COC",
                "COEd",
                "COE",
                "CIT",
                "CICT",
                "CMBT",
                "CON",
                "CPADM",
                "LHS"
        }));
        comboCourse.setBorder(null);
        comboCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCourseActionPerformed(evt);
            }
        });

        txtPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        txtCPW.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtUsernameError.setForeground(new java.awt.Color(255, 0, 0));
        txtUsernameError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboSport
                .setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Athletics", "Badminton", "Basketball",
                        "Futsal", "Sepak Takraw", "Soccer", "Softball", "Swimming", "Table Tennis", "Volleyball" }));
        comboSport.setBorder(null);

        comboCampus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "General Tinio Street Campus",
                "Sumacab Campus",
                "San Isidro Campus",
                "Gabaldon Campus",
                "Atate Campus",
                "Fort Magsaysay Campus",
                "Off Campuses"
        }));
        comboCampus.setBorder(null);

        pnlGender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(214, 217, 223)));

        radioMale.setText("MALE");
        radioMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMaleActionPerformed(evt);
            }
        });

        radioFemale.setText("FEMALE");
        radioFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFemaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGenderLayout = new javax.swing.GroupLayout(pnlGender);
        pnlGender.setLayout(pnlGenderLayout);
        pnlGenderLayout.setHorizontalGroup(
                pnlGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGenderLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(radioMale)
                                .addGap(18, 18, 18)
                                .addComponent(radioFemale)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pnlGenderLayout.setVerticalGroup(
                pnlGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlGenderLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        pnlGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(radioMale)
                                                .addComponent(radioFemale))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        txtError.setForeground(new java.awt.Color(255, 0, 0));
        txtError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtGEmailError.setForeground(new java.awt.Color(255, 0, 0));
        txtGEmailError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtEmailError.setForeground(new java.awt.Color(255, 0, 0));
        txtEmailError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtGContactError.setForeground(new java.awt.Color(255, 0, 0));
        txtGContactError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtPasswordError.setForeground(new java.awt.Color(255, 0, 0));
        txtPasswordError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtContactError.setForeground(new java.awt.Color(255, 0, 0));
        txtContactError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtDobError.setForeground(new java.awt.Color(255, 0, 0));
        txtDobError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtContactError2.setForeground(new java.awt.Color(255, 0, 0));
        txtContactError2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 260,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 260,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel9)
                                                .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 260,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel6)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(txtContactError,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addComponent(txtUname, javax.swing.GroupLayout.PREFERRED_SIZE, 260,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel8)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(txtUsernameError,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 260,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel7)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtEmailError,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 189,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 260,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5)
                                                .addComponent(pnlGender, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtError, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                        .createSequentialGroup()
                                                        .addComponent(comboDobDay,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(comboDobMonth,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(comboDobYear,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtDobError,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(lblSex)
                                                .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26,
                                        Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup()
                                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(96, 96, 96))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(comboCampus,
                                                                javax.swing.GroupLayout.Alignment.LEADING, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(comboSport, 0,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(34, 34, 34))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel20)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel13)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtGContactError,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel19)
                                                                .addGap(22, 22, 22)
                                                                .addComponent(txtPasswordError,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE))
                                                        .addComponent(txtGfname,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtGcontact,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout
                                                                .createSequentialGroup()
                                                                .addComponent(jLabel15)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(txtGEmailError,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 189,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(txtGemail,
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtPassword)
                                                        .addComponent(txtCPW)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout
                                                                .createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(lblSport,
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel17,
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel18,
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel12,
                                                                                javax.swing.GroupLayout.Alignment.LEADING))
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(comboCourse,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        196,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(comboYear,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(switchLogin))))
                                                .addGap(34, 34, 34))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(238, 238, 238)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(txtContactError2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(324, 324, 324))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26,
                                        Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSport)
                                        .addComponent(jLabel4))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(comboSport, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboCampus, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel17)
                                                .addComponent(jLabel7))
                                        .addComponent(txtEmailError, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboCourse, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel19)
                                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtPasswordError, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUsernameError, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtUname, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel20))
                                        .addComponent(txtContactError, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCPW, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup()
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(txtGfname,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel13))
                                                        .addComponent(txtGContactError,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(txtGcontact,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel15))
                                                        .addComponent(txtGEmailError,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtGemail, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel10)
                                                        .addComponent(txtDobError,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(comboDobMonth,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(comboDobYear,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(comboDobDay,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblSex)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pnlGender, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(switchLogin)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(txtError, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap(371, Short.MAX_VALUE)
                                        .addComponent(txtContactError2, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(275, 275, 275))));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtGfnameKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtGfnameKeyTyped
        if (txtGfname.getText().length() != 0) {
            char c = evt.getKeyChar();
            if (c == ' ' && txtGfname.getText().charAt(txtGfname.getText().length() - 1) == ' ') {
                evt.consume();
            }
        }
    }// GEN-LAST:event_txtGfnameKeyTyped

    private void txtAddressKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtAddressKeyTyped
        if (txtAddress.getText().length() != 0) {
            char c = evt.getKeyChar();
            if (c == ' ' && txtAddress.getText().charAt(txtAddress.getText().length() - 1) == ' ') {
                evt.consume();
            }
        }
    }// GEN-LAST:event_txtAddressKeyTyped

    private void txtLnameKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtLnameKeyTyped
        if (txtLname.getText().length() != 0) {
            char c = evt.getKeyChar();
            if (c == ' ' && txtLname.getText().charAt(txtLname.getText().length() - 1) == ' ') {
                evt.consume();
            }
        }
    }// GEN-LAST:event_txtLnameKeyTyped

    private void txtFnameKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtFnameKeyTyped
        if (txtFname.getText().length() != 0) {
            char c = evt.getKeyChar();
            if (c == ' ' && txtFname.getText().charAt(txtFname.getText().length() - 1) == ' ') {
                evt.consume();
            }
        }
    }// GEN-LAST:event_txtFnameKeyTyped

    private void txtContactKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtContactKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c))) {
            evt.consume();
        } else if (txtContact.getText().length() == 11) {
            evt.consume();
        }
    }// GEN-LAST:event_txtContactKeyTyped

    private void txtGcontactKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtGcontactKeyTyped
        char c = evt.getKeyChar();
        if ((Character.isLetter(c))) {
            evt.consume();
        } else if (txtGcontact.getText().length() == 11) {
            evt.consume();
        }
    }// GEN-LAST:event_txtGcontactKeyTyped

    private void txtGcontactInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {// GEN-FIRST:event_txtGcontactInputMethodTextChanged

    }// GEN-LAST:event_txtGcontactInputMethodTextChanged

    private void txtGcontactKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtGcontactKeyPressed
    }// GEN-LAST:event_txtGcontactKeyPressed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtPasswordActionPerformed

    private void comboCourseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_comboCourseActionPerformed
        if (comboCourse.getSelectedItem().toString() == "COArch") {
            comboYear.setModel(new javax.swing.DefaultComboBoxModel<>(
                    new String[] { "1ST YEAR", "2ND YEAR", "3RD YEAR", "4TH YEAR", "5TH YEAR" }));
        } else if (comboCourse.getSelectedItem().toString() == "LHS") {
            comboYear.setModel(new javax.swing.DefaultComboBoxModel<>(
                    new String[] { "JHS", "SHS" }));
        } else {
            comboYear.setModel(new javax.swing.DefaultComboBoxModel<>(
                    new String[] { "1ST YEAR", "2ND YEAR", "3RD YEAR", "4TH YEAR" }));
        }
    }// GEN-LAST:event_comboCourseActionPerformed

    private void comboDobYearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_comboDobYearActionPerformed
        updateDay();
    }// GEN-LAST:event_comboDobYearActionPerformed

    private void radioFemaleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_radioFemaleActionPerformed
        // TODO add your handling code here:
        if (radioFemale.isSelected()) {
            radioMale.setSelected(false);
        }
        pnlGender.setBorder(null);
    }// GEN-LAST:event_radioFemaleActionPerformed

    private void txtFnameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtFnameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtFnameActionPerformed

    private void txtLnameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtLnameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtLnameActionPerformed

    private void txtContactActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtContactActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtContactActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtEmailActionPerformed

    private void txtUnameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtUnameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtUnameActionPerformed

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtAddressActionPerformed

    private void comboDobMonthActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_comboDobMonthActionPerformed
        updateDay();
    }// GEN-LAST:event_comboDobMonthActionPerformed

    private void radioMaleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_radioMaleActionPerformed
        if (radioMale.isSelected()) {
            radioFemale.setSelected(false);
        }

    }// GEN-LAST:event_radioMaleActionPerformed

    private void txtGfnameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtGfnameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtGfnameActionPerformed

    private void txtGcontactActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtGcontactActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtGcontactActionPerformed

    private void txtGemailActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtGemailActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtGemailActionPerformed

    private void txtTypesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTypesActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtTypesActionPerformed

    private void txtCourseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtCourseActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtCourseActionPerformed

    private void txtCampusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtCampusActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtCampusActionPerformed

    private void txtCPWActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtCPWActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtCPWActionPerformed

    private void txtYearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtYearActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtYearActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRegisterActionPerformed
        con = Commands.Connect(con);
        boolean errTrigger = false;
        txtError.setText("");
        txtEmailError.setText("");
        txtGEmailError.setText("");
        txtContactError.setText("");
        txtGContactError.setText("");
        txtPasswordError.setText("");
        txtUsernameError.setText("");
        txtDobError.setText("");

        String firstname = txtFname.getText();
        String lastname = txtLname.getText();
        String email = txtEmail.getText();
        String username = txtUname.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String dob = comboDobYear.getSelectedItem().toString() + "-"
                + monthMap.get(comboDobMonth.getSelectedItem().toString()) + "-"
                + comboDobDay.getSelectedItem().toString();
        int yearCheck = Integer.parseInt(comboDobYear.getSelectedItem().toString());
        int monthCheck = Integer.parseInt(monthMap.get(comboDobMonth.getSelectedItem().toString()));
        int dateCheck = Integer.parseInt(comboDobDay.getSelectedItem().toString());
        java.sql.Date date = java.sql.Date.valueOf(dob);
        String gender = GenderSelect();
        String sport = comboSport.getSelectedItem().toString();
        String campus = comboCampus.getSelectedItem().toString();
        String course = comboCourse.getSelectedItem().toString();
        int year = comboYear.getSelectedIndex() + 1;

        char[] password = txtPassword.getPassword();
        String passwords = new String(password);

        char[] cpassword = txtCPW.getPassword();
        String cpasswords = new String(cpassword);

        String guardian_firstname = txtGfname.getText();
        String guardian_contact = txtGcontact.getText();
        String guardian_email = txtGemail.getText();

        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() ||
                username.isBlank() || contact.isBlank() || address.isBlank() ||
                dob.isBlank() || gender.isBlank() || sport.isBlank() || campus.isBlank() ||
                passwords.isBlank() || cpasswords.isBlank() ||
                guardian_firstname.isBlank() || guardian_contact.isBlank() || guardian_email.isBlank()) {

            if (firstname.isBlank())
                Commands.simpleHighlightComponent(txtFname);
            if (lastname.isBlank())
                Commands.simpleHighlightComponent(txtLname);
            if (email.isBlank())
                Commands.simpleHighlightComponent(txtEmail);
            if (username.isBlank())
                Commands.simpleHighlightComponent(txtUname);
            if (contact.isBlank())
                Commands.simpleHighlightComponent(txtContact);
            if (address.isBlank())
                Commands.simpleHighlightComponent(txtAddress);
            if (gender.isBlank()) {
                System.out.println("this runs");
                Commands.simpleHighlightComponent(pnlGender);
            }
            if (comboDobYear.getSelectedItem().toString().isBlank())
                Commands.simpleHighlightComponent(comboDobYear);
            if (comboDobMonth.getSelectedItem().toString().isBlank())
                Commands.simpleHighlightComponent(comboDobMonth);
            if (comboDobDay.getSelectedItem().toString().isBlank())
                Commands.simpleHighlightComponent(comboDobDay);
            if (comboSport.getSelectedItem().toString().isBlank())
                Commands.simpleHighlightComponent(comboDobDay);
            if (comboCampus.getSelectedItem().toString().isBlank())
                Commands.simpleHighlightComponent(comboDobDay);
            if (passwords.isBlank())
                Commands.simpleHighlightComponent(txtPassword);
            if (cpasswords.isBlank())
                Commands.simpleHighlightComponent(txtCPW);
            if (guardian_firstname.isBlank())
                Commands.simpleHighlightComponent(txtGfname);
            if (guardian_contact.isBlank())
                Commands.simpleHighlightComponent(txtGcontact);
            if (guardian_email.isBlank())
                Commands.simpleHighlightComponent(txtGemail);
            txtError.setText("Empty Field/s");
            errTrigger = true;
        }
        if (!passwords.equals(cpasswords)) {
            Commands.simpleHighlightComponent(txtPassword);
            Commands.simpleHighlightComponent(txtCPW);
            txtPasswordError.setText("Non Matching Passwords!");
            errTrigger = true;
        }
        if (contact.length() != 11) {
            txtContactError.setText("Must Use 11 Digit Format!");
            errTrigger = true;
        }
        if (guardian_contact.length() != 11) {
            txtGContactError.setText("Must Use 11 Digit Format!");
            errTrigger = true;
        }
        if (yearCheck > yearNow) {
            txtDobError.setText("Future Date!");
            errTrigger = true;
        } else if (yearCheck == yearNow) {
            if (monthCheck > monthNow) {
                txtDobError.setText("Future Date!");
                errTrigger = true;
            } else if (monthCheck == monthNow) {
                if (dateCheck > dateNow) {
                    txtDobError.setText("Future Date!");
                    errTrigger = true;
                }
            }
        }
        if (passwords.length() < 8) {
            txtPasswordError.setText("Minimum 8 characters");
            errTrigger = true;
        }
        if (!validEmail(email)) {
            Commands.simpleHighlightComponent(txtEmail);
            txtEmailError.setText("Invalid Email Format");
            errTrigger = true;
        }
        if (!validEmail(guardian_email)) {
            Commands.simpleHighlightComponent(txtGemail);
            txtGEmailError.setText("Invalid Email Format");
            errTrigger = true;
        }
        if (Commands.existingUser(username)) {
            Commands.simpleHighlightComponent(txtUname);
            txtUsernameError.setText("Username Already Exists!");
            errTrigger = true;
        }
        if (existingEmail(email)) {
            Commands.simpleHighlightComponent(txtEmail);
            txtEmailError.setText("Email Already Exists!");
            errTrigger = true;
        }

        if (!errTrigger) {
            String encryptedPassword = BCrypt.hashpw(new String(password), BCrypt.gensalt());
            String query = "INSERT INTO REGISTRATION (first_name, last_name, date_of_birth, email, username, contact, address, gender, type_of_sport, course, year, parent_guardian_name, parent_guardian_contact, parent_guardian_email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pst;
            ResultSet rs;

            try {
                pst = con.prepareStatement(query);

                pst.setString(1, firstname);
                pst.setString(2, lastname);
                pst.setDate(3, date);
                pst.setString(4, email);
                pst.setString(5, username);
                pst.setString(6, contact);
                pst.setString(7, address);
                pst.setString(8, gender);
                pst.setString(9, sport);
                pst.setString(10, course);
                pst.setInt(11, year);
                pst.setString(12, guardian_firstname);
                pst.setString(13, guardian_contact);
                pst.setString(14, guardian_email);
                pst.setString(15, encryptedPassword);

                pst.executeUpdate();
            } catch (SQLException err) {
                err.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Registered Successfuly");
            new LogForm().setVisible(true);
            this.dispose();
            // txtUname.setText("");
            // txtFname.setText("");
            // txtLname.setText("");
            // txtEmail.setText("");
            // txtContact.setText("");
            // txtAddress.setText("");
            // radioMale.setSelected(false);
            // radioFemale.setSelected(false);
            // comboSport.setSelectedIndex(0);
            // comboCampus.setSelectedIndex(0);
            // comboCourse.setSelectedIndex(0);
            // comboYear.setSelectedIndex(0);
            // txtPassword.setText("");
            // txtCPW.setText("");
            // txtGfname.setText("");
            // txtGcontact.setText("");
            // txtGemail.setText("");
        }

        // Continue with the rest of the code if validation passes
        // ...

        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }// GEN-LAST:event_btnRegisterActionPerformed

    private void switchLoginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_switchLoginActionPerformed
        new LogForm().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_switchLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegister;
    private javax.swing.JComboBox<String> comboCampus;
    private javax.swing.JComboBox<String> comboCourse;
    private javax.swing.JComboBox<String> comboDobDay;
    private javax.swing.JComboBox<String> comboDobMonth;
    private javax.swing.JComboBox<String> comboDobYear;
    private javax.swing.JComboBox<String> comboSport;
    private javax.swing.JComboBox<String> comboYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblSex;
    private javax.swing.JLabel lblSport;
    private javax.swing.JPanel pnlGender;
    private javax.swing.JRadioButton radioFemale;
    private javax.swing.JRadioButton radioMale;
    private javax.swing.JButton switchLogin;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JPasswordField txtCPW;
    private javax.swing.JTextField txtContact;
    private javax.swing.JLabel txtContactError;
    private javax.swing.JLabel txtContactError2;
    private javax.swing.JLabel txtDobError;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JLabel txtEmailError;
    private javax.swing.JLabel txtError;
    private javax.swing.JTextField txtFname;
    private javax.swing.JLabel txtGContactError;
    private javax.swing.JLabel txtGEmailError;
    private javax.swing.JTextField txtGcontact;
    private javax.swing.JTextField txtGemail;
    private javax.swing.JTextField txtGfname;
    private javax.swing.JTextField txtLname;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JLabel txtPasswordError;
    private javax.swing.JTextField txtUname;
    private javax.swing.JLabel txtUsernameError;
    // End of variables declaration//GEN-END:variables
}
