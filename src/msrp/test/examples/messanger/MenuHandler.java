package msrp.test.examples.messanger;

import java.awt.event.*;

public class MenuHandler
    implements ActionListener, ItemListener {

  @Override
  public void actionPerformed(ActionEvent event) {

    String commandName = event.getActionCommand();
    if( commandName.compareToIgnoreCase("Log Out")==0){

    }
  }

  @Override
  public void itemStateChanged(ItemEvent event) {
  }

}
