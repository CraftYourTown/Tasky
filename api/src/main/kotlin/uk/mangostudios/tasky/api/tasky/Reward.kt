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

import uk.mangostudios.tasky.api.reward.Weighted

/**
 * Creates a [Reward].
 *
 * @param command The command of the reward.
 * @param weight The weight of the reward.
 * @return The reward.
 */
public fun reward(command: String, weight: Double): Reward {
    return object : Reward {
        override val command = command
        override val weight = weight
    }
}

/**
 * A reward is a command that is executed when a task is completed.
 *
 * @property command The command to execute.
 * @property weight The weight of the reward.
 */
public interface Reward : Weighted {

    public val command: String
    public val weight: Double

    override fun weight(): Double {
        return weight
    }
}
