# Mc Forge 1.8.9 Example
An example project for Forge 1.8.9 that can be used as a template with useful code snippets and step by step guides in the [Wiki section](https://github.com/doej1367/McForge189Sample/wiki) of this repository. The main reason being that there apparently is no documentation or official support for 1.8.9 anymore, despite it being widely used on the Hypixel network.

## Features
- `/debugview` command that uses the waypoint render method from [moulberry's NEU mod](https://github.com/Moulberry/NotEnoughUpdates/blob/master/src/main/java/io/github/moulberry/notenoughupdates/core/util/render/RenderUtils.java) to display waypoints to entities that are visible to the forge client (excludes players)
    - `/debugview` - shows all entity waypoints
    - `/debugview off` - hides all entity waypoints
    - `/debugview <name>` - shows all entity waypoints that contain the provided arguments in their name. For example `/debugview Squid Chicken` would show all squids and chickens in the players mob render distance.

## The Vision
The mod is ment to provide the main code blocks for many client side only features, provide all the necessary tools and show how to get information about enitiys, blocks, sidebars, chat, chest guis and all the other surroundings. Once all that information has been collected it should be displayed in an overlay fashion. Overlay elements in the sidebar category should be easily rearrangable. This would **make this mod a good starting point for every beginner** who wants to develop a Forge 1.8.9 overlay mod for the Hypixel server.

## Credits / Sources / Inspiration
Great mods created by cool people and partly how I leared how to code Forge 1.8.9 mods without the existance of official support or documentation:
- [NotEnoughUpdates](https://github.com/Moulberry/NotEnoughUpdates) by Moulberry (and many more)
- [SkyblockAddons](https://github.com/BiscuitDevelopment/SkyblockAddons) by Biscuit (and many more)
- [SkytilsMod](https://github.com/Skytils/SkytilsMod) by My-Name-Is-Jeff and Sychic
- [Cowlection](https://github.com/cow-mc/Cowlection) by Cow
- [SkyblockPersonalized](https://github.com/Cobble8/SkyblockPersonalized/) by Cobble8
