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
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.pag.n_satou.refrigeratorstocker.databinding.FragmentPhotoBinding
import kotlinx.android.synthetic.main.fragment_photo.*
import java.util.*
import java.util.regex.Pattern


/**
 * Created by N-Satou on 2018/05/12.
 */
class PhotoFragment: Fragment(), TabProvider {

    private lateinit var binding: FragmentPhotoBinding
    private lateinit var befavior: BottomSheetBehavior<NestedScrollView>

    val pattern = Pattern.compile("\\d{1,4}.\\d{1,2}.\\d{1,2}|\\d{1,4}/\\d{1,2}/\\d{1,2}")

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
                        if (binding.takePicture.alpha == 1.0f) {
                            binding.takePicture.animate().alpha(0.0f).start()
                        }
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        if (binding.takePicture.alpha == 1.0f) {
                            binding.takePicture.animate().alpha(0.0f).start()
                        }
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        if (binding.takePicture.alpha == 0.0f) {
                            binding.takePicture.animate().alpha(1.0f).start()
                        }
                    }
                }
            }
        } )

        binding.takePicture.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            binding.cameraView.captureImage {
                it.bitmap?.let {
                    val image = FirebaseVisionImage.fromBitmap(it)

                    FirebaseVision.getInstance().visionTextDetector.detectInImage(image)
                            .addOnSuccessListener {
                                analysisText(it)?.let {
                                    showBottomSheet(it)
                                    progressBar.visibility = View.GONE
                                }
                            }
                            .addOnFailureListener {
                                failureAnalysis(it)
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

    private fun analysisText(text: FirebaseVisionText): Date? {
        for (block in text.blocks) {
            for (line in block.lines) {
                val lineTxt = line.text
                Log.d("■", lineTxt)

                val m = pattern.matcher(lineTxt.replace(" ", ""))
                if (m.find()) {
                    val analysisTxt = m.group()
                    Log.d("■■■■■■■■■■", analysisTxt)

                    return DateFormat.strToDate(analysisTxt)
                }
            }
        }
        failureAnalysis(AnalysisNoItemException("NO Items"))
        return null
    }

    private fun failureAnalysis(throwable: Throwable) {
        progressBar.visibility = View.GONE
    }

    private fun showBottomSheet(date: Date) {
        val dateStr = DateFormat.dateToString(date)
        binding.dateText.text = dateStr
        befavior.state = BottomSheetBehavior.STATE_EXPANDED
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