package com.greydog.fitness

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import com.greydog.extensions.inflateBinding
import com.greydog.fitness.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflateBinding<LoginFragmentBinding>(R.layout.login_fragment, container) { it.vm = viewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Doing this here as I have access to the context. Could have used an
        //AndroidViewModel instead of a ViewModel which also give access to the
        //context
        viewModel.token.observe(viewLifecycleOwner, Observer {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            var editor = prefs.edit()
            editor.putString("token", viewModel.token.value)

            //hard coding user id but would get this off the login response and save
            editor.putInt("userid", 12345)
            editor.commit()
        })

        viewModel.loginSuccess.observe(this, Observer { success ->
            if (success) {
                findNavController().navigate(R.id.action_loginfragment_to_stepsfragment)
            }
        })
    }

}