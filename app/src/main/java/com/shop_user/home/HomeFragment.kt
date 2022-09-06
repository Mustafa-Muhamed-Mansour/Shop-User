package com.shop_user.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.shop_user.adapter.SellerAdapter
import com.shop_user.databinding.FragmentHomeBinding
import com.shop_user.interfaces.ClickedOnSeller
import com.shop_user.model.SellerModel
import com.shop_user.productOfSeller.ProductOfSellerFragment

class HomeFragment : Fragment(), ClickedOnSeller
{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sellerAdapter: SellerAdapter
    private lateinit var sellerModel: ArrayList<SellerModel>
    private lateinit var retSellerReference: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initView()
        initDatabase()

    }

    private fun initView()
    {
        sellerModel = ArrayList()
    }

    private fun initDatabase()
    {
        binding.loadingSeller.visibility = View.VISIBLE
        retSellerReference = FirebaseDatabase.getInstance().reference
        retSellerReference
            .child("Sellers")
            .addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    sellerModel.clear()

                    binding.loadingSeller.visibility = View.GONE

                    if (snapshot.exists())
                    {
                        for (snap in snapshot.children)
                        {
                            val model = snap.getValue(SellerModel::class.java)
                            sellerModel.add(model!!)
                        }

                        sellerAdapter = SellerAdapter(sellerModel, this@HomeFragment)
                        binding.rV.adapter = sellerAdapter
                        binding.rV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        binding.rV.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    }

                    else
                    {
                        binding.loadingSeller.visibility = View.GONE
                        Toast.makeText(requireContext(), "مفيش حاجة علشان أعرضها", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    binding.loadingSeller.visibility = View.GONE
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun clickedOnSeller(sellerModel: SellerModel)
    {
        val data = HomeFragmentDirections.actionHomeFragmentToProductOfSellerFragment(sellerModel)
        Navigation.findNavController(requireView()).navigate(data)
    }
}