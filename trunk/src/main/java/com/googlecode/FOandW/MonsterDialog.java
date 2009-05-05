package com.googlecode.FOandW;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MonsterDialog extends JDialog
                   implements ActionListener 
{
  protected Display GUI;
  protected GameData data;
  protected JTextField name;
  protected NumericField quantity;
  protected SignedNumericField ac;
  protected NumericField morale;
  protected NumericField hits;
  protected NumericField plus;
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
          "Please enter a name for this opponent");
        return;
      }

      // add opponent to GameData
      int num = quantity.getValue();
      int die = hits.getValue();
      int and = plus.getValue();
      boolean big = large.isSelected();
      MonsterSummary m =
        new MonsterSummary(name.getText(),
                           morale.getValue(),
                           ac.getValue(),
                           m_wpn
                          );

      // leave modal dialog
      dispose();

      // update menu & GUI
      int ix = data.addMonsters(m, num, die, and, big);
      m.createLabel(GUI);
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

  public MonsterDialog(Display c, GameData data)
  {
    super(JOptionPane.getFrameForComponent(c), true);
    this.data = data;
    this.GUI  = c;
    setTitle("Enter the Opponents' details");

    // place UI elements in pane
    Container ctr = getContentPane();
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(5,5,5,5);  // padding
    ctr.setLayout(gridbag);

    JLabel l = new JLabel("Name:");
    l.setToolTipText("Enter an unique name for the Opponents");
    g.anchor = GridBagConstraints.EAST; // right-justify
    g.gridx  = 0;
    g.gridy  = 0;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    name = new JTextField(25);
    l.setLabelFor(name);
    g.anchor = GridBagConstraints.WEST;
    g.gridx  = 1;
    g.gridwidth = 6;
    gridbag.setConstraints(name, g);
    ctr.add(name);

    l = new JLabel("Quantity:");
    l.setToolTipText("how many opponents");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 0;
    g.gridy  = 1;
    g.gridwidth = 1;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    quantity = new NumericField(0, 5);
    quantity.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(quantity);
    g.gridx  = 1;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(quantity, g);
    ctr.add(quantity);

    l = new JLabel("Hit Dice:");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 3;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    hits = new NumericField(0, 3);
    hits.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(hits);
    g.anchor = GridBagConstraints.WEST;
    g.gridx = 4;
    gridbag.setConstraints(hits, g);
    ctr.add(hits);

    l = new JLabel("d8 + ");
    g.anchor = GridBagConstraints.CENTER;
    g.gridx  = 5;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    plus = new NumericField(0, 2);
    plus.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(plus);
    g.gridx = 6;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(plus, g);
    ctr.add(plus);

    l = new JLabel("AC:");
    l.setToolTipText("Armor Class");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 0;
    g.gridy  = 2;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    ac = new SignedNumericField(10, 3);
    ac.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(ac);
    g.anchor = GridBagConstraints.WEST;
    g.gridx = 1;
    gridbag.setConstraints(ac, g);
    ctr.add(ac);

    l = new JLabel("Morale:");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 3;
    gridbag.setConstraints(l, g);
    ctr.add(l);

    morale = new NumericField(9, 2);
    morale.setHorizontalAlignment(NumericField.RIGHT);
    l.setLabelFor(morale);
    g.gridx = 4;
    g.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(morale, g);
    ctr.add(morale);

    large = new JCheckBox("Large?");
    large.setToolTipText("Are these Opponents bigger than 7ft?");
    g.anchor = GridBagConstraints.EAST;
    g.gridx = 5;
    g.gridwidth = 2;
    gridbag.setConstraints(large, g);
    ctr.add(large);

    l = new JLabel("Wielding:");
    l.setToolTipText("Weapon of Choice");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 0;
    g.gridy  = 3;
    g.gridwidth = 1;
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
                     + " wielded by these Opponents");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 3;
    gridbag.setConstraints(b, g);
    ctr.add(b);

    btn = new JButton("Finished");
    btn.setActionCommand(ok);
    btn.setEnabled(false);
    btn.addActionListener(this);
    btn.setToolTipText("Click this button when you are finished"
                       + " entering these Opponents' details");
    g.anchor = GridBagConstraints.EAST;
    g.gridx  = 5;
    gridbag.setConstraints(btn, g);
    ctr.add(btn);

    pack(); // ie. resize dialog to fit contents
    setLocationRelativeTo(c); // center in window
  }
}