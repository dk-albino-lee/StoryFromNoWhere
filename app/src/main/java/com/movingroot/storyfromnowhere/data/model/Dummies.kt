package com.movingroot.storyfromnowhere.data.model

object Dummies {
    fun makeDummyUser() = User("", "", "", "")

    fun makeDummyPost() = Post("", "", 0, "", "", null)

    fun makeDummyLabel() = Label("", "", "", "")
}