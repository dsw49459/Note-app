/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.crys.gymrat.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object NoteList

    @Serializable
    object AddNote

    @Serializable
    data class NoteDetail(val noteId: String)

    @Serializable
    object Auth
}
