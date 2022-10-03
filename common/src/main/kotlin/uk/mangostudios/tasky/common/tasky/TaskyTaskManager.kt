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

package uk.mangostudios.tasky.common.tasky

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import uk.mangostudios.tasky.api.TaskyAPI
import uk.mangostudios.tasky.common.config.TasksConfig
import uk.mangostudios.tasky.common.config.internal.ConfigManager

public abstract class TaskyTaskManager<E : Any> {

    public companion object {
        public val tasks: MutableList<TaskyTask<*>> = mutableListOf()
    }

    protected var currentTask: TaskyTask<*>? = null

    init {
        CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                val config = ConfigManager[TasksConfig::class]
                currentTask = if (currentTask == null) tasks.random() else currentTask

                currentTask?.let {
                    if (it.startTime == null && config.minimumPlayers > TaskyAPI.tasky.onlinePlayers()) {
                        it.start()
                    } else {
                        it.startTime?.let { startTime ->
                            if (startTime + config.autoEndTime.toMillis() > System.currentTimeMillis()) {
                                it.end()
                                currentTask = null
                                delay(config.timeBetweenGames.toMillis())
                            }
                        }
                    }
                }

                delay(1_000L)
            }
        }
    }

    public abstract fun start(task: TaskyTask<E>)
    public abstract fun end(task: TaskyTask<E>)
}
