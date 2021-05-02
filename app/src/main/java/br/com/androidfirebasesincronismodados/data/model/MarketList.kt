package br.com.androidfirebasesincronismodados.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class MarketList (
    @DocumentId var id: String? = "", // documentId do Firestore
    val productName: String? = "",
    val section: String? =""
)