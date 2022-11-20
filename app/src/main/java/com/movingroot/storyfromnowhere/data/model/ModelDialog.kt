package com.movingroot.storyfromnowhere.data.model

class ModelDialog {
    private val title: String
    private val content: String
    private val confirm: (() -> Unit)?

    constructor(_title: String, _content: String, _confirm: (() -> Unit)? = null) {
        title = _title
        content = _content
        confirm = _confirm
    }
}