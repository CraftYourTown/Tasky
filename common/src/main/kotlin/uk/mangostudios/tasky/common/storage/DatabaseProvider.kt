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

import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig
import uk.mangostudios.tasky.common.config.StorageConfig
import uk.mangostudios.tasky.common.config.internal.ConfigManager

internal class DatabaseProvider {

    val database: Database

    init {
        val config = DatabaseConfig().apply {
            dataSourceConfig = DataSourceConfig().apply {
                val config = ConfigManager[StorageConfig::class]
                url = config.url
                username = config.username
                password = config.password
            }
        }
        this.database = DatabaseFactory.create(config)
    }
}
