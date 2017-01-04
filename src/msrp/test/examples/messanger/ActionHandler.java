package msrp.test.examples.messanger;

//import java.awt.Event;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActionHandler
    implements ActionListener {
  @Override
  public void actionPerformed(ActionEvent event) {
    //Button Events
    Object obj = event.getSource();
    //ButtonEvent
    if (obj instanceof JButton) {
      if (event.getActionCommand().compareToIgnoreCase("Send") == 0) {
        /*
        if (MainWindow.chatRecvWindow != null) {
          MainWindow.chatRecvWindow.sendMessage();
        }
        else if (MainWindow.chatSendWindow != null) {
          MainWindow.chatSendWindow.sendMessage();
        }
        */
      }
    }
    //ComboBox Events
    if (obj instanceof JComboBox) {
      JComboBox cb = (JComboBox) obj;
      String item = (String) cb.getSelectedItem();
      item.trim();
      return;
    }
    //CheckBox Events
    if (obj instanceof JCheckBox) {
      JCheckBox chkBox = (JCheckBox) obj;
      if (chkBox.isEnabled()) {
        if (chkBox.getText().matches("Status")) {

        }
        else if (chkBox.getText().matches("Failure Report")) {

        }
        else if (chkBox.getText().matches("Login")) {
        }
      }
    }

  }
}
