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

package uk.mangostudios.tasky.common.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uk.mangostudios.tasky.api.tasky.TaskyUser
import java.util.UUID
import uk.mangostudios.tasky.api.storage.TaskyUserService as UserService

public class TaskyUserService : UserService {

    override val cache: MutableMap<UUID, TaskyUser> = mutableMapOf()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                cache.values
                    .filter { it.dirty }
                    .apply {
                        launch {
                            save(this@apply)
                        }
                    }
                delay(30_000L)
            }
        }
    }

    override fun addToCache(user: TaskyUser) {
        cache[user.uuid] = user
    }

    override fun removeFromCache(uuid: UUID) {
        val user = get(uuid)
        runBlocking {
            launch {
                save(user)
            }
        }
        cache.remove(user.uuid)
    }

    override suspend fun save(user: TaskyUser) {
        TaskyUserRepository.save(user)
    }

    override suspend fun save(users: List<TaskyUser>) {
        TaskyUserRepository.saveAll(users)
    }

    override fun getAsync(uuid: UUID): Deferred<TaskyUser?> {
        return runBlocking {
            async {
                TaskyUserRepository.findById(uuid)
            }
        }
    }
}
