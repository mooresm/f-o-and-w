package com.googlecode.FOandW;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class WeaponDialog extends JDialog
                   implements ActionListener
{
  Weapon m_wpn;
  boolean m_ok;
  protected JTextField name;
  protected NumericField att;
  protected NumericField rnd;
  protected NumericField THAC0;
  protected NumericField spd;
  protected NumericField dSMnum;
  protected JComboBox    dSMdie;
  protected NumericField dSMplus;
  protected NumericField dLnum;
  protected JComboBox    dLdie;
  protected NumericField dLplus;
  static final String ok = "finito";

  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals(ok))
    {
      m_ok = true;
      m_wpn = new Weapon();
      m_wpn.name = name.getText();
      m_wpn.attacks = att.getValue();
      m_wpn.rounds = rnd.getValue();
      m_wpn.THAC0 = THAC0.getValue();
      m_wpn.speed = spd.getValue();
      m_wpn.dSMnum = dSMnum.getValue();
      m_wpn.dSMplus = dSMplus.getValue();
      m_wpn.dLnum = dLnum.getValue();
      m_wpn.dLplus = dLplus.getValue();

      switch(dSMdie.getSelectedIndex())
      {
      case 0:  m_wpn.dSMdie = GameData.D3; break;
      case 1:  m_wpn.dSMdie = GameData.D4; break;
      case 2:  m_wpn.dSMdie = GameData.D6; break;
      case 3:  m_wpn.dSMdie = GameData.D8; break;
      case 4:  m_wpn.dSMdie = GameData.D10; break;
      case 5:  m_wpn.dSMdie = GameData.D12; break;
      case 6:  m_wpn.dSMdie = GameData.D20; break;
      case 7:  m_wpn.dSMdie = GameData.Dpc; break;
      }

      switch(dLdie.getSelectedIndex())
      {
      case 0:  m_wpn.dLdie = GameData.D3; break;
      case 1:  m_wpn.dLdie = GameData.D4; break;
      case 2:  m_wpn.dLdie = GameData.D6; break;
      case 3:  m_wpn.dLdie = GameData.D8; break;
      case 4:  m_wpn.dLdie = GameData.D10; break;
      case 5:  m_wpn.dLdie = GameData.D12; break;
      case 6:  m_wpn.dLdie = GameData.D20; break;
      case 7:  m_wpn.dLdie = GameData.Dpc; break;
      }

      hide();
    }
  }

  WeaponDialog(Frame f, Component c, GameData data)
  {
    super(f, true);
    setTitle("Weapon of Choice");

    // place UI elements in pane
    Container ctr = getContentPane();
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(5,5,5,5);  // padding
    ctr.setLayout(gridbag);

    JLabel l = new JLabel("Name:");
    l.setToolTipText("Enter a name for this weapon");
    g.anchor = GridBagConstraints.EAST; // right-justify
    g.gridx  = 0;
    g.gridy  = 0;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    name = new JTextField(20);
    l.setLabelFor(name);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 1;
    g.gridwidth = 4;
    gridbag.setConstraints(name, g);
    ctr.add(name);

    att = new NumericField(1, 2);
    att.setHorizontalAlignment(NumericField.RIGHT);
    g.gridx  = 1;
    g.gridy  = 1;
    g.gridwidth = 1;
    g.anchor = GridBagConstraints.EAST;
    gridbag.setConstraints(att, g);
    ctr.add(att);

    l = new JLabel("attack/s per");
    l.setLabelFor(att);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 2;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    rnd = new NumericField(1, 2);
    rnd.setHorizontalAlignment(NumericField.RIGHT);
    g.gridx  = 3;
    g.anchor = GridBagConstraints.EAST;
    gridbag.setConstraints(rnd, g);
    ctr.add(rnd);

    l = new JLabel("round/s");
    l.setLabelFor(rnd);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 4;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    l = new JLabel("THAC0:");
    l.setToolTipText("To Hit Armor Class 0");
    g.anchor = GridBagConstraints.EAST; // right-justify
    g.gridx  = 0;
    g.gridy  = 2;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    THAC0 = new NumericField(20, 2);
    THAC0.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(THAC0);
    g.gridx  = 1;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(THAC0, g);
    ctr.add(THAC0);

    l = new JLabel("weapon speed:");
    l.setToolTipText("Initiative penalty (lower is better)");
    g.anchor = GridBagConstraints.EAST; // right-justify
    g.gridx  = 2;
    g.gridwidth = 2;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    spd = new NumericField(7, 2);
    spd.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(spd);
    g.gridx  = 4;
    g.gridwidth = 1;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(spd, g);
    ctr.add(spd);

    l = new JLabel("dam(S-M):");
    l.setToolTipText("damage vs. small//medium opponents");
    g.anchor = GridBagConstraints.EAST; // right-justify
    g.gridx  = 0;
    g.gridy  = 3;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    dSMnum = new NumericField(1, 2);
    dSMnum.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(dSMnum);
    g.gridx  = 1;
    g.anchor = GridBagConstraints.EAST;
    gridbag.setConstraints(dSMnum, g);
    ctr.add(dSMnum);

    dSMdie = new JComboBox(data.diceStrings);
    dSMdie.setSelectedIndex(2);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 2;
    gridbag.setConstraints(dSMdie, g);
    ctr.add(dSMdie);

    l = new JLabel("+");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 3;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    dSMplus = new NumericField(0, 2);
    dSMplus.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(dSMplus);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 4;
    gridbag.setConstraints(dSMplus, g);
    ctr.add(dSMplus);

    l = new JLabel("dam(Lge):");
    l.setToolTipText("damage vs. Large opponents");
    g.anchor = GridBagConstraints.EAST; // right-justify
    g.gridx  = 0;
    g.gridy  = 4;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    dLnum = new NumericField(1, 2);
    dLnum.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(dLnum);
    g.gridx  = 1;
    g.anchor = GridBagConstraints.EAST;
    gridbag.setConstraints(dLnum, g);
    ctr.add(dLnum);

    dLdie = new JComboBox(data.diceStrings);
    dLdie.setSelectedIndex(3);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 2;
    gridbag.setConstraints(dLdie, g);
    ctr.add(dLdie);

    l = new JLabel("+");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 3;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    dLplus = new NumericField(0, 2);
    dLplus.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(dLplus);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 4;
    gridbag.setConstraints(dLplus, g);
    ctr.add(dLplus);

    JButton btn = new JButton("Finished");
    btn.setActionCommand(ok);
    btn.addActionListener(this);
    btn.setToolTipText("Click this button when you are finished"
                       + " entering the weapon description");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 4;
    g.gridy  = 5;
    gridbag.setConstraints(btn, g);
    ctr.add(btn);

    pack();
    setLocationRelativeTo(c); // center in window
  }    
}