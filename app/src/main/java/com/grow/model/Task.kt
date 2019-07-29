package com.grow.model

import java.io.Serializable

class Task (var title: String, var description: String?) : Serializable {
  override fun toString(): String {
    return title
  }
}
