package com.googlecode.FOandW;

import java.util.Random;

class Weapon
{
  String name;
  int attacks;
  int rounds;
  int THAC0;
  int speed;
  int dSMnum, dSMdie, dSMplus;
  int dLnum, dLdie, dLplus;

  public int strike(Random rand, Individual i, boolean flank)
  {
    // to-hit roll
    int d20roll = rand.nextInt(20) + 1;
    if (d20roll < (THAC0 - i.getAC(flank)))
    {
      return -1; // Missed!
    }

    // calculate damage
    int damage;
    if (i.isLarge())
    {
      damage = dLplus;
      for (int ix=0; ix<dLnum; ix++)
      {
        damage += rand.nextInt(dLdie) + 1;
      }
    }
    else
    {
      damage = dSMplus;
      for (int ix=0; ix<dSMnum; ix++)
      {
        damage += rand.nextInt(dSMdie) + 1;
      }
    }
    i.takeThat(damage);
    return damage;
  }
}