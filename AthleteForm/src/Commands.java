import java.sql.*;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nash
 */
public class Commands {
  public static boolean existingUser(String username) {
    Connection con = null;
    con = Connect(con);
    String query = "SELECT username FROM registration WHERE username = ?";
    PreparedStatement pst;
    ResultSet rs;

    try {
      pst = con.prepareStatement(query);
      pst.setString(1, username);
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

  public static Connection Connect(Connection conn) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://localhost/itpf", "root", "");
      return conn;
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static void attachDeHighlightListener(JTextComponent attachTo) {

    attachTo.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        Commands.simpleDehighlightComponent(attachTo);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        Commands.simpleDehighlightComponent(attachTo);
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        Commands.simpleDehighlightComponent(attachTo);
      }
    });
  }

  public static void simpleHighlightComponent(Component component) {
    if (component instanceof JTextField || component instanceof JPasswordField || component instanceof JComboBox
        || component instanceof JRadioButton || component instanceof JPanel) {
      Border highlightBorder;

      highlightBorder = new LineBorder(Color.RED);

      if (component instanceof JTextField) {
        JTextField textField = (JTextField) component;
        textField.setBorder(highlightBorder);
      } else if (component instanceof JPasswordField) {
        JPasswordField passwordField = (JPasswordField) component;
        passwordField.setBorder(highlightBorder);
      } else if (component instanceof JComboBox) {
        JComboBox<?> comboBox = (JComboBox<?>) component;
        comboBox.setBorder(highlightBorder);
      } else if (component instanceof JRadioButton) {
        JRadioButton radioButton = (JRadioButton) component;
        radioButton.setBorder(highlightBorder);
      } else if (component instanceof JPanel) {
        JPanel pnl = (JPanel) component;
        pnl.setBorder(highlightBorder);
      }
    }

  }

  public static void simpleDehighlightComponent(Component component) {
    if (component instanceof JTextField || component instanceof JPasswordField || component instanceof JComboBox
        || component instanceof JRadioButton || component instanceof JPanel) {
      Border highlightBorder;

      highlightBorder = new LineBorder(Color.BLACK);

      if (component instanceof JTextField) {
        JTextField textField = (JTextField) component;
        textField.setBorder(highlightBorder);
      } else if (component instanceof JPasswordField) {
        JPasswordField passwordField = (JPasswordField) component;
        passwordField.setBorder(highlightBorder);
      } else if (component instanceof JComboBox) {
        JComboBox<?> comboBox = (JComboBox<?>) component;
        comboBox.setBorder(highlightBorder);
      } else if (component instanceof JRadioButton) {
        JRadioButton radioButton = (JRadioButton) component;
        radioButton.setBorder(highlightBorder);
      } else if (component instanceof JPanel) {
        JPanel pnl = (JPanel) component;
        pnl.setBorder(highlightBorder);
      }
    }

  }
}
