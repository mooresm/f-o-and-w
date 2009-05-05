package com.googlecode.FOandW;

import java.util.Vector;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JLabel;

class MonsterSummary
{
  String name;
  Vector vMon;
  JLabel gui;
  int morale;
  int ac;
  Weapon wpn;

  MonsterSummary(String name, int morale, int ac, Weapon wpn)
  {
    this.name = name;
    this.morale = morale;
    this.ac = ac;
    this.wpn = wpn;
  }

  public void populate(int quantity, int hd, int plus,
                       Vector d8rolls, boolean big)
  {
    this.vMon = new Vector(quantity, quantity);
    // roll hit points and generate monsters
    for (int i=0; i<quantity; i++)
    {
      int hp=plus;
      for (int j=0; j<hd; j++)
      {
        // throw invalid cast exception on error
        Integer roll = (Integer)d8rolls.get((i*hd)+j);
        hp += roll.intValue();
      }
      vMon.addElement(new Monster(name, hp, ac, wpn, big, i, this));
    }
  }

  public void createLabel(Display disp)
  {
    // create new GUI element
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0; gbc.weightx = 1;
    gbc.gridy = GridBagConstraints.RELATIVE;

    gui = new JLabel(toString());
    gui.setForeground(Color.white);

    Container c = disp.getContentPane();
    c.add(gui, gbc);
    c.doLayout();
  }

  public String toString()
  {
    if ((vMon != null) && (vMon.size() > 0))
    {
      return new Integer(vMon.size()).toString()
        + " " + name;
    }
    else
    {
      return name;
    }
  }
}