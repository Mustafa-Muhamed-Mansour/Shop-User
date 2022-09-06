package com.shop_user.productOfSeller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.shop_user.R
import com.shop_user.adapter.ProductOfSellerAdapter
import com.shop_user.common.Constant
import com.shop_user.common.Validation
import com.shop_user.databinding.BottomSheetOrderBinding
import com.shop_user.databinding.FragmentProductOfSellerBinding
import com.shop_user.interfaces.ClickedOnProductOfSeller
import com.shop_user.model.OrderModel
import com.shop_user.model.ProductModel
import com.shop_user.model.UserModel

class ProductOfSellerFragment : Fragment(), ClickedOnProductOfSeller
{

    private lateinit var binding: FragmentProductOfSellerBinding
    private lateinit var sellerData: ProductOfSellerFragmentArgs
    private lateinit var auth: FirebaseAuth
    private lateinit var productOfSellerAdapter: ProductOfSellerAdapter
    private lateinit var productModel: ArrayList<ProductModel>
    private lateinit var retProductReference: DatabaseReference
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetOrderBinding: BottomSheetOrderBinding
    private lateinit var validation: Validation
    private lateinit var constant: Constant
    private lateinit var randomKey: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentProductOfSellerBinding.inflate(inflater, container, false)
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
        sellerData = ProductOfSellerFragmentArgs.fromBundle(requireArguments())
        Glide
            .with(requireContext())
            .load(sellerData.productOfSeller.image)
            .placeholder(R.drawable.ic_seller)
            .error(R.drawable.ic_seller)
            .into(binding.imgSeller)
        binding.txtNameSeller.text = sellerData.productOfSeller.name
        binding.txtPhoneSeller.text = sellerData.productOfSeller.phone
        productModel = ArrayList()
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.bottom_sheet_order, requireActivity().findViewById(R.id.relative_order), false)
        bottomSheetDialog.setContentView(bottomSheetOrderBinding.root)
        constant = Constant()
        validation = Validation()
    }


    private fun initDatabase()
    {
        binding.loadingProductOfSeller.visibility = View.VISIBLE
        randomKey = FirebaseDatabase.getInstance().reference.push().key.toString()
        auth = FirebaseAuth.getInstance()
        retProductReference = FirebaseDatabase.getInstance().reference
//        numberQuantity = FirebaseDatabase.getInstance().reference
        retProductReference
            .child("Sellers")
            .child(sellerData.productOfSeller.id)
            .child("Products")
            .addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    productModel.clear()

                    binding.loadingProductOfSeller.visibility = View.GONE

                    if (snapshot.exists())
                    {
                        for (snap in snapshot.children)
                        {
                            val model = snap.getValue(ProductModel::class.java)
                            productModel.add(model!!)
                        }

                        productOfSellerAdapter = ProductOfSellerAdapter(productModel, this@ProductOfSellerFragment)
                        binding.rVProduct.adapter = productOfSellerAdapter
                        binding.rVProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        binding.rVProduct.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    }

                    else
                    {
                        binding.loadingProductOfSeller.visibility = View.GONE
                        Toast.makeText(requireContext(), "مفيش حاجة علشان أعرضها", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun clickedOnProductOfSeller(productModel: ProductModel)
    {
        retProductReference
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
                            .into(bottomSheetOrderBinding.imgUserOrder)
                        bottomSheetOrderBinding.txtNameUser.text = model.name
                        bottomSheetOrderBinding.txtPhoneUser.text = model.phone
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

        bottomSheetOrderBinding.quantityNumberOfProduct.minValue = 0
        bottomSheetOrderBinding.quantityNumberOfProduct.maxValue = 10

        bottomSheetOrderBinding
            .btnSendOrder
            .setOnClickListener {

                if (bottomSheetOrderBinding.quantityNumberOfProduct.value == 0)
                {
                    validation.checkNumberOfQuantity(requireContext(), bottomSheetOrderBinding.quantityNumberOfProduct.value.toString())
                    return@setOnClickListener
                }

                else
                {
                    val orderModel = OrderModel(sellerData.productOfSeller.id, randomKey, bottomSheetOrderBinding.txtNameUser.text.toString(), bottomSheetOrderBinding.txtPhoneUser.text.toString(), productModel.image, productModel.title, productModel.price, bottomSheetOrderBinding.quantityNumberOfProduct.value.toString())

                    retProductReference
                        .child("Orders")
                        .child(sellerData.productOfSeller.id)
                        .child(randomKey)
                        .setValue(orderModel)

                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.dismiss()
                }
            }

        bottomSheetDialog.show()

    }

}