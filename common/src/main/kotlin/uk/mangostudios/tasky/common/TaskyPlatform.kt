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

package uk.mangostudios.tasky.common

import uk.mangostudios.tasky.api.Tasky
import uk.mangostudios.tasky.common.command.CommandManager
import uk.mangostudios.tasky.common.config.CommandConfig
import uk.mangostudios.tasky.common.config.StorageConfig
import uk.mangostudios.tasky.common.config.TasksConfig
import uk.mangostudios.tasky.common.config.internal.ConfigManager
import uk.mangostudios.tasky.common.scripting.TaskyScriptHost
import uk.mangostudios.tasky.common.storage.TaskyUserService
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

public interface TaskyPlatform : Tasky {

    public var commandManager: CommandManager
    public val dataDirectory: Path
    override val taskyUserService: TaskyUserService

    public fun enable() {
        ConfigManager(dataDirectory).apply {
            initConfigs(CommandConfig::class, StorageConfig::class, TasksConfig::class)
        }

        val taskPath = dataDirectory.resolve("tasks")
        if (!taskPath.exists()) {
            taskPath.createDirectory()
        }

        taskPath.toFile()
            .listFiles { it -> it.endsWith(".tasky.kts") }
            ?.forEach(TaskyScriptHost::evalFile)

        onEnable()
    }

    public fun onEnable()
}
