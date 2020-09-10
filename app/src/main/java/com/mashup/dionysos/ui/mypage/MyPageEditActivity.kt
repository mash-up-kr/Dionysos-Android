package com.mashup.dionysos.ui.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mashup.dionysos.R
import com.mashup.dionysos.api.dto.ReqEditNickName
import com.mashup.dionysos.base.activity.BaseActivity
import com.mashup.dionysos.databinding.ActivityMyPageEditBinding
import com.mashup.dionysos.ui.main.TimeViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_page_edit.*

class MyPageEditActivity : BaseActivity<ActivityMyPageEditBinding>(R.layout.activity_my_page_edit) {
    companion object {
        private const val TAG = "MY_PAGE_EDIT_ACTIVIVY"
    }

    lateinit var timeViewModel: TimeViewModel
    lateinit var myPageViewModel: MyPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMainDataBinding()
        Log.e(TAG, ":  onCreateView")
        editNickName()
        nickname_edit.afterTextChanged {
            checkSaveBtn(it)
        }
        saveButton.setOnClickListener {
            editNickName(nickname_edit.text.toString())
        }
        back.setOnClickListener {
            finish()
        }

    }

    private fun checkSaveBtn(text: String) {
        if (text.isNotEmpty() &&
            text != myPageViewModel.userInfo.value?.nickname ?: ""
        ) {
            saveButton.setTextColor(ContextCompat.getColor(this, R.color.azure))
        } else {
            saveButton.setTextColor(ContextCompat.getColor(this, R.color.light_blue_grey))
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun editNickName(name: String?) {
        name?.let {
            repository.reqEditNickName(ReqEditNickName(name))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    myPageViewModel.userInfo.value = it.result
                    Log.e(TAG, ": editNickName success")
                    finish()
                }, { e ->
                    e.printStackTrace()
                })
        }
    }

    private fun editNickName() {
        repository.reqGetNickName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("editNickName", "success $it")
                myPageViewModel.userInfo.value = it.result
            }, { e ->
                e.printStackTrace()
            })
    }

    private fun initMainDataBinding() {
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timeViewModel = ViewModelProvider(this, viewModelFactory).get(TimeViewModel::class.java)
        myPageViewModel = ViewModelProvider(this, viewModelFactory).get(MyPageViewModel::class.java)
        binding.timeVM = timeViewModel
        binding.mypageVM = myPageViewModel
    }
}