package com.example.myapplication.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R
import com.example.myapplication.taskdetail.view.TaskDetailFragment
import com.example.myapplication.taskform.view.TaskFormFragment

class NavigationPerformerImpl(
    private val activity: FragmentActivity
) : NavigationPerformer {
    override fun navigateTo(navigationModel: NavigationModel) {
        when (navigationModel) {
            NavigationModel.CreateTask -> navigateToTaskForm()
            NavigationModel.NavigateBack -> navigateBack()
            is NavigationModel.TaskClick -> navigateToTaskDetail(navigationModel.taskId)
        }
    }

    private fun navigateBack() {
        if (activity.supportFragmentManager.fragments.size > 1) {
            activity.supportFragmentManager.popBackStack()
        } else {
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToTaskForm() {
        navigateToFragment(TaskFormFragment())
    }

    private fun navigateToTaskDetail(taskId: String) {
        val fragment = TaskDetailFragment().apply {
            arguments = bundleOf(VIEW_MODEL_ARGUMENTS to taskId)
        }
        navigateToFragment(fragment)
    }

    private fun navigateToFragment(fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view,  fragment)
            .addToBackStack("")
            .commit()
    }
}