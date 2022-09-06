package com.shop_user.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shop_user.R
import com.shop_user.common.Validation
import com.shop_user.databinding.FragmentRegisterBinding
import com.shop_user.model.UserModel

class RegisterFragment : Fragment()
{

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var validation: Validation
    private lateinit var auth: FirebaseAuth
    private lateinit var userReference: DatabaseReference
    private lateinit var userRef: StorageReference
    private lateinit var id: String
    private lateinit var image: String
    private lateinit var resultURI: Uri
    private lateinit var someLuncher: ActivityResultLauncher<Intent>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
        validation = Validation()
        someLuncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {

            val  dataIntent: Intent = it.data!!

            if (it.resultCode == Activity.RESULT_OK && dataIntent.data != null)
            {
                resultURI = dataIntent.data!!
                image = resultURI.toString()
                binding.imgUser.setImageURI(resultURI)
            }

            else
            {
                binding.loadingRegister.visibility = View.GONE
                Toast.makeText(requireContext(), dataIntent.data.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initDatabase()
    {
        auth = FirebaseAuth.getInstance()
        userReference = FirebaseDatabase.getInstance().reference
        id = FirebaseDatabase.getInstance().reference.push().key.toString()
        userRef = FirebaseStorage.getInstance().reference.child("Images").child("Images-Users").child(id)
    }

    private fun clickedViews()
    {
        binding
            .txtLogin
            .setOnClickListener {

                Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
            }

        binding
            .imgUser
            .setOnClickListener{

                openGallery()
            }

        binding
            .btnRegister
            .setOnClickListener {

                val email = binding.editEmail.text.toString()
                val name = binding.editName.text.toString()
                val password = binding.editPassword.text.toString()
                val phone = binding.editPhone.text.toString()

//                if (image == null)
//                {
//                    validation.checkImage(requireContext(), image)
//                    return@setOnClickListener
//                }

                if (TextUtils.isEmpty(email))
                {
                    validation.checkEmail(requireContext(), email)
                    binding.editEmail.requestFocus()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(name))
                {
                    validation.checkName(requireContext(), name)
                    binding.editName.requestFocus()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(password))
                {
                    validation.checkPassword(requireContext(), password)
                    binding.editPassword.requestFocus()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(phone))
                {
                    validation.checkPhone(requireContext(), phone)
                    binding.editPhone.requestFocus()
                    return@setOnClickListener
                }

                else
                {
                    registerSeller(image, email, name, password, phone)
                }
            }
    }

    private fun registerSeller(image: String, email: String, name: String, password: String, phone: String)
    {
        binding.loadingRegister.visibility = View.VISIBLE
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful)
                {
                    userRef
                        .putFile(Uri.parse(image))
                        .addOnCompleteListener{ task ->

                            if (task.isSuccessful)
                            {
                                userRef
                                    .downloadUrl
                                    .addOnSuccessListener { image ->

                                        val userModel = UserModel(auth.uid!!, id, image.toString(), email, name, phone)

                                        userReference
                                            .child("Users")
                                            .child(auth.uid!!)
                                            .setValue(userModel)

                                        binding.loadingRegister.visibility = View.GONE

                                        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)


                                    }.addOnFailureListener { ex ->

                                        binding.loadingRegister.visibility = View.GONE
                                        Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
                                    }
                            }

                            else
                            {
                                binding.loadingRegister.visibility = View.GONE
                                Toast.makeText(requireContext(), task.exception!!.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                }

                else
                {
                    binding.loadingRegister.visibility = View.GONE
                    Toast.makeText(requireContext(), it.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun openGallery()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        someLuncher.launch(intent)
    }
}