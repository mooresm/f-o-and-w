[existing FO&W 2 code](PageName.md) defines the minimum functionality for a useful implementation.<br>
simplest idea: read in two PCGen characters and have them fight to the death, toe to toe<br>
<br>
<ol><li>read stats from file<br>
<ul><li>hard-coded file format<br>
</li><li>PCGen <code>*</code>.pcg (3.<i>x</i>)<br>
</li><li>Wizards <a href='http://code.google.com/p/cbloader/'>Character Builder</a> (4th)<br>
</li></ul></li><li>display to user<br>
<ul><li>eg. PCGen DMtools encounter tracker GUI<br>
</li></ul></li><li>roll initiative<br>
</li><li>resolve combat rounds<br>
<ol><li>are both opponents still conscious?<br>
</li><li>in initiative order:<br>
<ol><li>roll to-hit<br>
</li><li>if to-hit >= AC, roll damage<br>
</li><li>subtract hit points</li></ol></li></ol></li></ol>

<h3>Random Terrain</h3>
Populate each 5ft square according to random probabilities in chapter 3 of the DMG 3.5: "Wilderness Adventures"<br>
<ul><li>random encounter distance<br>
</li><li>forest, desert, mountain, ...<br>
</li><li>aquatic or aerial combat: in 3D</li></ul>

Use a <a href='http://en.wikipedia.org/wiki/Markov_random_field'>Markov random field</a> to make neighbouring terrain more homogeneous.<br>
<br>
Don't need to generate the entire world map all at once - just the immediate surroundings. Can "lay down the tracks in front of the train": generate new terrain as the characters move, to keep one step ahead.<br>
<br>
Random wilderness encounters (table 3-25)<br>
<ul><li><a href='http://monsterfinder.dndrunde.de/'>D&amp;D monster finder</a></li></ul>

<h3>Random Dungeons</h3>

An idea that was used in Diablo and Elder Scrolls: Daggerfall<br>
<br>
Random Maze Generator:<br>
<a href='http://www.charm.net/~shack/java/maze.html'>http://www.charm.net/~shack/java/maze.html</a>

<a href='http://www.bin.sh/gaming/tools/dungeon.cgi'>http://www.bin.sh/gaming/tools/dungeon.cgi</a>

<a href='http://en.wikipedia.org/wiki/Maze_generation_algorithm'>http://en.wikipedia.org/wiki/Maze_generation_algorithm</a>
<ul><li>Depth-First Search<br>
</li><li>Kruskal's algorithm<br>
</li><li>Prim's algorithm<br>
<a href='http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap'>http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap</a></li></ul>

To begin with, assume that each room of the maze is a 5ft square and that more than one occupant cannot occupy the same square simultaneously<br>
<br>
Insert each character at random points in the random maze. The game ends when the first person reaches the exit, or when there is only one person left alive. Each turn, each character either:<br>
<ol><li>if the character is the only individual left alive, the game ends.<br>
</li><li>if the character is in an adjacent square to the exit, the game ends.<br>
</li><li>moves one square to the left, right or straight ahead (depending on whether there are passageways or walls in those directions) or turns around (if they have arrived at a dead end)<br>
<ul><li>if there are only two possible directions, the character moves away from the square that it occupied in the previous round (ie. avoid backtracking)<br>
</li></ul></li><li>attacks an adjacent character (what about monsters? traps?)</li></ol>

A more sophisticated game (<a href='http://www.d20srd.org/'>RSRD</a>):<br>
<blockquote>movement rate (30ft per round, or double move, 5ft step)<br>
time dilation<br>
<ul><li>from realtime (6sec/round) to fast forward (0 delay)<br>
</li></ul>torchlight, lanterns or magic improve visibility<br>
AoO, tumble<br>
party of 4 characters occupies a 10ft square (each room in the random maze is 10ft x 10ft, with doorways 5ft wide...)<br>
<ul><li>configurable marching order, eg. mage in the middle, fighters on the front line, rogue   scouting ahead, etc.<br>
</li></ul>populate the maze with random monsters and traps (and other dungeon features, eg. difficult terrain) according to the average party level<br>
<ul><li>new monsters might wander in from outside</li></ul></blockquote>

