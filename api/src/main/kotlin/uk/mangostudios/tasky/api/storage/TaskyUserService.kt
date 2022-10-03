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

package uk.mangostudios.tasky.api.storage

import kotlinx.coroutines.Deferred
import uk.mangostudios.tasky.api.tasky.TaskyUser
import java.util.UUID

public interface TaskyUserService {

    public val cache: MutableMap<UUID, TaskyUser>

    public suspend fun save(user: TaskyUser)
    public suspend fun save(users: List<TaskyUser>)
    public fun getAsync(uuid: UUID): Deferred<TaskyUser?>
    public fun addToCache(user: TaskyUser)
    public fun removeFromCache(uuid: UUID)

    public operator fun get(uuid: UUID): TaskyUser {
        return cache[uuid] ?: throw IllegalArgumentException("User not in cache")
    }
}
