libraryaddict's ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Theme:__ How can movement/travel be improved? and How can combat/weapons be improved?
- __Time:__ Time 1 (7/12/2014 00:00 to 7/12/2014 10:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ http://www.twitch.tv/redwarfare

---------------------------------------

Compilation
-----------

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/tenjava/libraryaddict-t1`
- Compile and create the plugin package using Maven: `mvn`

Maven will download all required dependencies and build a ready-for-use plugin package!

---------------------------------------

Usage
-----

The first step with the plugin is to drop it into your plugins folder!
When the plugin is first run. It will generate a config file you can edit!

The config file is very very simple. You can modify the values in it to change the costs of the runes!
The runes cost gold nuggets to cast them to provide a sense of balance.

Players in creative mode can bypass this cost.

To get the rune wand. Use /addwand to add it to your inventory.
Or /addwand <Player> to add it to someone elses inventory.

The rune wand is what you use to cast your runes.
You can select runes on it by sneaking and left/right clicking.
Once you have selected a rune, you left/right click to cast it.

All runes are cast at the location you right click, however some runes like to be placed within 10 blocks of you.

The runes are as follows

Defense: The damage players receive while inside this rune is minimized!

Exploding: The rune will explode after so and so seconds!

Healing: You are healed every so and so seconds!

Teleport: A rune is placed at your location and the destination. After a short wait, the teleport becomes active.

Trap: A rune is placed and hidden on the ground. Anything stepping on that rune will activate it with a explosion!

Wither Summoning: I got bored. This draws a pentagram on the ground, once finished. I use the blood of mbaxter to activate it.