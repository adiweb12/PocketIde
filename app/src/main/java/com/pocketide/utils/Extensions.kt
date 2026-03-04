package com.pocketide.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import java.io.File

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun File.ensureExists(): File { if (!exists()) mkdirs(); return this }
fun String.toSafeFileName(): String = replace(Regex("[^a-zA-Z0-9._-]"), "_")
