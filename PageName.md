The existing 2nd edition code defines

```
public interface Individual
{
  String name;
  D20Box initiative;
  int currentInit;
  Weapon wpn;
  int maxHP, currentHP;
  int ac, touchAC, flatAC;
  D20Box reflex, fortitude, will;
}

public interface Weapon
{
  CriticalD20Box toHit;
  DiceBox damage;
  int critMult;
}

// this is the class that calls Random and returns the result
public class DiceBox
{
  public DiceBox(int numDie, int dieSize, int modifier)

  int roll()
}

public class D20Box extends DiceBox
{
  public D20Box(int modifier)

  boolean wasNatural20()

  boolean wasNatural1()
}

public class CriticalD20Box extends D20Box
{
  public CriticalDiceBox(int modifier, int threat)

  boolean wasThreat()
}
```