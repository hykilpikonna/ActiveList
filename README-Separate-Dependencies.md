### What's the advantage of using separated dependencies?

The plugin's jar size is **way** smaller (1.8 MB → 12 KB)

### How to use this plugin with separated dependencies:

1. Copy plugin jar to `./plugins`
2. Copy dependency jars to `./libs`
3. Change your start script from `java -jar <server.jar> nogui` to...
   *  For CraftBukkit / Spigot: `java -cp "<server.jar>:./libs/*" org.bukkit.craftbukkit.Main nogui` 
   * For PaperMC / Purpur: `java -cp "<server.jar>:./libs/*" -javaagent:<server.jar> io.papermc.paperclip.Paperclip nogui`

* Note: You should replace the `:` with `;` in the start script if you are using Windows.
* Note: Replace `<server.jar>` with the name of your CraftBukkit or Spigot jar file.

