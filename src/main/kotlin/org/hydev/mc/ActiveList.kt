package org.hydev.mc

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.ceil

/**
 * TODO: Write a description for this class!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2021-03-07 09:24
 */
class ActiveList : JavaPlugin(), CommandExecutor
{
    override fun onEnable()
    {
        super.onEnable()

        getCommand("activelist").executor = this
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, _args: Array<out String>): Boolean
    {
        sender.sendMessage(command(_args.toMutableList()))
        return true
    }

    private fun command(args: MutableList<String>): String
    {
        val dirPlugins = dataFolder.parent
        val dir = File(dirPlugins, "Essentials/userdata")

        // Get args
        var reversed = false
        if (args.contains("-r"))
        {
            reversed = true
            args.remove("-r")
        }
        val page = args.getOrNull(0)?.toIntOrNull() ?: 1
        val pageSize = args.getOrNull(1)?.toIntOrNull() ?: 10

        // Read user dates
        var list = dir.listFiles()?.filter { it.name.toLowerCase().endsWith("yml") }?.mapNotNull {
            try
            {
                val yml = YamlConfiguration.loadConfiguration(it)
                val name = yml.getString("lastAccountName")
                val logoutDate = yml.getLong("timestamps.logout")
                val money = yml.getString("money") ?: ""

                User(name, Date(logoutDate), money)
            }
            catch (e: Exception) { e.printStackTrace(); null }
        }

        // Create result
        val pageMax = ceil(list.size.toDouble() / pageSize).toInt()
        var result = "§3 ActiveList - Page: $page/$pageMax, PageSize: $pageSize \n" +
            "§7|§f %-16s §7|§f %-10s §7|§f %-16s §7|\n".format("Username", "Last Login", "Money")

        // Sort
        list = if (reversed) list.sortedBy { it.date }
            else list.sortedByDescending{ it.date }

        // Paginate
        val start = (page - 1) * pageSize
        if (start >= list.size) return "Error: No entries on this page".red()
        list = list.subList(start, (start + pageSize).coerceAtMost(list.size))

        // Add to result
        result += list.joinToString("\n") {
            "§7|§b %-16s §7|§e %-10s §7|§a %-16s §7|".format(
                it.name,
                it.date.format("yyyy-MM-dd"),
                it.money
            )
        }

        return result
    }
}

data class User(val name: String, val date: Date, val money: String)

fun String.red() = "§c$this"
fun Date.format(f: String = "yyyy-MM-dd"): String
{
    val date = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return date.format(DateTimeFormatter.ofPattern(f))
}
