package msrp.test.examples.messanger;

import msrp.stack.MsrpStack;
import msrp.stack.TCPChannel;
import msrp.message.MsrpRequest;
import msrp.message.MessageFactoryImpl;
import msrp.header.ContentType;
import msrp.header.MessageID;
import msrp.header.FromPath;
import msrp.header.ByteRange;
import msrp.header.ToPath;
import msrp.address.MsrpUrl;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JList;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * 
 * @author inam_
 */
public class ChatWindow
    extends JFrame implements KeyListener, WindowListener {
  /** GUI Stuff */
  public TextField txtInput = null;
  public JList messageWindow = null;
  JButton sendButton = null;
  JButton fontButton = null;
  JButton emotionsButton = null;
  TCPChannel tcpChannel = null;
  String body;
  boolean isSendWindow = true;
  DefaultListModel listModel = null;
  String title = "";
  MainWindow parent = null;
  /**
   *
   * @param title String
   */
  public ChatWindow(String title) {
    tcpChannel = new TCPChannel();
    this.title = title;
  }

  /**
   *
   * @return boolean
   */
  public boolean isSendWindow() {
    return isSendWindow;
  }

  /**
   *
   * @param flag boolean
   */
  public void setSendReceieveWindow(boolean flag) {
    this.isSendWindow = flag;
  }

  /**
   *
   */
  public void init() {

    this.getContentPane().setLayout(new BorderLayout());
    this.setLocation(new Point(200, 80));
    txtInput = new TextField(20);
    txtInput.addKeyListener(this);
    listModel = new DefaultListModel();
    messageWindow = new JList(listModel);
    messageWindow.setPreferredSize(new Dimension(270, 380));

    //Create SpitWindow
    JSplitPane splitPane = new JSplitPane(0, messageWindow, txtInput);
    splitPane.setPreferredSize(new Dimension(280, 500));
    this.getContentPane().add(splitPane, BorderLayout.NORTH);

    //Add Buttons
    JPanel buttonsPane = new JPanel(new FlowLayout());
    sendButton = new JButton("Send");
    fontButton = new JButton("Font");
    emotionsButton = new JButton("Emotions");
    //sendButton.addActionListener(new ActionHandler());
    sendButton.setEnabled(true);
    fontButton.setEnabled(true);
    emotionsButton.setEnabled(true);
    buttonsPane.add(sendButton);
    buttonsPane.add(fontButton);
    buttonsPane.add(emotionsButton);

    this.getContentPane().add(buttonsPane, BorderLayout.SOUTH);
    this.addWindowListener(this);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setTitle(title);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Sends MSRP Message
   */
  public void sendMessage() {

    MessageFactoryImpl messageFactory = new MessageFactoryImpl();
    body = txtInput.getText();
    if (body == null) {
      body = "null message";
      System.out.println("Null Body Messsage");
    }
    String transactionId = "";
    if (getTransactionId() == null) {
      transactionId = messageFactory.createTransactionId();
    }
    else {
      transactionId = getTransactionId();
    }
    String strFrom = "msrp://" + MsrpStack.getLocalAddress() + ":" +
        MsrpStack.getLocalPort() + "/" + transactionId + ";" +
        getTransport();
    String strTo = "msrp://" + MsrpStack.getPeerAddress() + ":" +
        getRemotePort() + "/" + transactionId + ";" +
        getTransport();
    MsrpUrl from = new MsrpUrl(strFrom);
    MsrpUrl to = new MsrpUrl(strTo);
    FromPath fromPath = new FromPath(from);
    ToPath toPath = new ToPath(to);
    MessageID messageID = new MessageID(getMessageId());
    ByteRange byteRange = null;
    if (body.length() != 0) {
      byteRange = new ByteRange();
      byteRange.setStartIndex(1);
      byteRange.setEndIndex(body.length());
      byteRange.setSize(body.length());
                             }
    ContentType contentType = new ContentType("text", "plain");

    MsrpRequest request = messageFactory.createRequest("SEND", toPath, fromPath,
        messageID, byteRange, contentType,
        body);
   // if (!tcpChannel.sendRequest(request)) {
    //  JOptionPane.showMessageDialog(null, "ERROR!! Message Not Send");
   // }
   // else
   System.out.print("\nSendin TCP REQ");
      tcpChannel.sendRequest(request);
      txtInput.setText("");

  }

  /**
   * Thread Entry Point
   */
  public void run() {
    init();
  }

  /**
   * Get transaction Id
   * @return String
   */
  public String getTransactionId() {
    //Retrieve SipTrasanstion and Asscociate it with MSRP session
    // return MessageFactoryImpl.createTransactionId();
    return MsrpStack.getCurrentTransaction();
  }

  public String getMessageId() {
    return MessageFactoryImpl.createTransactionId();
  }

  /**
   * Get Remote Port
   * @return int
   */
  public int getRemotePort() {
    return 6060;
  }

  /**
   * Get Transport
   * @return String
   */
  public String getTransport() {
    return "tcp";
  }

  /**
   * Get User
   * @return String
   */
  public String getUser() {
    return "omer";
  }

  /**
   *
   */
  public void SipSessionEstablished() {
  }

  /**
   * Add Message to Message Window Log
   * @param message String
   */
  public void addMessageToList(String message) {
    listModel.insertElementAt(message, listModel.getSize());
  }

  // Events
  /**
   * Key Pressed Event
   * @param e KeyEvent
   */
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 10) {
      Date dateTime = new Date();
      SimpleDateFormat format = new SimpleDateFormat("H:mm:ss", Locale.ENGLISH);
      addMessageToList("Local [" + format.format(dateTime) + "]\t:" +
                      MsrpStack.getLocalUser()+ " says :  \n" +"\n\t\t" + txtInput.getText());
      sendMessage();
      txtInput.setText("");
    }

  }

  /**
   * Key Released Event
   * @param e KeyEvent
   */
  public void keyReleased(KeyEvent e) {

  }

  /**
   * Key Typed
   * @param e KeyEvent
   */
  public void keyTyped(KeyEvent e) {
  }

  /**
   *
   * @param args String[]
   */
  public static void main(String args[]) {
    ChatWindow newChat = new ChatWindow("Title");
    newChat.init();
  }

  public void windowOpened(WindowEvent e) {
  }

  public void windowClosing(WindowEvent e) {
    System.out.println("Closing Session & Sending BYE Request");
    if (parent != null && parent.isSessionColsed()) {
      parent.closeSession();
    }
  }

  public void windowClosed(WindowEvent e) {

  }

  public void windowIconified(WindowEvent e) {

  }

  public void windowDeiconified(WindowEvent e) {
  }

  public void windowActivated(WindowEvent e) {
  }

  public void windowDeactivated(WindowEvent e) {
  }

  public void setParent(MainWindow parentFrame) {
    parent = parentFrame;
  }

}
