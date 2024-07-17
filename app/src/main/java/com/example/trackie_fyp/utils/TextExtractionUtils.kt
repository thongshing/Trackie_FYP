package com.example.trackie_fyp.utils


import java.text.SimpleDateFormat
import android.util.Log
import java.text.ParseException
import java.util.Calendar
import java.util.Locale


fun extractAmountAfterTotal(lines: List<String>): Double? {
    var foundTotal = false
    for (line in lines) {
        val normalizedLine = normalizeText(line)
        if (normalizedLine.contains("total", ignoreCase = true)) {
            foundTotal = true
        } else if (foundTotal) {
            // Find the first valid amount with exactly two decimal places after "total"
            val regex = Regex("\\b\\d+\\.\\d{2}\\b")
            val match = regex.find(normalizedLine)
            if (match != null) {
                val amountString = match.value.replace(",", "")
                Log.d("ReceiptViewModel", "Extracting amount after 'total' from text: $normalizedLine, found amount: $amountString")
                return amountString.toDoubleOrNull()
            }
        }
    }
    return null
}

// Utility function to extract the largest amount with RM prefix from a single line of text
fun extractLargestAmountWithPrefixRM(text: String): Double? {
    val regex = Regex("rm\\s*(\\d+(?:\\.\\d{1,2})?)", RegexOption.IGNORE_CASE)
    val matches = regex.findAll(text)
    var largestAmount: Double? = null
    for (match in matches) {
        val amount = match.groups[1]?.value?.replace(",", "")?.toDoubleOrNull()
        if (amount != null) {
            if (largestAmount == null || amount > largestAmount) {
                largestAmount = amount
            }
        }
    }
    return largestAmount
}


// Utility function to clean and normalize extracted text
fun normalizeText(text: String): String {
    return text.toLowerCase()
        .replace("\\s+".toRegex(), " ")
        .replace("[^a-z0-9., ]".toRegex(), "")
        .replace("t0tal", "total")
        .replace("total,", "total")
        .replace("iotal", "total") // Add this line
        .replace("lotal", "total") // Add this line
        .replace("(?<=\\d) (?=\\d{2})".toRegex(), ".") // Replace spaces between digits representing decimal point
        .trim()
}

fun extractDate(text: String): String? {
    // Define regex patterns for various date formats
    val regexPatterns = listOf(
        "\\b\\d{2}/\\d{2}/\\d{4}\\b",   // DD/MM/YYYY
        "\\b\\d{4}/\\d{2}/\\d{2}\\b",   // YYYY/MM/DD
        "\\b\\d{2}/\\d{2}/\\d{2}\\b",   // DD/MM/YY
        "\\b\\d{2}/\\d{2}/\\d{2}\\b",   // YY/MM/DD
        "\\b\\d{2}\\.\\d{2}\\.\\d{4}\\b", // DD.MM.YYYY
        "\\b\\d{4}\\.\\d{2}\\.\\d{2}\\b", // YYYY.MM.DD
        "\\b\\d{2}\\.\\d{2}\\.\\d{2}\\b", // DD.MM.YY
        "\\b\\d{2}-\\d{2}-\\d{4}\\b",   // DD-MM-YYYY
        "\\b\\d{4}-\\d{2}-\\d{2}\\b",   // YYYY-MM-DD
        "\\b\\d{2}-\\d{2}-\\d{2}\\b",   // DD-MM-YY
        "\\b\\d{2}\\d{2}\\d{4}\\b",     // DDMMYYYY
        "\\b\\d{6}\\b"                 // DDMMYY
    )

    // Check for keywords that imply a date context
    val keywords = listOf("date", "purchase date", "transaction date", "dated")

    // First, check for keywords in the text
    for (keyword in keywords) {
        if (text.contains(keyword, ignoreCase = true)) {
            // If a keyword is found, prioritize extracting the date using regex patterns
            for (pattern in regexPatterns) {
                val regex = Regex(pattern)
                val match = regex.find(text)
                if (match != null) {
                    val extractedDate = match.value
                    if (isValidDate(extractedDate)) {
                        // Normalize to DD.MM.YYYY format
                        return normalizeDate(extractedDate)
                    }
                }
            }
            break
        }
    }

    // If no keywords are found or no valid date matched the keywords, try to interpret numbers as DDMMYY
    val potentialDate = text.replace("[^0-9]".toRegex(), "")
    if (potentialDate.length == 6) {
        val day = potentialDate.substring(0, 2)
        val month = potentialDate.substring(2, 4)
        val year = potentialDate.substring(4, 6)
        val formattedDate = "$day.$month.$year"
        if (isValidDate(formattedDate)) {
            return formattedDate
        }
    }

    // If still no valid date is found, return null
    return null
}

