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

/**
 * Handles the storage of [TaskyUser]s.
 *
 * @property cache The online users.
 */
public interface TaskyUserService {

    public val cache: MutableMap<UUID, TaskyUser>

    /**
     * Saves a [TaskyUser] to the database.
     *
     * @param user The user to save.
     */
    public suspend fun save(user: TaskyUser)

    /**
     * Saves multiple [TaskyUser]s to the database.
     *
     * @param users The users to save.
     */
    public suspend fun save(users: List<TaskyUser>)

    /**
     * Gets a [TaskyUser] from the database.
     *
     * @param uuid The UUID of the user to get.
     * @return The user.
     */
    public fun getAsync(uuid: UUID): Deferred<TaskyUser?>

    /**
     * Adds a [TaskyUser] to the cache.
     */
    public fun addToCache(user: TaskyUser)

    /**
     * Removes a [TaskyUser] from the cache.
     */
    public fun removeFromCache(uuid: UUID)

    /**
     * Gets a [TaskyUser] from the cache.
     *
     * @param uuid The UUID of the user to get.
     * @return The user.
     */
    public operator fun get(uuid: UUID): TaskyUser {
        return cache[uuid] ?: throw IllegalArgumentException("User not in cache")
    }
}
