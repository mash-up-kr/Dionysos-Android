package com.mashup.dionysos

import android.graphics.BitmapFactory
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File


@BindingAdapter("android:bind_url")
fun bindUrl(view: ImageView, imageUrl: String?) {
  if (imageUrl != null) {
    Glide.with(view.context).load(imageUrl)
      .placeholder(R.drawable.ic_launcher_foreground)
      .error(R.drawable.ic_launcher_background)
      .into(view)
  }
}

@BindingAdapter("android:bind_int")
fun bindResource(view: ImageView, image: Int) {
  view.setImageResource(image)
}

@BindingAdapter("android:image_path")
fun bindIamgePath(view: ImageView, string: String?) {
  try {
    val imgFile = File(string)
    if (imgFile.exists()) {
      val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
      view.setImageBitmap(myBitmap)
    }
  } catch (e: Error) {
    view.setImageResource(R.drawable.icon_app)
  }
}

@BindingAdapter("app:front")
fun bindTimeLaps(view: com.mashup.dionysos.ui.timelapse.CameraPreview, int: Int) {
  view.mCameraFacing = int
}

@BindingAdapter("android:text_line")
fun bindTextUnderLine(view: TextView, string: String) {
  val content = SpannableString(string)
  content.setSpan(UnderlineSpan(), 0, content.length, 0);
  view.text = content
}