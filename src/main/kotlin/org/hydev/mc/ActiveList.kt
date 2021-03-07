package org.hydev.mc

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

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
        return true
    }
}

data class User(val name: String, val date: Date, val money: String)

fun Date.format(f: String = "yyyy-MM-dd"): String
{
    val date = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return date.format(DateTimeFormatter.ofPattern(f))
}
