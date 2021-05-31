package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.lang.NumberFormatException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

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

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val textMin:EditText = view.findViewById(R.id.min_value)
        val textMax:EditText=view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            val min:Int
            val max:Int
            try {
                min=textMin.text.toString().toInt()
            }catch (nfe: NumberFormatException){
                Snackbar.make(view,"Неверное значение для нижней границы.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            try {
                max=textMax.text.toString().toInt()
            }catch (nfe: NumberFormatException){
                Snackbar.make(view,"Неверное значение для верхней границы.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if(min>=max){
                Snackbar.make(view,"Нижняя граница должна быть меньше верхней.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            fun Fragment.mainActivity() = requireActivity() as MainActivity
            mainActivity().openSecondFragment(min, max)
        }
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