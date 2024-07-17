package com.example.trackie_fyp.models

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trackie_fyp.utils.extractTotalAmountAndDate
import com.example.trackie_fyp.utils.normalizeText
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReceiptViewModel(application: Application) : AndroidViewModel(application) {
    val ocrResult = MutableLiveData<List<String>>()
    private val _totalAmount = MutableLiveData<Double?>()
    val totalAmount: LiveData<Double?> get() = _totalAmount
    private val _receiptDate = MutableLiveData<String?>()
    val receiptDate: LiveData<String?> get() = _receiptDate
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun processImage(bitmap: Bitmap) {
        Log.d("ReceiptViewModel", "Processing image for OCR")
        val image = InputImage.fromBitmap(bitmap, 0)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val items = visionText.textBlocks.flatMap { it.lines.map { line -> normalizeText(line.text) } }
                Log.d("ReceiptViewModel", "OCR successful, items: $items")
                ocrResult.postValue(items)
                viewModelScope.launch(Dispatchers.Default) {
                    val (amount, date) = extractTotalAmountAndDate(items)
                    _totalAmount.postValue(amount)
                    _receiptDate.postValue(date)
                    if (amount == null && date == null) {
                        _error.postValue("No total amount or date found. Please try scanning the receipt again.")
                    } else {
                        _error.postValue(null)
                    }
                    Log.d("ReceiptViewModel", "Extracted total amount: $amount, Extracted date: $date")
                }
            }
            .addOnFailureListener { e ->
                Log.e("ReceiptViewModel", "OCR failed", e)
                e.printStackTrace()
                _error.postValue("OCR processing failed. Please try again.")
            }
    }

    fun resetTotalAmount() {
        _totalAmount.postValue(null)
        _receiptDate.postValue(null)
        _error.postValue(null)
    }
}




