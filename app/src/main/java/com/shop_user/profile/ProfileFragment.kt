package com.shop_user.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.shop_user.R
import com.shop_user.databinding.FragmentProfileBinding
import com.shop_user.model.UserModel

class ProfileFragment : Fragment()
{

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var retProfileReference: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)




        initViews()
        initDatabase()
        clickedViews()


    }

    private fun initViews()
    {
    }

    private fun initDatabase()
    {
        auth = FirebaseAuth.getInstance()
        retProfileReference = FirebaseDatabase.getInstance().reference
        retProfileReference
            .child("Users")
            .child(auth.uid!!)
            .addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    if (snapshot.exists())
                    {
                        val model = snapshot.getValue(UserModel::class.java)

                        Glide
                            .with(requireContext())
                            .load(model!!.image)
                            .placeholder(R.drawable.ic_person)
                            .error(R.drawable.ic_person)
                            .into(binding.imgProfile)
                        binding.txtName.text = model.name
                        binding.txtPhone.text = model.phone
                    }

                    else
                    {
                        Toast.makeText(requireContext(), "مفيش حاجة علشان أعرضها", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun clickedViews()
    {
        binding
            .btnEdit
            .setOnClickListener {

                Toast.makeText(requireContext(), "Soon", Toast.LENGTH_SHORT).show()
            }

        binding
            .imgBtnLogout
            .setOnClickListener {

                Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_loginFragment)

            }
    }

}