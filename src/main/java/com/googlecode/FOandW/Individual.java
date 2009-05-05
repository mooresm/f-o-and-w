package com.googlecode.FOandW;

import java.lang.Comparable;
import java.util.Random;

class Individual implements Comparable // partial ordering by Initiative
{
  protected String name;
  protected int initiative;
  protected Weapon wpn;
  protected float attacks;
  protected int hp;
  protected int ac;
  protected boolean large;

  Individual(String name, int hp, int ac, boolean big, Weapon wpn)
  {
    this.name = name;
    this.hp = hp;
    this.ac = ac;
    this.large = big;
    this.wpn = wpn;
    initiative = 0; // must call newInitiative() prior to sort
    if (((float)wpn.attacks / (float)wpn.rounds) < 1.0F)
      attacks = 1.0F; // everyone attacks in first round of combat
    else
      attacks = 0.0F;
  }

  boolean isLarge()
  {
    return large;
  }

  boolean isDead()
  {
    return (hp <= 0);
  }

  boolean canAttack()
  {
    if ((attacks >= 1.0F) && (hp > 0))
    {
      attacks -= 1.0F;
      return true;
    }
    else
    {
      return false;
    }
  }

  boolean canAttackAgain()
  {
    return ((attacks >= 1.0F) && (hp > 0));
  }

  boolean hasOpponent()
  {
    // override in subclass
    return false;
  }

  String getOpponent()
  {
    // override in subclass
    return "nobody.";
  }

  int getAC(boolean flank)
  {
    return ac;
  }

  int attack(Random rand)
  {
    // override in subclass
    return -1;
  }

  void takeThat(int damage)
  {
    // override in subclass
    hp -= damage;
  }

  void newInitiative(int d10)
  {
    // The application must generate a random number from 1 to 10
    // (the initiative roll) to calculate individual initiative
    initiative = d10 + wpn.speed;
    if (attacks < 1.0F) // if individual has already attacked
      attacks += (float)wpn.attacks / (float)wpn.rounds; // usually 1.0
  }

  public int compareTo(Object o)
  {
    // sort in Ascending order to determine who acts first
    Individual ind = (Individual) o;
    if (equals(ind))
      return 0;
    else if (initiative < ind.initiative)
      return -1;
    else
      return 1;
  }

  public boolean equals(Object o)
  {
    if (!(o instanceof Individual))
      return false;
    return ((Individual)o).initiative == initiative;
  }

  public int hashcode()
  {
    // if a.equals(b) then a.hashcode == b.hashcode
    return initiative;
  }

  public String toString()
  {
    // override in the base class
    return name;
  }
}