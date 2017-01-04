package msrp.test.examples.messanger;

import msrp.stack.MsrpStack;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.net.*;
import java.io.File;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 
 * @author inam_
 */

public class AppletWindow  extends JApplet implements WindowListener{


  /*GUI Stuff */
  JToolBar toolBar = null;
  JMenuBar menuBar = null;
  JMenu menu = null;
  JTextField usernameField = null;
  JPasswordField passwordField = null;
  JButton btnConnect = null;
  JLabel picture = null;
  JPanel labelPane = null;
  JPanel displayPicturePane;
  JPanel groupPane;
  JPanel inputPane;
  JPanel buttonPane;
  JPanel statusPane;
  JComboBox userStatus;
  JCheckBox saveUserCheck;
  JCheckBox savePasswordCheck;
  JCheckBox saveAutoLoginCheck;
  String displayPicPath = "";
  JFrame LoginWindow;
  JPanel menuPane;
  ActionListener signInListener = null;
  ActionListener comboListener = null;
  ActionListener picButtonListener = null;
  ActionListener addContactListener = null;
  static boolean sessionEstablished = false;
  String signInText = "Sign In..";
  static String remoteAddress = "";
  /*Logged In Window*/
  JComboBox cmb;
  public static ChatWindow chatWindow = null;
  /*SIP Stuff*/
  static UAC uac = null;
  static UAS uas = null;
  public boolean isSipSessionCreated = false;
  boolean sessionClosed = false;
  /* MSRP Stuff */
  MsrpListner msrpListener = null;



