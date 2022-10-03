/*
 * #######
 *    #      ##    ####  #    # #   #
 *    #     #  #  #      #   #   # #
 *    #    #    #  ####  ####     #
 *    #    ######      # #  #     #
 *    #    #    # #    # #   #    #
 *    #    #    #  ####  #    #   #
 *
 * Copyright (C) 2022 Mango Studios Limited.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package uk.mangostudios.tasky.common.config

import net.kyori.adventure.text.format.NamedTextColor
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Setting
import uk.mangostudios.tasky.common.messages.message

@ConfigSerializable
internal class CommandConfig {
    @Setting
    var help = Help()
    @Setting
    var exceptions = Exceptions()

    @ConfigSerializable
    inner class Help {
        @Setting
        var headerFooterLength = 46
        @Setting
        var maxResultsPerPage = 6
        @Setting
        var colorPrimary = NamedTextColor.GOLD.asHexString()
        @Setting
        var colorHighlight = NamedTextColor.GREEN.asHexString()
        @Setting
        var colorAlternateHighlight = NamedTextColor.YELLOW.asHexString()
        @Setting
        var colorText = NamedTextColor.GRAY.asHexString()
        @Setting
        var colorAccent = NamedTextColor.DARK_GRAY.asHexString()
        @Setting
        var messageHelpTitle = "Help"
        @Setting
        var messageCommand = "Command"
        @Setting
        var messageDescription = "Description"
        @Setting
        var messageNoDescription = "No description"
        @Setting
        var messageArguments = "Arguments"
        @Setting
        var messageOptional = "Optional"
        @Setting
        var messageShowingResultsForQuery = "Showing search results for query"
        @Setting
        var messageNoResultsForQuery = "No results for query"
        @Setting
        var messageAvailableCommands = "Available commands"
        @Setting
        var messageClickToShowHelp = "Click to show help for this command"
        @Setting
        var messagePageOutOfRange = "Error: Page<page> is not in range. Must be in range [1, <max_pages]"
        @Setting
        var messageClickForNextPage = "Click for next page"
        @Setting
        var messageClickForPreviousPage = "Click for previous page"
    }

    @ConfigSerializable
    inner class Exceptions {
        @Setting
        var invalidSyntax = message {
            message = "<red>Invalid command syntax. Correct command syntax is: <gray><syntax>"
        }
        @Setting
        var invalidSender = message {
            message = "<red>Invalid command sender. You must be of type <gray><sender>"
        }
        @Setting
        var noPermission = message {
            message = "<red>I'm sorry, but you do not have permission to perform this command. <br>Please contact the server administrators if you believe that this is an error."
        }
        @Setting
        var invalidArgument = message {
            message = "<red>Invalid command argument: <gray><argument>"
        }
    }
}
