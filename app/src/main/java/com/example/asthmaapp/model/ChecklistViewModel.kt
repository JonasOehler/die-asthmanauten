package com.example.asthmaapp.model

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

class ChecklistViewModel(application: Application) : AndroidViewModel(application) {
    var todos = mutableStateListOf<Todo>()
        private set

    init {
        loadTodosFromAssets()
    }

    fun loadTodosFromAssets() {
        try {
            val context = getApplication<Application>().applicationContext
            val jsonString = context.assets.open("checklist.json")
                .bufferedReader().use { it.readText() }
            val loadedTodos: List<Todo> = Json.decodeFromString(jsonString)
            todos.clear()
            todos.addAll(loadedTodos)
        } catch (e: IOException) {
            todos.clear()
        }
    }

    fun updateTodo(updated: Todo) {
        val index = todos.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            todos[index] = updated
        }
    }

    fun deleteTodo(id: String) {
        todos.removeAll { it.id == id }
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
    }

    fun toggleDone(todo: Todo) {
        val index = todos.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            val old = todos[index]
            todos[index] = old.copy(done = !old.done)
        }
    }

    fun saveTodosToFile() {
        try {
            val context = getApplication<Application>().applicationContext
            val jsonString = Json.encodeToString(todos)
            context.openFileOutput("checklist.json", 0).use { it.write(jsonString.toByteArray()) }
        } catch (e: IOException) {
            // Fehler beim Speichern
        }
    }
}
