package com.googlecode.FOandW;

import java.util.Random;
import java.util.Vector;
import java.util.Collections;
import javax.swing.JTextArea;

class GameData implements Runnable
{
  protected JTextArea debugLog;
  protected Random        rand; // the global random sequence
  protected Collections   coll; 
  protected Vector        vOpp; // Individuals, sorted by initiative
  protected Vector         vPC;  // player characters
  protected Vector        vMon; // monsters (summary information)

  public static final int D3 = 3;
  public static final int D4 = 4;
  public static final int D6 = 6;
  public static final int D8 = 8;
  public static final int D10 = 10;
  public static final int D12 = 12;
  public static final int D20 = 20;
  public static final int Dpc = 100;
  public String[] diceStrings = { "d3", "d4", "d6", "d8", "d10",
                                  "d12", "d20", "d100" };

  GameData(JTextArea disp)
  {
    debugLog = disp;
    rand = new Random(System.currentTimeMillis());
    vPC  = new Vector(5, 10);
    vMon = new Vector(5, 10);
    vOpp = new Vector(200, 200);
  }

  public synchronized int addPC(Player pc)
  {
    debugLog.append(pc.toString() + "\n");
    vPC.addElement(pc);
    vOpp.addElement(pc);
    return vPC.size() - 1;
  }

  public synchronized int addMonsters(MonsterSummary monst,
                                      int quantity,
                                      int hd,
                                      int plus,
                                      boolean big)
  {
    debugLog.append(monst.toString() + ": ");
    Vector d8rolls = new Vector(quantity*hd, quantity);
    for (int ix=0; ix<quantity*hd; ix++)
    {
      Integer i = new Integer(rand.nextInt(8) + 1);
      d8rolls.addElement(i);
    }
    monst.populate(quantity, hd, plus, d8rolls, big);
    vMon.addElement(monst);
    vOpp.addAll(monst.vMon);

    // dump output to debug log
    for (int ix=0; ix<monst.vMon.size(); ix++)
    {
      debugLog.append(monst.vMon.elementAt(ix).toString() + " ");
    }
    debugLog.append("\n");
    
    return vMon.size() - 1;
  }

  // pre: vOpp already contains the union of VPC & vMon[n].vMon
  public void run() // the game loop
  {
    int round_num = 1;
    do
    {
      Integer round_int = new Integer(round_num++);
      debugLog.append("   ### ROUND " + round_int.toString()
                      + " ###\n");

      // roll initiative and sort List
      for (int ix=0; ix<vOpp.size(); ix++)
      {
        Individual i = (Individual)vOpp.elementAt(ix);
        while (i.isDead() && (ix < vOpp.size()))
        {
          vOpp.remove(ix);
          if (ix < vOpp.size())
          {
            i = (Individual)vOpp.elementAt(ix);
          }
        }
        if (!i.isDead())
        {
          i.newInitiative(rand.nextInt(10) + 1);
        }
      }
      Collections.sort(vOpp); // lowest initiative roll first

    } while (gameLoop(vOpp));
    debugLog.append("game over.\n");
  }

  protected boolean findPC(Monster m)
  {
    for (int i=0; i<vPC.size(); i++)
    {
      Player p = (Player)vPC.elementAt(i);
      if (p.roomForMore())
      {
        p.addOpponent(m);
        m.addOpponent(p);
        return true;
      }
    }
    return false;
  }

  protected boolean findMon(Player p)
  {
    for (int i=0; i<vMon.size(); i++)
    {
      MonsterSummary ms = (MonsterSummary)vMon.elementAt(i);
      for (int j=0; j<ms.vMon.size(); j++)
      {
        Monster m = (Monster)ms.vMon.elementAt(j);
        if (!m.hasOpponent())
        {
          p.addOpponent(m);
          m.addOpponent(p);
          return true;
        }
      }
    }
    return false;
  }

  // this recursive method implements the actual combat mechanics
  protected boolean gameLoop(Vector guys)
  {
    boolean fighting = false;
    Vector vL8r = new Vector(10, 10); // multiple attacks/round

    for (int ix=0; ix<guys.size(); ix++)
    {
      // decide who this Individual will attack, if anyone
      Individual i = (Individual)guys.elementAt(ix);
      if (i.canAttack())
      {
        if (i.canAttackAgain())
        {
          vL8r.addElement(i);
        }
        if (!i.hasOpponent())
        {
          if ((i instanceof Player) && (!findMon((Player)i)))
          {
            continue;            // nobody for this Player to fight
          }
          else if ((i instanceof Monster) && (!findPC((Monster)i)))
          {
            continue;            // nobody for this Monster to fight
          }
        }
        fighting = true;

        // we know who we are fighting, so bash 'em
        String who = i.getOpponent();
        int damage = i.attack(rand);
        if (damage < 0)
        {
          debugLog.append("miss by " + i.toString() + "\n");
        }
        else
        {
          Integer dam = new Integer(damage);
          debugLog.append(dam.toString() + "hp damage to " + who
                          + " by " + i.toString() + "\n");
        }
      }
    }
    if (fighting && (vL8r.size() > 0))
    {
      gameLoop(vL8r);
    }
    return fighting;
  }
}