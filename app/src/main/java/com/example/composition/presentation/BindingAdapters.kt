package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entities.GameResult

interface OnOptionClickListener{
    fun onOptionClick(option : Int)
}

@BindingAdapter("emojiResult")
fun emojiResult(imageView: ImageView, winner: Boolean){
    imageView.setImageResource(setUpImage(winner))
}

private fun setUpImage(winner: Boolean): Int{
    return if(winner){
        R.drawable.ic_smile
    }else{
        R.drawable.ic_sad
    }
}

@BindingAdapter("requiredAnswer")
fun requiredAnswers(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_score), count)
}

@BindingAdapter("scoreAnswers")
fun scoreAnswers(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers), count)
}

@BindingAdapter("requiredPercentage")
fun requiredPercentage(textView: TextView, percent: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage), percent)
}

@BindingAdapter("scorePercentage")
fun scorePercentage(textView: TextView, gameResult: GameResult){
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage), getPercentOfRightAnswers(gameResult))
}

private fun getPercentOfRightAnswers(gameResult: GameResult): Int{
    with(gameResult){
        if (countOfQuestions == 0){
            return 0
        }else{
            return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }
}

@BindingAdapter("answersProgress")
fun answersProgress(textView: TextView, isEnough: Boolean){
    textView.setTextColor(setColor(textView.context, isEnough))
}

@BindingAdapter("progressBarColor")
fun progressBarColor(progressBar: ProgressBar, isEnough: Boolean){
    progressBar.progressTintList = ColorStateList.valueOf(setColor(progressBar.context, isEnough))
}

private fun setColor(context: Context, isEnough: Boolean): Int{
    val colorResId = if (isEnough){
        android.R.color.holo_green_light
    }else{
        android.R.color.holo_red_light
    }
    val color = ContextCompat.getColor(context, colorResId)
    return color
}

@BindingAdapter("numberToText")
fun numberToText(textView: TextView, number: Int){
    textView.text = number.toString()
}

@BindingAdapter("onOptionClick")
fun onOptionClick(textView: TextView, clickListener: OnOptionClickListener){
    textView.setOnClickListener { clickListener.onOptionClick(textView.text.toString().toInt()) }
}
