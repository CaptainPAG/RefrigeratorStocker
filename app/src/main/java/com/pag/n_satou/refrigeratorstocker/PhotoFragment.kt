package com.pag.n_satou.refrigeratorstocker

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.pag.n_satou.refrigeratorstocker.databinding.FragmentPhotoBinding
import java.util.regex.Pattern


/**
 * Created by N-Satou on 2018/05/12.
 */
class PhotoFragment: Fragment(), TabProvider {

    private lateinit var binding: FragmentPhotoBinding
    private lateinit var befavior: BottomSheetBehavior<NestedScrollView>

    companion object {

        private var fragment: PhotoFragment? = null

        fun getInstance(): PhotoFragment {
            if (fragment == null) {
                fragment = PhotoFragment()
            }
            return fragment!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false)

        befavior = BottomSheetBehavior.from(binding.bottomSheet)
        befavior.state = BottomSheetBehavior.STATE_HIDDEN
        befavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.tranceView.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                }
            }
        } )

        binding.takePicture.setOnClickListener{
            binding.cameraView.captureImage {
                it.bitmap?.let {
                    val image = FirebaseVisionImage.fromBitmap(it)
                    val detector = FirebaseVision.getInstance().visionTextDetector

                    val p = Pattern.compile("\\d{1,4}.\\d{1,2}.\\d{1,2}|\\d{1,4}/\\d{1,2}/\\d{1,2}")
                    val result = detector.detectInImage(image)
                            .addOnSuccessListener {
                                for (block in it.blocks) {
                                    for (line in block.lines) {
                                        val lineTxt = line.text
                                        Log.d("■", lineTxt)

                                        lineTxt.replace(" ", "")
                                        val m = p.matcher(lineTxt)
                                        if (m.find()) {
                                            Log.d("■■■■■■■■■■", m.group())
                                        }

                                    }
                                }

                            }
                            .addOnFailureListener {

                            }
                }
            }
        }

        binding.oneMoreButton.setOnClickListener{
            befavior.state = BottomSheetBehavior.STATE_HIDDEN
            binding.cameraView.start()
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        binding.cameraView.stop()
    }

    override fun onPageSelected() {
        binding.cameraView.start()
    }

    override fun onPageUnSelected() {
        binding.cameraView.stop()
        befavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

}