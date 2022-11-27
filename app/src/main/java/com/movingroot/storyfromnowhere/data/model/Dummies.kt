package com.movingroot.storyfromnowhere.data.model

object Dummies {
    fun makeDummyUser() = User("", "", "", "")

    fun makeDummyPost() = Post("", "", 0, listOf(), "", "2022-11-21", null)

    fun makeDummyLabel() = Label("", "", "", "")
}