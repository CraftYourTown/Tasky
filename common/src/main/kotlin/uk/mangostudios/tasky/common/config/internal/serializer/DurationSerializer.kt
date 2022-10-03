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

package uk.mangostudios.tasky.common.config.internal.serializer

import org.spongepowered.configurate.serialize.ScalarSerializer
import java.lang.reflect.Type
import java.time.Duration
import java.util.function.Predicate
import java.util.regex.Pattern

internal object DurationSerializer : ScalarSerializer<Duration>(Duration::class.java) {

    private val pattern = Pattern.compile("(([1-9][0-9]+|[1-9])[dhms])")

    override fun deserialize(type: Type?, obj: Any?): Duration {
        val value = obj.toString().lowercase()
        val matcher = pattern.matcher(value)
        var duration = Duration.ofNanos(0)

        while (matcher.find()) {
            val group = matcher.group()
            val timeUnit = group[group.length - 1].toString()
            val timeValue = group.substring(0, group.length - 1).toInt()
            duration = when (timeUnit) {
                "d" -> duration.plusDays(timeValue.toLong())
                "h" -> duration.plusHours(timeValue.toLong())
                "m" -> duration.plusMinutes(timeValue.toLong())
                "s" -> duration.plusSeconds(timeValue.toLong())
                else -> throw IllegalArgumentException("Invalid duration character. Use format 1d2h3m4s")
            }
        }

        return duration
    }

    override fun serialize(item: Duration?, typeSupported: Predicate<Class<*>>?): Any {
        return item.toString().substring(2)
    }
}
