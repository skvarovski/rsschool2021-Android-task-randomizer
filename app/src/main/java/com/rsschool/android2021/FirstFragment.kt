package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigDecimal
import kotlin.math.min

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null
    private var toast: Toast? = null
    private var router: IFragmentRouter? = null

    // Кастуем переменую
    override fun onAttach(context: Context) {
        super.onAttach(context)
        router = activity as IFragmentRouter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // Реализуем действия при клике, с проверками
        generateButton?.setOnClickListener {
            val min = minValue?.text?.trim().toString()
            val max = maxValue?.text?.trim().toString()

            // проверки на "кривые" данные
            when {
                min.isEmpty() -> makeToast("Min Value is empty!")
                max.isEmpty() -> makeToast("Max value is empty!")
                BigDecimal(min) > BigDecimal(Int.MAX_VALUE) -> makeToast("Min so max value")
                BigDecimal(max) > BigDecimal(Int.MAX_VALUE) -> makeToast("Max so max value")
                min.toInt() > max.toInt() -> makeToast("MAX not max of MIN")
                min.toInt() < 0 -> makeToast("Min so down of zero")
                max.toInt() < 0 -> makeToast("Max so down of zero")

                else -> {
                    router?.openSecondFragment(min.toInt(),max.toInt())
                }
            }
        }
    }

    // Вспомогательный метод для оповещения ошибок
    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }

    override fun onDetach() {
        super.onDetach()
        router = null
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}