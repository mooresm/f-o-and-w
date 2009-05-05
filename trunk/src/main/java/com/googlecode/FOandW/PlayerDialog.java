package com.googlecode.FOandW;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class PlayerDialog extends JDialog
                   implements ActionListener 
{
  protected Display GUI;
  protected GameData data;
  protected JTextField name;
  protected NumericField hits;
  protected SignedNumericField ac;
  protected SignedNumericField ac_excl;
  protected NumericField str;
  protected NumericField dex;
  protected NumericField con;
  protected NumericField opp;
  protected NumericField rear;
  protected JCheckBox large;
  protected JLabel wpn;
  protected Weapon m_wpn;
  protected JButton btn;
  static final String ok = "finito";
  static final String wp = "weapon";

  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals(ok))
    {
      // verify Dialog contents
      if (name.getText().length() == 0)
      {
        JOptionPane.showMessageDialog(this,
          "Please enter a name for this PC");
        return;
      }

      // add PC to GameData
      Player p = new Player(name.getText(),
                            hits.getValue(),
                            ac.getValue(),
                            ac_excl.getValue(),
                            large.isSelected(),
                            opp.getValue(),
                            rear.getValue(),
                            m_wpn
                           );
      // dispose
      dispose();

      // update menu & GUI
      int ix = data.addPC(p);
      p.createLabel(GUI);
    }
    else if (e.getActionCommand().equals(wp))
    {
      // get weapon details from user
      Frame f = JOptionPane.getFrameForComponent(this);
      WeaponDialog w = new WeaponDialog(f, this, data);
      w.show(); // modal dialog box
      if (w.m_ok)
      {
        m_wpn = w.m_wpn;
        btn.setEnabled(true);
        getRootPane().setDefaultButton(btn);
        wpn.setText(m_wpn.name);
      }
      pack();
      show();
    }
  }

  public PlayerDialog(Display c, GameData data)
  {
    super(JOptionPane.getFrameForComponent(c), true);
    this.data = data;
    this.GUI  = c;
    setTitle("Enter this Player Character's details");

    // place UI elements in pane
    Container ctr = getContentPane();
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(5,5,5,5);  // padding
    ctr.setLayout(gridbag);

    JLabel l = new JLabel("Name:");
    l.setToolTipText("Enter an unique name for the PC");
    g.anchor = GridBagConstraints.EAST; // right-justify
    g.gridx  = 0;
    g.gridy  = 0;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    name = new JTextField(25);
    l.setLabelFor(name);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 1;
    g.gridwidth = 5;
    gridbag.setConstraints(name, g);
    ctr.add(name);

    l = new JLabel("STR:");
    l.setToolTipText("Strength");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 0;
    g.gridy  = 1;
    g.gridwidth = 1;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    str = new NumericField(9, 2);
    str.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(str);
    g.gridx  = 1;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(str, g);
    ctr.add(str);

    l = new JLabel("HP:");
    l.setToolTipText("Hit Points");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 4;
    g.gridwidth = 1;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    hits = new NumericField(0, 3);
    hits.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(hits);
    g.anchor = GridBagConstraints.WEST;
    g.gridx = 5;
    gridbag.setConstraints(hits, g);
    ctr.add(hits);

    l = new JLabel("DEX:");
    l.setToolTipText("Dexterity");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 0;
    g.gridy  = 2;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    dex = new NumericField(9, 2);
    dex.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(dex);
    g.gridx = 1;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(dex, g);
    ctr.add(dex);

    l = new JLabel("AC:");
    l.setToolTipText("Armor Class");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 4;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    ac = new SignedNumericField(10, 2);
    ac.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(ac);
    g.anchor = GridBagConstraints.WEST;
    g.gridx = 5;
    gridbag.setConstraints(ac, g);
    ctr.add(ac);

    l = new JLabel("CON:");
    l.setToolTipText("Constitution");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 0;
    g.gridy  = 3;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    con = new NumericField(9, 2);
    con.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(con);
    g.gridx = 1;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(con, g);
    ctr.add(con);

    l = new JLabel("AC (excl. DEX & shield):");
    l.setToolTipText("flank Armor Class"
      + " (excluding Dexterity and shield bonus)");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 2;
    g.gridwidth = 3;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    ac_excl = new SignedNumericField(10, 2);
    ac_excl.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(ac_excl);
    g.gridx = 5;
    g.gridwidth = 1;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(ac_excl, g);
    ctr.add(ac_excl);

    large = new JCheckBox("Large?");
    large.setToolTipText("Is the player taller than 7ft?");
    g.anchor = GridBagConstraints.EAST;
    g.gridx = 0;
    g.gridy  = 4;
    g.gridwidth = 2;
    gridbag.setConstraints(large, g);
    ctr.add(large);

    l = new JLabel("opponents:");
    l.setToolTipText("The maximum number of opponents"
      + " this PC will face in battle simultaneously");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 2;
    g.gridwidth = 1;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    opp = new NumericField(6, 2);
    opp.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(opp);
    g.gridx = 3;
    g.anchor = GridBagConstraints.EAST;
    gridbag.setConstraints(opp, g);
    ctr.add(opp);

    l = new JLabel("(rear):");
    l.setToolTipText("The number of opponents that"
      + " may attack the player from behind");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 4;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    rear = new NumericField(3, 2);
    rear.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(rear);
    g.gridx = 5;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(rear, g);
    ctr.add(rear);

    l = new JLabel("Wielding:");
    l.setToolTipText("Weapon of Choice");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 0;
    g.gridy  = 5;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    wpn = new JLabel("none.");
    l.setLabelFor(wpn);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 1;
    g.gridwidth = 2;
    gridbag.setConstraints(wpn, g);
    ctr.add(wpn);

    JButton b = new JButton("Weapons");
    b.setActionCommand(wp);
    b.addActionListener(this);
    b.setToolTipText("Click this button to change the weapon"
                     + " wielded by this PC");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 3;
    gridbag.setConstraints(b, g);
    ctr.add(b);

    btn = new JButton("Finished");
    btn.setActionCommand(ok);
    btn.setEnabled(false);
    btn.addActionListener(this);
    btn.setToolTipText("Click this button when you are finished"
                       + " entering the PC's details");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 5;
    g.gridwidth = 1;
    gridbag.setConstraints(btn, g);
    ctr.add(btn);

    pack(); // ie. resize dialog to fit contents
    setLocationRelativeTo(c); // center in window
  }
}