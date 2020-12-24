package com.idan.frame.model

sealed class State

object LoadState : State()

object Success : State()

class Failed(val fail: String) : State()

object Finish : State()