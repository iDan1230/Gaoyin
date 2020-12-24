package com.idan.frame.widget.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.idan.frame.R
import com.idan.frame.ktx.e
import kotlinx.android.synthetic.main.dialog_loading.view.*

/**
 * Date:2020/9/27
 * User:yangzhidan
 * Remark:加载框，待完善。需要增加显示错误时的样式
 */
class Loading(val defaultContent: String? = null) : DialogFragment() {

    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        root = inflater.inflate(R.layout.dialog_loading, container)

        if (!defaultContent.isNullOrEmpty()) {
            root?.loading?.text = defaultContent
        }

        root?.btnConfirm?.setOnClickListener {
            dismissAllowingStateLoss()
        }
        return root
    }

    fun setMessage(msg: String) {
        root?.run {
            loading.text = msg
            progressBar.visibility = View.GONE
            btnConfirm.visibility = View.VISIBLE
        }
    }

    fun show(manager: FragmentManager) {
        show(manager, "loading")
    }

    override fun show(manager: FragmentManager, tag: String?) {
        root?.run {
            loading.text = "加载中.."
            progressBar.visibility = View.VISIBLE
            btnConfirm.visibility = View.GONE
        }
        try {
            super.show(manager, tag)
        } catch (e: IllegalStateException) {
            "loading catch ------------>".e()
            e.printStackTrace()
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        }
    }
}