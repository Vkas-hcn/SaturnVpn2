package com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var binding: DB
    protected lateinit var viewModel: VM

    abstract val layoutId: Int
    abstract val viewModelClass: Class<VM>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(viewModelClass)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    customizeReturnKey()
                }
            })
    }
    abstract fun setupViews()

    abstract fun observeViewModel()

    abstract fun customizeReturnKey()

    fun navigateTo(actionId: Int) {
        try {
            findNavController().navigate(actionId)
        } catch (e: IllegalArgumentException) {
            Log.e("BaseFragment", "Navigation action not found: $actionId", e)
        }
    }
    fun navigateTo(actionId: Int, callback: (() -> Unit)? = null) {
        try {
            val navController = findNavController()

            // 设置一个返回结果监听器，如果需要回调
            if (callback != null) {
                val backStackEntry = navController.getBackStackEntry(navController.graph.id)
                backStackEntry.savedStateHandle.set("callback_key", true)

                backStackEntry.savedStateHandle.getLiveData<Boolean>("callback_key")
                    .observe(backStackEntry) { result ->
                        if (result == true) {
                            callback.invoke()
                            // 清除结果，防止重复触发
                            backStackEntry.savedStateHandle.remove<Boolean>("callback_key")
                        }
                    }
            }

            navController.navigate(actionId)
        } catch (e: IllegalArgumentException) {
            Log.e("BaseFragment", "Navigation action not found: $actionId", e)
        }
    }


    fun navigateToBack(){
        findNavController().popBackStack()
    }


    protected fun navigateTo(actionId: Int, bundle: Bundle) {
        findNavController().navigate(actionId, bundle)
    }

    protected fun showCustomDialog(message: String, title: String = "Info") {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .create()
        dialog.show()
    }
}
