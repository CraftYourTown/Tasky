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

package uk.mangostudios.tasky.api.tasky

import java.util.UUID

/**
 * A tasky user.
 *
 * @property uuid The user's UUID.
 * @property name The user's name.
 * @property wins The user's amount of wins.
 * @property dirty Whether the user's data has been modified.
 */
public interface TaskyUser {

    public val uuid: UUID
    public var name: String
    public var wins: Int
    public var dirty: Boolean

    /**
     * Mutates the user and marks it as dirty.
     *
     * @param block The mutation.
     * @return The mutated user.
     */
    public fun mutate(block: TaskyUser.() -> Unit): TaskyUser {
        block(this)
        this.dirty = true
        return this
    }
}