Usable by DMs as a mass combat simulator<br>
<ul><li>define 2 (or more?) factions<br>
</li><li>paint your terrain<br>
</li><li>let it rip!<br>
<ul><li>optional interactive mode?<br>
</li><li>scriptable AI?</li></ul></li></ul>

import PCGen characters<br>
<a href='http://www.pcgen-test.org/wiki/index.php?title=Building_PCGen'>http://www.pcgen-test.org/wiki/index.php?title=Building_PCGen</a> <br>
<a href='http://www.pcgen-test.org/wiki/index.php?title=MAVEN'>http://www.pcgen-test.org/wiki/index.php?title=MAVEN</a>
<ul><li>specify location of PCGen jar file<br>
</li><li>specify location of LST sources, as well as which sources to load into memory</li></ul>

GemRB Game Engine (open source re-implementation of BioWare's Infinity engine, 1998-2002)<br>
<ul><li>2nd edition D&D<br>
<a href='http://linux.prinas.si/gemrb/doku.php'>http://linux.prinas.si/gemrb/doku.php</a> <br>
<a href='https://sourceforge.net/project/showfiles.php?group_id=10122'>https://sourceforge.net/project/showfiles.php?group_id=10122</a> <br>
<a href='http://www.moddb.com/mods/gemrb-the-infinity-engine-clone'>http://www.moddb.com/mods/gemrb-the-infinity-engine-clone</a></li></ul>

support for feats and spellcasting (incl. arcane spell failure)<br>
<ul><li>8h rest to recover spells<br>
hide, move silently <br>
diplomacy, intimidation</li></ul>

save/load maze data file (which includes the inhabitants of the maze) <br>
export maze inhabitant as a PCGen character<br>
<br>
web server provides a low-key way to check on the progress of your digital persona<br>
<ul><li>customizable CSS to make it look like you're reading something work-related<br>
</li><li>subscribe to event notifications via twitter, email or IM<br>
<ul><li>level-up, negative hit points, 50% hits, character death, ...<br>
</li></ul></li><li>hosted as a FaceBook/MySpace application</li></ul>

memory requirements & CPU<br>
<ul><li>can tune the time dilation factor to reduce CPU usage<br>
</li><li>also depends on realtime display; frequency of webpage refresh<br>
blow-by-blow logging of every move (log4j DEBUG)</li></ul>

AI:<br>
<ul><li>select which weapon/feat/spell to use each round, whether to power attack, etc.<br>
<ul><li>Bayesian prior expectation of attack effectiveness, updated after each combat round<br>
</li><li>statistical similarity to other monsters previously encountered<br>
<ul><li>tactics vs. humanoid (goblin) spellcaster, or humanoid (orc) archer<br>
</li><li>AI randomly selects different attack option each round, according to probability of doing the most damage<br>
</li><li>AI should only be as intelligent as the monster! (zombies, vermin, oozes ...)<br>
</li></ul></li><li>tactics: goblin horde, undead legion, military units, ...<br>
</li><li>different preferences for "rogue" types, spellcasters, etc.<br>
</li></ul></li><li>maze solving algorithm (keeps track of which squares in the maze have already been visited)<br>
</li><li>fight/flight (remember where enemies were last seen?)<br>
</li><li>scout ahead and then run back to the rest of the party when trouble is encountered<br>
</li><li>inventory management (kill it and take its treasure, but must remain unencumbered - pick which armor to wear, etc.)</li></ul>

Inspiration:<br>
<ul><li>IdleRPG<br>
</li><li>The Sims<br>
</li><li>D&D: Tiny Adventures<br>
</li><li>Arena of Death