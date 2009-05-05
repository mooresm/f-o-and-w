package com.googlecode.FOandW;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Display extends JApplet
                     implements ActionListener, ItemListener 
{
  // GUI components
  protected JMenuBar menuBar;
  protected JMenu edit_pc, del_pc, edit_op, del_op;
  protected JMenuItem goBabyGo;
  protected JTextArea textArea;
  protected JScrollPane scrollPane;
  protected JCheckBoxMenuItem supr_pc, supr_op;
  protected Frame pFrame;

  // Logic components
  protected GameData data;
  static final String addPC = "addPC";
  static final String addMon = "addMon";
  static final String goBaby = "goBaby";

  public void init()
  {
    // set native look & feel
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {}

    pFrame = JOptionPane.getFrameForComponent(this);

    // add a pull-down menu
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    addMenuItems();

    // place UI elements in pane
    Container c = getContentPane();
    c.setBackground(Color.black);
    c.setForeground(Color.white);
    c.setLayout(new GridBagLayout()); 

    GridBagConstraints gbc = new GridBagConstraints();

    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.gridheight = GridBagConstraints.REMAINDER;
    gbc.gridwidth = 1; gbc.weighty = 1;
    gbc.gridx = 1; gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.anchor = GridBagConstraints.NORTHEAST;
    textArea = new JTextArea(5, 60);
    scrollPane = new JScrollPane(textArea);
    c.add(scrollPane, gbc);
    data = new GameData(textArea);
  }

  public synchronized void logText(String txt)
  {
    textArea.append(txt);
  }

  public void actionPerformed(ActionEvent e)
  {
    //...Get information from the action event...
    String command = e.getActionCommand();
    
    if (command.equals(addPC))
    {
      // collect Player stats, & add to summary window
      PlayerDialog pdlg = new PlayerDialog(this, data);
      pdlg.setVisible(true);
    }
    else if (command.equals(addMon))
    {
      MonsterDialog mdlg = new MonsterDialog(this, data);
      mdlg.setVisible(true);
    }
    else if (command.equals(goBaby))
    {
      goBabyGo.setEnabled(false);
      // fight to the death
      Thread t = new Thread(data);
      t.start();
    }
    else
    {
      Object source = e.getSource();
      JOptionPane.showMessageDialog(this, source.getClass().getName() +
                                    " fired an unhandled event: " + command
                                   );
    }
  }

  public void itemStateChanged(ItemEvent e)
  {
    //...Get information from the item event...
    JMenuItem source = (JMenuItem)(e.getSource());

    //...Display it in the text area...
    textArea.append(e.toString() + "\n");

    Container c = getContentPane();

    if (e.getStateChange() == ItemEvent.SELECTED)
    {
    }
  }

  /**
  * create the pull-down menus
  */
  void addMenuItems()
  {
    JMenu menu;
    JMenuItem menuItem;

    //Build the first menu.
    menu = new JMenu("Players");
    menu.setMnemonic('P');
    menuBar.add(menu);

    menuItem = new JMenuItem("Add a PC", KeyEvent.VK_A);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_P, ActionEvent.ALT_MASK));
    menuItem.setActionCommand(addPC); // this is important
    menuItem.addActionListener(this);
    menu.add(menuItem);

    edit_pc = new JMenu("Edit PC");
    edit_pc.setMnemonic('e');
    edit_pc.setEnabled(false);
    menu.add(edit_pc);

    del_pc = new JMenu("Delete PC");
    del_pc.setMnemonic('d');
    del_pc.setEnabled(false);
    menu.add(del_pc);

    menu.addSeparator();
    supr_pc = new JCheckBoxMenuItem("Surprised");
    supr_pc.setMnemonic('s');
    menu.add(supr_pc);

    //Build second menu in the menu bar.
    menu = new JMenu("Opponents");
    menu.setMnemonic('O');
    menuBar.add(menu);

    menuItem = new JMenuItem("Add an opponent", KeyEvent.VK_A);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_O, ActionEvent.ALT_MASK));
    menuItem.setActionCommand(addMon); // this is important
    menuItem.addActionListener(this);
    menu.add(menuItem);

    edit_op = new JMenu("Edit opponent");
    edit_op.setMnemonic('e');
    edit_op.setEnabled(false);
    menu.add(edit_op);

    del_op = new JMenu("Delete opponent");
    del_op.setMnemonic('d');
    del_op.setEnabled(false);
    menu.add(del_op);

    menu.addSeparator();
    supr_op = new JCheckBoxMenuItem("Surprised");
    supr_op.setMnemonic('s');
    menu.add(supr_op);

    //Build third menu in the menu bar.
    menu = new JMenu("Battle");
    menu.setMnemonic('B');
    menuBar.add(menu);

    goBabyGo = new JMenuItem("GO!!", KeyEvent.VK_G);
    goBabyGo.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_G, ActionEvent.ALT_MASK));
    goBabyGo.setActionCommand(goBaby); // this is important
    goBabyGo.addActionListener(this);
    menu.add(goBabyGo);
    menuItem = new JMenuItem("Stop", KeyEvent.VK_S);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_S, ActionEvent.ALT_MASK));
    menuItem.setEnabled(false);
    menu.add(menuItem);

    menu.addSeparator();
    menuItem = new JMenuItem("Initiative modifiers", KeyEvent.VK_I);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_I, ActionEvent.ALT_MASK));
    menuItem.addActionListener(this);
    menu.add(menuItem);
    menuItem = new JMenuItem("Morale modifiers", KeyEvent.VK_M);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_M, ActionEvent.ALT_MASK));
    menuItem.addActionListener(this);
    menu.add(menuItem);
    menu.addSeparator();
    menuItem = new JMenuItem("other parameters", KeyEvent.VK_P);
    menuItem.addActionListener(this);
    menu.add(menuItem);

    // HELP
    menuBar.add(Box.createHorizontalGlue());
    menu = new JMenu("Help");
    menu.setMnemonic('H');
    menuBar.add(menu);
    menuItem = new JMenuItem("About", KeyEvent.VK_A);
    menuItem.addActionListener(this);
    menu.add(menuItem);
  }
}