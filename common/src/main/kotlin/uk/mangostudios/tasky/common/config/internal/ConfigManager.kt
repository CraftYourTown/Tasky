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

package uk.mangostudios.tasky.common.config.internal

import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists
import kotlin.reflect.KClass

internal class ConfigManager(private val dataDirectory: Path) : AutoCloseable {

    companion object {
        val configs: MutableMap<Class<*>, ConfigHandler<*>> = mutableMapOf()

        operator fun <T : Any> get(clazz: KClass<T>): T {
            return configs[clazz.java]?.getConfig() as T
        }
    }

    fun initConfigs(vararg clazz: KClass<*>) {
        if (!dataDirectory.exists()) {
            dataDirectory.createDirectory()
        }

        clazz.forEach {
            configs[it.java] = ConfigHandler(it.java, dataDirectory)
        }
    }

    override fun close() {
        configs.values.forEach { it.close() }
    }
}
