package com.anji.babydiary.common

import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics
import android.util.Log
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.security.MessageDigest


class MyTransformation : BitmapTransformation {


    enum class CornerType {
        NONE,
        ALL,
        TOP,
        BOTTOM,
    }


    var radius: Float = 0F
    lateinit var cornerType: CornerType;

    constructor(context: Context, radius: Float) {
        //super(context);
        this.radius = radius;
        this.cornerType = CornerType.NONE;
    }

    constructor(context: Context, radius: Float, cornerType: CornerType) {
        //super(context);
        this.radius = radius;
        this.cornerType = cornerType;
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {


    }


    /**
     *
     * @param bitmapPool
     * An interface for a pool that allows users to reuse Bitmap objects.
     *
     * @param original
     * Glide로 받아온 이미지
     *
     * @param width
     * ImageView의 넓이
     *
     * @param height
     * ImageView의 높이
     *
     */


    override fun transform(
        bitmapPool: BitmapPool,
        original: Bitmap,
        width: Int,
        height: Int
    ): Bitmap {
        Log.d("MyTag", "imageView 사이즈 width : " + width + " , height : " + height); // imageView 사이즈

        var orgWidth: Int = original.getWidth();
        var orgHeight: Int = original.getHeight();

        Log.d(
            "MyTag",
            "받아온 이미지 사이즈 orgWidth : " + orgWidth + " , orgHeight : " + orgHeight
        ); // 받아온 이미지의 사이즈

        var scaleX: Float = (width / orgWidth) as Float
        var scaleY: Float = (height / orgHeight) as Float

        var scaledWidth: Float;
        var scaledHeight: Float;

        if (orgWidth >= orgHeight) {
            scaledWidth = scaleY * orgWidth;
            scaledHeight = height as Float;
        } else {
            scaledWidth = width as Float;
            scaledHeight = scaleX * orgHeight;
        }

        Log.d(
            "MyTag",
            "스케일 사이즈 scaledWidth : " + scaledWidth + " , scaledHeight : " + scaledHeight
        );

        var result: Bitmap = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        //캔버스 준비
        var canvas: Canvas = Canvas(result);

        //크레파스 준비
        var paint = Paint();
        paint.setAntiAlias(true);
        //paint.setColor(0xff424242);

        //모서리가 둥근 사각형(destination) 그리기
        when (cornerType) {

            CornerType.ALL -> {

                var rectF = RectF(0.toFloat(), 0.toFloat(), width.toFloat(), height.toFloat());
                canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint);

                //SRC_IN -> source 이미지가 destination 이미지를 덮습니다.
                paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                ;
            }

            CornerType.TOP -> {

                var rect = Rect(0, 0, width, height);
                var rectF = RectF(rect);

                canvas.drawRoundRect(rectF, radius, radius, paint);

                //Fill in bottom corner
                var bottomRect = Rect(0, height / 2, width, height);
                canvas.drawRect(bottomRect, paint);

                //SRC_IN -> source 이미지가 destination 이미지를 덮습니다.
                paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            }

            CornerType.BOTTOM -> {

                var rect = Rect(0, 0, width, height);
                var rectF = RectF(rect);

                canvas.drawRoundRect(rectF, radius, radius, paint);

                //Fill in top corner
                var topRect = Rect(0, 0, width, height / 2);
                canvas.drawRect(topRect, paint);

                //SRC_IN -> source 이미지가 destination 이미지를 덮습니다.
                paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            }


        }


        //블랙펜서 비트맵(source) 그리기
        var targetRect = RectF(0.toFloat(), 0.toFloat(), scaledWidth, scaledHeight);
        canvas.drawBitmap(original, null, targetRect, paint);

        return result;

    }
}



    /**
     * 다른 기능일 경우 ID 가 달라야 합니다.
    @Override
    public String getId() {
        return "id : " + System.currentTimeMillis();
    }
        */
