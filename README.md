# ShulkerRespawner 
This is a plugin that allows shulkers to respawn in the end.

It works by allowing any enderman that spawns on a purpur block to transform into a shulker,
so long as there are no other shulkers within a 10 block (configurable) radius.

The commands made available to the user are `/sr-distance`, `/sr-lang`, and `/sr-help`.
 - `/sr-distance` allows you to set the distance between shulkers.
 If an enderman spawns within that radius of another shulker, it won't transform into a shulker.
 `/sr-distance get` to get the current distance. `/sr-distance-set <number>` to set the distance.
 Set the distance to `0` if you want all endermen to turn into shulkers.
 - `/sr-lang` allows you to change the language.
 `/sr-lang list` to list the language options, then `/sr-lang set <code>` to set the language.
 - `/sr-help` allows you to get in-game help for the commands.
 `/sr-help <command>` shows you help for a specific command.

If you have any suggestions or bugs, open an issue or a pull request.

This project was created by  JoelGodOfwar, rewritten by solonovamax.