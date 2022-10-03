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

package uk.mangostudios.tasky.api

import net.kyori.adventure.audience.Audience
import uk.mangostudios.tasky.api.storage.TaskyUserService
import uk.mangostudios.tasky.api.tasky.Reward
import uk.mangostudios.tasky.api.tasky.TaskyUser

/**
 * Houses all public facing and platform dependent related functionality.
 *
 * @param P the platform specific Player.
 */
public interface Tasky<P> {

    public val taskyUserService: TaskyUserService
    public val audience: Audience

    public fun init() {
        TaskyAPI.tasky = this
    }

    /**
     * Gives a [Reward] to a [TaskyUser].
     *
     * @param user The [TaskyUser] to give the reward to.
     * @param reward The [Reward] to give.
     */
    public fun giveRewards(user: TaskyUser, reward: Reward)

    /**
     * Checks if player is in creative.
     *
     * @param player The player to check.
     * @return true if player is in creative.
     */
    public fun isInCreative(player: P): Boolean

    /**
     * Gets the count of online players.
     *
     * @return The count of online players.
     */
    public fun onlinePlayers(): Int
}
