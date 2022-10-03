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

package uk.mangostudios.tasky.common.command

import cloud.commandframework.annotations.AnnotationParser
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.exceptions.InvalidCommandSenderException
import cloud.commandframework.exceptions.InvalidSyntaxException
import cloud.commandframework.minecraft.extras.AudienceProvider
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler
import cloud.commandframework.minecraft.extras.MinecraftHelp
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.util.ComponentMessageThrowable
import uk.mangostudios.tasky.common.config.CommandConfig
import uk.mangostudios.tasky.common.config.internal.ConfigManager
import cloud.commandframework.CommandManager as CloudCommandManager

public interface CommandManager {

    public val commandManager: CloudCommandManager<Commander>
    public val annotationParser: AnnotationParser<Commander>

    public fun withCommands(vararg commands: Any): CommandManager {
        commands.forEach { command -> annotationParser.parse(command) }
        return this
    }

    public fun init(): CommandManager {
        val minecraftHelp = MinecraftHelp(
            "/tasky help",
            AudienceProvider.nativeAudience(),
            commandManager
        ).apply {
            val config = ConfigManager[CommandConfig::class]

            helpColors = MinecraftHelp.HelpColors.of(
                TextColor.fromHexString(config.help.colorPrimary)!!,
                TextColor.fromHexString(config.help.colorHighlight)!!,
                TextColor.fromHexString(config.help.colorAlternateHighlight)!!,
                TextColor.fromHexString(config.help.colorText)!!,
                TextColor.fromHexString(config.help.colorAccent)!!
            )
            setMaxResultsPerPage(config.help.maxResultsPerPage)
            setHeaderFooterLength(config.help.headerFooterLength)
            setMessage(MinecraftHelp.MESSAGE_HELP_TITLE, config.help.messageHelpTitle)
            setMessage(MinecraftHelp.MESSAGE_COMMAND, config.help.messageCommand)
            setMessage(MinecraftHelp.MESSAGE_DESCRIPTION, config.help.messageDescription)
            setMessage(MinecraftHelp.MESSAGE_NO_DESCRIPTION, config.help.messageNoDescription)
            setMessage(MinecraftHelp.MESSAGE_ARGUMENTS, config.help.messageArguments)
            setMessage(MinecraftHelp.MESSAGE_OPTIONAL, config.help.messageOptional)
            setMessage(MinecraftHelp.MESSAGE_SHOWING_RESULTS_FOR_QUERY, config.help.messageShowingResultsForQuery)
            setMessage(MinecraftHelp.MESSAGE_NO_RESULTS_FOR_QUERY, config.help.messageNoResultsForQuery)
            setMessage(MinecraftHelp.MESSAGE_AVAILABLE_COMMANDS, config.help.messageAvailableCommands)
            setMessage(MinecraftHelp.MESSAGE_CLICK_TO_SHOW_HELP, config.help.messageClickToShowHelp)
            setMessage(MinecraftHelp.MESSAGE_PAGE_OUT_OF_RANGE, config.help.messagePageOutOfRange)
            setMessage(MinecraftHelp.MESSAGE_CLICK_FOR_NEXT_PAGE, config.help.messageClickForNextPage)
            setMessage(MinecraftHelp.MESSAGE_CLICK_FOR_PREVIOUS_PAGE, config.help.messageClickForPreviousPage)
        }

        commandManager.command(
            commandManager.commandBuilder("tasky")
                .permission("tasky.help")
                .literal("help")
                .argument(StringArgument.newBuilder<Commander?>("query").greedy().asOptionalWithDefault("").build())
                .handler { context ->
                    minecraftHelp.queryCommands(
                        context.getOrDefault("query", "")!!,
                        context.sender
                    )
                }
        )

        MinecraftExceptionHandler<Commander>()
            .withHandler(MinecraftExceptionHandler.ExceptionType.INVALID_SYNTAX) { e ->
                ConfigManager[CommandConfig::class]
                    .exceptions
                    .invalidSyntax
                    .asComponent(Placeholder.unparsed("syntax", String.format("/%s", (e as InvalidSyntaxException).correctSyntax)))
            }
            .withHandler(MinecraftExceptionHandler.ExceptionType.INVALID_SENDER) { e ->
                ConfigManager[CommandConfig::class]
                    .exceptions
                    .invalidSender
                    .asComponent(Placeholder.unparsed("sender", (e as InvalidCommandSenderException).requiredSender.simpleName))
            }
            .withHandler(MinecraftExceptionHandler.ExceptionType.NO_PERMISSION) { e ->
                ConfigManager[CommandConfig::class]
                    .exceptions
                    .noPermission
                    .asComponent()
            }
            .withHandler(MinecraftExceptionHandler.ExceptionType.ARGUMENT_PARSING) { e ->
                ConfigManager[CommandConfig::class]
                    .exceptions
                    .invalidArgument
                    .asComponent(Placeholder.component("argument", ComponentMessageThrowable.getOrConvertMessage(e) ?: NULL))
            }

        return this
    }

    private companion object {
        private val NULL = Component.text("null")
    }
}