  public void init(){

    //LoginWindow.addWindowListener(this);
    LoginWindow = new JFrame("Live Help Desk");
    menuPane = new JPanel(new FlowLayout());

    LoginWindow.getContentPane().setLayout(new FlowLayout());
    Dimension dim = new Dimension(280,500);
    LoginWindow.setSize(dim);
    LoginWindow.setLocation(new Point(600, 80));
    //setMenuCtrl();
    //Load Picture
    displayPicPath = getCurrentDirectory() + "/images/display.gif";
    LoadPicture(displayPicPath);
    //Init InputBoxes
    usernameField = new JTextField(15);
    passwordField = new JPasswordField();
    //Create Username and Password Panel
    inputPane = new JPanel();
    inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.Y_AXIS));
    inputPane.add(new JLabel("Enter Username:"));
    inputPane.add(usernameField);
    inputPane.add(new JLabel("Enter Password:"));
    inputPane.add(passwordField);

    //Add User Status Pane
    String[] statusValues = {
        "  Online  ", "  Offline  ", "  Busy  ", "  Appear Offline  ",
        "  Be Right Back  ", "  Away  ",
        "  Out to Lunch  "};
    userStatus = new JComboBox(statusValues);
    userStatus.addActionListener(new ActionHandler());
    JLabel lblStatus = new JLabel("Status:    ");
    statusPane = new JPanel(new FlowLayout());
    statusPane.add(lblStatus);
    statusPane.add(userStatus);

    //Add Connect Now button
    btnConnect = new JButton(signInText);
    signInListener = new ButtonEventHandler() {
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareToIgnoreCase(signInText) == 0) {
          loadLoggedInFrame();
        }
      }
    };
    //btnConnect.addActionListener(new ActionHandler());
    btnConnect.addActionListener(signInListener);
    buttonPane = new JPanel(new FlowLayout());
    buttonPane.add(btnConnect);
    //Add Save User & Password Pane
    saveUserCheck = new JCheckBox("Remember my ID");
    savePasswordCheck = new JCheckBox("Remember my Password");
    saveAutoLoginCheck = new JCheckBox(
        "Login me automatically              ");
    //Action Listeners
    saveUserCheck.addActionListener(new ActionHandler());
    savePasswordCheck.addActionListener(new ActionHandler());
    saveAutoLoginCheck.addActionListener(new ActionHandler());

    groupPane = new JPanel();
    groupPane.setLayout(new BoxLayout(groupPane, BoxLayout.Y_AXIS));
    groupPane.add(saveUserCheck);
    groupPane.add(savePasswordCheck);
    groupPane.add(saveAutoLoginCheck);

    labelPane = new JPanel(new FlowLayout());
    //labelPane.add(new JLabel("Please Enter Username and Password to Login"));

    JButton picButton = new JButton("Picture");
    final JFileChooser fileChooser = new JFileChooser();
    //Parent Object
    final MainWindow parentWindow = new MainWindow();
    picButtonListener = new ButtonEventHandler() {
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareToIgnoreCase("Picture") == 0) {
          int returnVal = fileChooser.showOpenDialog(parentWindow);
          if (returnVal == fileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            LoadPicture(file.getAbsolutePath().toString());
          }
        }
      }
    };
    picButton.addActionListener(picButtonListener);
    JPanel picButtonPane = new JPanel(new FlowLayout());
    picButtonPane.add(picButton);
    //displayPicturePane.add(picButton);
    //Add menuPane, toolPane, inputPane ,StatusPane, ButtonPane
    LoginWindow.getContentPane().add(menuPane);
    LoginWindow.getContentPane().add(displayPicturePane);
    //LoginWindow.add(picButtonPane);
    LoginWindow.getContentPane().add(labelPane);
    LoginWindow.getContentPane().add(inputPane);
    LoginWindow.getContentPane().add(statusPane);
    LoginWindow.getContentPane().add(buttonPane);
    LoginWindow.getContentPane().add(groupPane);
    LoginWindow.addWindowListener(this);
    LoginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Init MainFrame
    LoginWindow.pack();
    LoginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    LoginWindow.setResizable(true);
    LoginWindow.setVisible(true);
    LoginWindow.setSize(dim);


  }//end init method

  void LoadPicture(String path) {
    picture = new JLabel(new ImageIcon(path));
    picture.setPreferredSize(new Dimension(130, 130));
    displayPicturePane = new JPanel(new GridBagLayout());
    displayPicturePane.add(picture);
  }

  /**
   * Reset Main Frame
   */
  private void resetMainFrame() {
    labelPane.setVisible(false);
    picture.setVisible(false);
    displayPicturePane.setVisible(false);
    inputPane.setVisible(false);
    buttonPane.setVisible(false);
    statusPane.setVisible(false);
    groupPane.setVisible(false);
    this.remove(picture);
    this.remove(labelPane);
    this.remove(displayPicturePane);
    this.remove(inputPane);
    this.remove(buttonPane);
    this.remove(statusPane);
    this.remove(groupPane);
  }

  /**
   * Logged In Frame
   */
  public void loadLoggedInFrame() {
    //Load the LAN Clients
    resetMainFrame();
    cmb = new JComboBox();
    comboListener = new ButtonEventHandler() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cmb = (JComboBox) e.getSource();
        String selectedItem = cmb.getSelectedItem().toString();
        if (selectedItem != null) {
          MsrpStack.setPeerAddress(selectedItem);
          MsrpStack.setRemoteUser("Omer");
          MsrpStack.setRoute(selectedItem + ":" + MsrpStack.getRemotePort());
          if (startSipSession()) {
            showNewChatWindow();
          }
          else {
            JOptionPane.showMessageDialog(null, UAC.expMessage);
          }

        }
      }
    };
    cmb.addActionListener(comboListener);
    //Main Window's dimension
    Dimension dim = new Dimension();
    LoginWindow.getSize(dim);
    dim.height = 30;
    dim.width = dim.width - 20;
    cmb.setPreferredSize(dim);
    JPanel peerListPane = new JPanel(new FlowLayout());
    peerListPane.add(cmb);
    JButton addContactButton = new JButton("Add Contact");
    addContactListener = new ButtonEventHandler() {
      public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().compareToIgnoreCase("Add Contact") == 0) {
          String retVal = retVal = (String) JOptionPane.showInputDialog(null,
              "Please Enter Contact", null);
          if (retVal.length() > 0) {
            //inset first item
            if (cmb.getItemCount() == 0) {
              cmb.insertItemAt(retVal, 0);
            }
            //insert item
            else {
              cmb.insertItemAt(retVal, cmb.getItemCount());
            }
          }
        }
      }
    };
    addContactButton.addActionListener(addContactListener);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(addContactButton);
    JPanel labelPane = new JPanel(new FlowLayout());
    labelPane.add(new JLabel("Select from the list to chat"));
    LoginWindow.getContentPane().add(labelPane);
    LoginWindow.getContentPane().add(peerListPane);
    LoginWindow.getContentPane().add(buttonPanel);
    LoginWindow.addWindowListener(this);
    LoginWindow.pack();
    LoginWindow.setVisible(true);
  }

  public void showNewChatWindow() {
    chatWindow = new ChatWindow("Send Chat Window");
    chatWindow.init();
    chatWindow.setSendReceieveWindow(true);
    //chatWindow(this);
  }

  /**
   *
   */
  public void setMenuCtrl() {
    //Menu Control
    MenuHandler menuHandle = new MenuHandler();
    JMenuItem menuItem;
    menuBar = new JMenuBar();

    setJMenuBar(menuBar);
    menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_F);
    menuItem = new JMenuItem("Log Out");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menu.addSeparator();
    menuItem = new JMenuItem("Close");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menuBar.add(menu);

    menu = new JMenu("View");
    menu.setMnemonic(KeyEvent.VK_F);
    menuItem = new JMenuItem("View 1");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menuItem = new JMenuItem("View 1");
    menu.add(menuItem);
    menuItem = new JMenuItem("View 2");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menuBar.add(menu);

    menu = new JMenu("Contacts");
    menu.setMnemonic(KeyEvent.VK_F);
    menuItem = new JMenuItem("Add Contact");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menuItem = new JMenuItem("Search Contact");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menuItem = new JMenuItem("Import Contacts");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menuItem = new JMenuItem("Send Contacts");
    menuItem.addActionListener(menuHandle);
    menu.add(menuItem);
    menuBar.add(menu);

    menu = new JMenu("Tools");
    menu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(menu);

    menu = new JMenu("Help");
    menu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(menu);
    menuPane.add(menuBar);
  }



  //AppletWindow start method
  public void start(){

    initSip();
    initMsrp();
    System.out.println("Systems Current Working Directory  is : " +
                       System.getProperty("user.dir"));
    getLocalSystemIP();

  }//end AppletWindow start method

  // SIP and MSRP Stuff Goes Here
  /**
   * Intializes Jain Sip Stack Listeners for UAC and UAS
   */
  public void initSip() {
    uac = new UAC();
    uas = new UAS();
    Thread uacThread = new Thread(uac);
    Thread uasThread = new Thread(uas);
    //Set UAS LocalIP
    uacThread.start();
    uasThread.start();
  }

  /**
   * This method establishes a Sip Session
   */
  boolean startSipSession() {
    return uac.sendInviteRequest(798);
  }

  /**
   * Intializes Msrp Stack
   */
  public void initMsrp() {
    String currentDirectory = getCurrentDirectory();
    MsrpStack.setMsgLogPath(currentDirectory + "\\log\\");
    MsrpStack.setStackLogPath(currentDirectory + "\\log\\");
    //Set Master/Slave Here.
    MsrpStack.setLocalUser("Omer");
    MsrpStack.setLocalAddress(getLocalSystemIP());
    MsrpStack.setRemotePort(5070);
    msrpListener = new MsrpListner();
    Thread msrpListenerThread = new Thread(msrpListener);
    msrpListenerThread.start();

  }

  /**
   *
   * @param tempAddress String
   */
  public void setRemotAddress(String tempAddress) {
    StringBuffer buffer = new StringBuffer(tempAddress);
    int index = buffer.indexOf("(");
    tempAddress = buffer.substring(index + 1, buffer.length() - 1);
    remoteAddress = tempAddress;
  }

  /**
   *
   * @return String
   */
  public String getLocalSystemIP() {
    String ip = "";
    try {
      ip = Inet4Address.getLocalHost().getHostAddress();
      System.out.println("Current System Local IP address =: " + ip);
    }
    catch (Exception exp) {
      System.out.println(exp.toString());
    }
    return ip;
  }

  /**
   * Sip Session Established
   */
  public static void SipSessionEstablished() {
    sessionEstablished = true;
    System.out.println("Sip Session Established");
  }

  /**
   * Close Session
   */
  public void closeSession() {
    if( sessionEstablished == true){
      System.out.println("Sending BYE");
      uac.sendBye();
    }
    else{
      System.out.println("BYE");
    }
  }
  /**
   *
   * @param val boolean
   */
  public void setSessionState(boolean val){
   this.sessionClosed = val;
  }
  /**
   *
   * @return boolean
   */
  public boolean isSessionColsed(){
    return sessionClosed;
  }
  public String getCurrentDirectory() {
    return System.getProperty("user.dir");
  }

  /**
   *
   * @param args String[]
   */

  public void windowClosing(WindowEvent e) {
    System.out.println("Closed");
  }

  public void windowClosed(WindowEvent e) {
    System.out.println("Closed");
  }

  /**
   * Window Opened
   * @param e WindowEvent
   */
  public void windowOpened(WindowEvent e) {
  }

  /**
   * Window Iconified
   * @param e WindowEvent
   */
  public void windowIconified(WindowEvent e) {
  }

  /**
   * Window Deiconified
   * @param e WindowEvent
   */
  public void windowDeiconified(WindowEvent e) {
  }

  /**
   * Window Deactivated
   * @param e WindowEvent
   */
  public void windowActivated(WindowEvent e) {
  }

  public void windowDeactivated(WindowEvent e) {
    System.out.println("Main Window Deactivated");

  }

}// end AppletWindow class

/*class ButtonEventHandler
    implements ActionListener {
  public void actionPerformed(ActionEvent e) {
  }
}

class ListSelectionHandler
    implements ListSelectionListener {
  public void valueChanged(ListSelectionEvent e) {
  }
}*/
