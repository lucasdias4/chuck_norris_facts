package com.lucasdias.home.pesentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucasdias.home.R

class UserFirstTimeFragment : Fragment() {

    companion object {
        fun newInstance() = UserFirstTimeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_first_time, container, false)
    }
}
