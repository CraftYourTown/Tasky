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

package uk.mangostudios.tasky.common.scripting

import java.io.File
import java.util.logging.Logger
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

internal object TaskyScriptHost {

    private val logger = Logger.getLogger("Tasky/ScriptHost")

    fun evalFile(scriptFile: File) {
        logger.info("Evaluating tasky script: ${scriptFile.name}")
        val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<TaskyScript>()
        val res = BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, null)

        res.reports.forEach {
            if (it.severity > ScriptDiagnostic.Severity.DEBUG) {
                logger.info(" : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
            }
        }
    }
}
