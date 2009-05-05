package com.googlecode.FOandW;

import java.util.Vector;
import java.util.Random;
import java.awt.Container;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;

class Player extends Individual
{
  protected Vector opponents;
  protected int ac_excl;
  protected JLabel  gui;
  int max_opp;
  int opp_rear;

  Player(String name, int hp, int ac, int ac_excl,
         boolean big, int opp, int rear, Weapon wpn)
  {
    super(name, hp, ac, big, wpn);
    this.ac_excl = ac_excl;
    this.max_opp = opp;
    this.opp_rear = rear;
    opponents = new Vector(max_opp, 8);
  }

  public boolean hasOpponent()
  {
    return (opponents.size() > 0);
  }

  public boolean roomForMore()
  {
    return ((opponents.size() < max_opp) && !isDead());
  }

  public void addOpponent(Monster m)
  {
    if (opponents.size() < max_opp)
    {
      opponents.addElement(m);
      updateLabel();
    }

    if (opponents.size() >= (max_opp - opp_rear))
    {
      m.setFlank(true);
    }
  }

  int getAC(boolean flank)
  {
    if (flank)
    {
      return ac_excl + 2;
    }
    else
    {
      return ac;
    }
  }

  public String getOpponent()
  {
    if (opponents.size() > 0)
    {
      Monster m = (Monster)opponents.elementAt(0);
      return m.toString();
    }
    else
    {
      return "nobody.";
    }
  }

  int attack(Random rand)
  {
    Individual i = (Individual)opponents.elementAt(0);
    int damage = super.wpn.strike(rand, i, false);
    if (i.isDead())
    {
      opponents.remove(0);
      updateLabel();
    }

    // shuffle opponents
    for (int ix=0; ix<opponents.size(); ix++)
    {
      Monster m = (Monster)opponents.elementAt(ix);
      m.setFlank(ix >= (max_opp - opp_rear - 1));
    }

    return damage;
  }

  void takeThat(int damage)
  {
    hp -= damage;
    updateLabel();
  }

  protected void updateLabel()
  {
    Integer hits = new Integer(super.hp);
    Integer oppn = new Integer(opponents.size());
    gui.setText("<html><b>" + super.name +
      "</b> " + hits.toString() +
      "hp (<i>vs.</i> " + oppn.toString() +
      ")</html>");
  }

  public void createLabel(Display GUI)
  {
    // create new GUI element
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0; gbc.weightx = 1;
    gbc.gridy = GridBagConstraints.RELATIVE;

    gui = new JLabel();
    updateLabel();
    gui.setForeground(Color.white);

    Container c = GUI.getContentPane();
    c.add(gui, gbc);
    c.doLayout();
  }

  public String toString()
  {
    Integer hits = new Integer(super.hp);
    Integer oppn = new Integer(opponents.size());
    return super.name +
      " (wielding " + super.wpn.name +
      ") " + hits.toString() + "hp -- fighting " +
      oppn.toString() + " opponents.";
  }
}