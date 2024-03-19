package com.example.notes.data.service

import android.content.ClipData
import android.content.ClipboardManager
import javax.inject.Inject

class ClipboardService @Inject constructor(
    private val clipboardManager: ClipboardManager
) {
    fun copyNoteIntoClipboard(note: String?) {
        val data: ClipData = ClipData.newPlainText(NOTE_LABEL, note)
        clipboardManager.setPrimaryClip(data)
    }
    companion object{
        private const val NOTE_LABEL = "Note"
    }
}