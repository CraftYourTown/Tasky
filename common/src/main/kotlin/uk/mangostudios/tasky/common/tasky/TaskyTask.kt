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

import uk.mangostudios.tasky.api.TaskyAPI
import uk.mangostudios.tasky.api.tasky.Reward
import uk.mangostudios.tasky.api.tasky.TaskyUser
import uk.mangostudios.tasky.common.messages.Message
import java.util.UUID
import kotlin.reflect.KClass
import uk.mangostudios.tasky.api.tasky.TaskyTask as ITaskyTask

public fun <E : Any> taskyTask(clazz: KClass<E>, block: MutableTaskyTask<E>.() -> Unit): TaskyTask<E> {
    return MutableTaskyTask(clazz).apply(block).build()
}

public class TaskyTask<E : Any>(
    override val clazz: Class<E>,
    override val rewards: List<Reward>,
    override val winCondition: (E) -> Boolean,
    override val player: (E) -> UUID,
    override var startTime: Long?,
    public val startMessage: Message,
    public val endMessage: Message,
    public val winMessage: Message
) : ITaskyTask<E> {

    override fun start() {
        startTime = System.currentTimeMillis()
        TaskyAPI.tasky.audience.sendMessage(startMessage)
    }

    override fun end() {
        TaskyAPI.tasky.audience.sendMessage(endMessage)
    }

    override fun giveRewards(user: TaskyUser, reward: Reward) {
        TaskyAPI.tasky.giveRewards(user, reward)
        TaskyAPI.tasky.audience.sendMessage(winMessage)
        user.mutate {
            wins++
        }
    }
}

public class MutableTaskyTask<E : Any>(private val clazz: KClass<E>) {
    public var winCondition: ((E) -> Boolean)? = null
    public var player: ((E) -> UUID)? = null
    public var rewards: List<Reward>? = null
    public var startMessage: Message? = null
    public var endMessage: Message? = null
    public var winMessage: Message? = null

    public fun build(): TaskyTask<E> {
        return TaskyTask(
            clazz.java,
            rewards!!,
            winCondition!!,
            player!!,
            null,
            startMessage!!,
            endMessage!!,
            winMessage!!
        ).also {
            TaskyTaskManager.tasks.add(it)
        }
    }
}
