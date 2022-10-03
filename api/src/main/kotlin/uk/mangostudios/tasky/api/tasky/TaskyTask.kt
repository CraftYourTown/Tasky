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

import uk.mangostudios.tasky.api.reward.RandomSelector
import java.util.UUID

public interface TaskyTask<E : Any> {

    public val clazz: Class<E>
    public val rewards: List<Reward>
    public val winCondition: (E) -> Boolean
    public val player: (E) -> UUID
    public var startTime: Long?

    public fun start()
    public fun end()
    public fun giveRewards(user: TaskyUser, reward: Reward)
    public fun win(user: TaskyUser) {
        giveRewards(user, RandomSelector.weighted(rewards).pick())
    }
}
