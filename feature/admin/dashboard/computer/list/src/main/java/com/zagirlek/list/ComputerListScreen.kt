package com.zagirlek.list

interface ComputerListScreen {
//    val model: StateFlow<ComputerListModel>
//    val dialog: Value<ChildSlot<*, AddOrEditComputerScreen>>
    fun addComputer()
    fun editComputer(userId: Long)
    fun hideAddOrEditComputerDialog()
    fun deleteComputer(userId: Long)
}