package com.example.goongoonaloAssignment

import com.example.goongoonaloAssignment.DestinationsArgs.TASK_ID_ARG
import com.example.goongoonaloAssignment.DestinationsArgs.TITLE_ARG
import com.example.goongoonaloAssignment.DestinationsArgs.USER_MESSAGE_ARG
import com.example.goongoonaloAssignment.Screens.ADD_EDIT_TASK_SCREEN
import com.example.goongoonaloAssignment.Screens.STATISTICS_SCREEN
import com.example.goongoonaloAssignment.Screens.TASKS_SCREEN
import com.example.goongoonaloAssignment.Screens.TASK_DETAIL_SCREEN

private object Screens {
    const val TASKS_SCREEN = "tasks"
    const val STATISTICS_SCREEN = "statistics"
    const val TASK_DETAIL_SCREEN = "task"
    const val ADD_EDIT_TASK_SCREEN = "addEditTask"
}

object DestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}

object Destinations {
    const val TASKS_ROUTE = "$TASKS_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val STATISTICS_ROUTE = STATISTICS_SCREEN
    const val TASK_DETAIL_ROUTE = "$TASK_DETAIL_SCREEN/{$TASK_ID_ARG}"
    const val ADD_EDIT_TASK_ROUTE = "$ADD_EDIT_TASK_SCREEN/{$TITLE_ARG}?$TASK_ID_ARG={$TASK_ID_ARG}"
}