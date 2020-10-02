package com.anji.babydiary.tips.webview

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anji.babydiary.R
import com.anji.babydiary.databinding.MapWebviewFragmentBinding
import kotlinx.android.synthetic.main.activity_main_feed.*


class MapWebview : Fragment() {

    companion object {
        fun newInstance() = MapWebview()
    }

    private val  REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
        //, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val PERMISSIONS_REQUEST_CODE = 8989

    private lateinit var viewModel: MapWebviewViewModel
    private var locationManager : LocationManager? = null

    private lateinit var binding:MapWebviewFragmentBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().fab.visibility = View.GONE


        binding = DataBindingUtil.inflate<MapWebviewFragmentBinding>(inflater, R.layout.map_webview_fragment, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.webview.getSettings().setUseWideViewPort(true)
        binding.webview.getSettings().setLoadWithOverviewMode(true)


        binding.webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE)

        binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
        binding.webview.getSettings().setSupportMultipleWindows(true)
        binding.webview.getSettings().setAllowFileAccess(true)
        binding.webview.getSettings().setDomStorageEnabled(true)
        binding.webview.getSettings().setPluginState(WebSettings.PluginState.ON)
        binding.webview.getSettings().setAllowContentAccess(true)
        binding.webview.getSettings().setAllowFileAccessFromFileURLs(true)
        binding.webview.getSettings().setAllowUniversalAccessFromFileURLs(true)
        binding.webview.getSettings().setJavaScriptEnabled(true)
        binding.webview.getSettings().setMediaPlaybackRequiresUserGesture(false)

        binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        binding.webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH)
        binding.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        binding.webview.getSettings().setEnableSmoothTransition(true)
        binding.webview.getSettings().setLoadsImagesAutomatically(true)
        binding.webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)

        WebView.setWebContentsDebuggingEnabled(true)

        // binding.webview.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());

        // binding.webview.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
        binding.webview.getSettings().setGeolocationEnabled(true)
        binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
        binding.webview.getSettings().setSupportMultipleWindows(true)

        binding.webview.getSettings().setAppCacheEnabled(false)
        binding.webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
        }
        binding.webview.setWebChromeClient(
            WebChromeClient()
        )
        binding.webview.setWebViewClient(
            WebViewClient()
        )

        val userAgent: String = binding.webview.getSettings().getUserAgentString()
        binding.webview.getSettings().setUserAgentString("$userAgent MobileApp ")

        //binding.webview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false);

        //binding.webview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false);
        binding.webview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true)
        binding.webview.getRendererPriorityWaivedWhenNotVisible()

        binding.webview.getSettings().setDatabaseEnabled(true)
        binding.webview.getSettings().setDomStorageEnabled(true)
        binding.webview.getSettings().setAppCacheEnabled(true)


        /*
        locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?;
        try {
            // Request location updates
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }

         */

        //checkRunTimePermission()
        checkRunTimePermission()

        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode === PERMISSIONS_REQUEST_CODE && grantResults.size === REQUIRED_PERMISSIONS.size) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true


            // 모든 퍼미션을 허용했는지 체크합니다.
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {

                locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?;
                try {
                    // Request location updates
                    locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
                } catch(ex: SecurityException) {
                    Log.d("myTag", "Security Exception, no location available");
                }


                binding.webview.loadUrl("https://www.google.co.kr/maps/search/%EB%B3%91%EC%9B%90/@37.5916738,126.9238116,15z/data=!3m1!4b1?hl=ko&authuser=0")

                //위치 값을 가져올 수 있음
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        REQUIRED_PERMISSIONS[0]
                    )
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        REQUIRED_PERMISSIONS[1]
                    )
                ) {
                    Toast.makeText(
                        requireContext(),
                        "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.",
                        Toast.LENGTH_LONG
                    ).show()
                    //finish()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.e("location", "" + location.longitude + ":" + location.latitude);

            binding.webview.loadUrl("https://www.google.co.kr/maps/search/%EB%B3%91%EC%9B%90/@${location.latitude},${location.longitude},15z/data=!3m1!4b1?hl=ko&authuser=0")
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.e("location", "" + "onStatusChanged");

        }
        override fun onProviderEnabled(provider: String) {
            Log.e("location", "" + "onProviderEnabled");

        }
        override fun onProviderDisabled(provider: String) {
            Log.e("location", "" + "onProviderDisabled");

        }
    }

    fun checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        Log.e("permission",
            (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED).toString()
        )
        Log.e("permission",
            (hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED).toString()
        )

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED
            //&& hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("myTag", "===========================================================");
            Log.e("myTag", "request location");
            Log.e("myTag", "===========================================================");
            locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?;
            try {
                // Request location updates
                locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
            } catch(ex: SecurityException) {
                Log.e("myTag", "===========================================================");
                Log.e("myTag", "Security Exception, no location available");
                ex.printStackTrace()
                Log.e("myTag", "===========================================================");
            }

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            /*
            locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?;
            try {
                // Request location updates
                locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available");
            }

             */
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE)

            /*
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    REQUIRED_PERMISSIONS
                )
            ) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(requireContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT)
                    .show()
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    requireActivity(), REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                Toast.makeText(requireContext(), "get permission.", Toast.LENGTH_SHORT)
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    requireActivity(), REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }

             */


        }
    }


}