// Function to check if a date string is valid
fun isValidDate(dateStr: String): Boolean {
    val formats = arrayOf("dd/MM/yyyy", "yyyy/MM/dd", "dd/MM/yy", "yy/MM/dd", "dd.MM.yyyy",
        "yyyy.MM.dd", "dd.MM.yy", "dd-MM-yyyy", "yyyy-MM-dd", "dd-MM-yy",
        "ddMMyyyy", "ddMMyy")
    for (format in formats) {
        val sdf = SimpleDateFormat(format, Locale.US)
        sdf.isLenient = false
        try {
            sdf.parse(dateStr)
            return true
        } catch (e: Exception) {
            // Parsing error, try next format
        }
    }
    return false
}


// Function to normalize date to DD.MM.YYYY format
fun normalizeDate(dateStr: String): String {
    val formats = arrayOf("dd/MM/yyyy", "yyyy/MM/dd", "dd.MM.yyyy", "yyyy.MM.dd", "dd-MM-yyyy", "yyyy-MM-dd", "ddMMyyyy")
    for (format in formats) {
        val sdf = SimpleDateFormat(format, Locale.US)
        sdf.isLenient = false
        try {
            val date = sdf.parse(dateStr)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: throw ParseException("Date parsing failed", 0)

            // Extract year from the calendar instance
            val year = calendar.get(Calendar.YEAR)

            // Check if the extracted year is valid
            if (year < 100) {
                // If year is less than 100, assume it's in the 20th century
                calendar.set(Calendar.YEAR, year + 2000)
            } else if (year in 1000..9999) {
                // If year is in the range of 1000 to 9999, use it directly
                calendar.set(Calendar.YEAR, year)
            } else {
                throw ParseException("Invalid year extracted", 0)
            }

            // Format date to DD.MM.YYYY
            return SimpleDateFormat("dd.MM.yyyy", Locale.US).format(calendar.time)
        } catch (e: Exception) {
            // Parsing error, try next format
        }
    }
    return dateStr // Return original if normalization fails
}

fun extractTotalAmountAndDate(ocrResult: List<String>): Pair<Double?, String?> {
    var largestAmount: Double? = null
    var foundRMAmount = false
    var extractedDate: String? = null

    for (item in ocrResult) {
        val normalizedItem = normalizeText(item)
        Log.d("ReceiptViewModel", "Normalized OCR item: $normalizedItem")

        // Extract amount
        val amount = extractLargestAmountWithPrefixRM(normalizedItem)
        if (amount != null) {
            foundRMAmount = true
            if (largestAmount == null || amount > largestAmount) {
                largestAmount = amount
            }
        }

        // Extract date if not already found
        if (extractedDate == null) {
            extractedDate = extractDate(normalizedItem)
        }
    }

    if (!foundRMAmount) {
        val amount = extractAmountAfterTotal(ocrResult)
        if (amount != null) {
            if (largestAmount == null || amount > largestAmount) {
                largestAmount = amount
            }
        }
    }

    Log.d("ReceiptViewModel", "Largest amount extracted: $largestAmount, Date extracted: $extractedDate")
    return Pair(largestAmount, extractedDate)
}


