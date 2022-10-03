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

import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.reference.ValueReference
import org.spongepowered.configurate.reference.WatchServiceListener
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import uk.mangostudios.tasky.common.config.internal.serializer.DurationSerializer
import java.nio.file.Path
import java.time.Duration
import java.util.logging.Logger

internal class ConfigHandler<T : Any>(clazz: Class<T>, dataFolder: Path) : AutoCloseable {

    companion object {
        private val logger = Logger.getLogger("Tasky/ConfigManager")
    }

    private val configFile = dataFolder.resolve("${clazz.simpleName}.yml")
    private val listener = WatchServiceListener.create()
    private val base = listener.listenToConfiguration(
        { file ->
            YamlConfigurationLoader.builder()
                .defaultOptions {
                    it.shouldCopyDefaults(true)
                        .serializers { serializers ->
                            serializers.register(Duration::class.java, DurationSerializer)
                        }
                }
                .nodeStyle(NodeStyle.BLOCK)
                .indent(2)
                .path(file)
                .build()
        },
        configFile
    )
    private val config: ValueReference<T, CommentedConfigurationNode> = base.referenceTo(clazz)

    init {
        listener.listenToFile(configFile) {
            logger.info("Updated config for ${configFile.fileName}")
        }
        base.save()
    }

    fun getConfig(): T {
        return config.get()!!
    }

    override fun close() {
        listener.close()
        base.close()
    }
}
