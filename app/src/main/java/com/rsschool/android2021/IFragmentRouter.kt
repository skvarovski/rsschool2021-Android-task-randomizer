package com.rsschool.android2021

// Интерфейс "роутер", в котором заданы фрагменты для открытия
interface IFragmentRouter {
    fun openSecondFragment(min:Int,max:Int)
    fun openFirstFragment(calculate:Int)
}