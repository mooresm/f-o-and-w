package com.googlecode.FOandW;

import java.util.Random;

class Monster extends Individual
{
  protected Player opponent;
  protected MonsterSummary parent;
  int pos;
  boolean flanking;

  Monster(String name, int hp, int ac, Weapon wpn,
          boolean big, int pos, MonsterSummary parent)
  {
    super(name, hp, ac, big, wpn);
    this.parent = parent;
    this.pos = pos;
    flanking = false;
  }

  boolean hasOpponent()
  {
    return ((opponent != null) && (!opponent.isDead()));
  }

  String getOpponent()
  {
    if (opponent == null)
    {
      return "nobody.";
    }
    else
    {
      return opponent.toString();
    }
  }

  void addOpponent(Player p)
  {
    opponent = p;
  }

  void setFlank(boolean flank)
  {
    this.flanking = flank;
  }

  int attack(Random rand)
  {
    int damage =
      super.wpn.strike(rand, (Individual)opponent, flanking);
    if (opponent.isDead())
    {
      opponent = null;
    }
    return damage;
  }

  void takeThat(int damage)
  {
    super.hp -= damage;
    if (super.hp <= 0)
    {
      parent.vMon.remove(this);
      parent.gui.setText(parent.toString());
    }
  }

  public String toString()
  {
    Integer hits = new Integer(super.hp);
    Integer ix = new Integer(pos);
    return super.name
      + "[" + ix.toString()
      + "]=" + hits.toString();
  }
}