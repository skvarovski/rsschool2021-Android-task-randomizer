package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment(), OnBackListener  {

    private var backButton: Button? = null
    private var result: TextView? = null
    private var router: IFragmentRouter? = null

    // Кастуем переменную для команд
    override fun onAttach(context: Context) {
        super.onAttach(context)
        router = activity as IFragmentRouter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result?.text = generate(min, max).toString()

        backButton?.setOnClickListener {
            val calculate = result?.text.toString().toInt()
            Log.d("TEST",calculate.toString())
            router?.openFirstFragment(calculate)
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return (min..max).random()
    }

    // Вешаем реализацию интерфейса для хардверной кнопки Back
    override fun doBack() {
        val calculate = result?.text.toString().toInt()
        router?.openFirstFragment(calculate)
    }

    // Обнуляем в случае ухода из фрагмента
    override fun onDetach() {
        super.onDetach()
        router = null
    }

    // Реализация статики, для вызова с параметрами (в обход конструктора)
    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}