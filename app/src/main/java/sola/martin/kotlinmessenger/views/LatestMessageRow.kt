package sola.martin.kotlinmessenger.views

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_messages_row.view.*
import sola.martin.kotlinmessenger.R
import sola.martin.kotlinmessenger.models.ChatMessage
import sola.martin.kotlinmessenger.models.User

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>(){

    var chatPartnerUser: User? = null


    override fun getLayout(): Int {
        return R.layout.latest_messages_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.latestmessage_textview_latestmessages_row.text = chatMessage.text

        val chatPartner: String
        if(chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chatPartner = chatMessage.toId
        }else{
            chatPartner = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartner")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                Log.d("LatestMessage", "Username name is: ${chatPartnerUser?.username}")
                viewHolder.itemView.username_textview_latestmessages_row.text = chatPartnerUser?.username

                val targetImageView = viewHolder.itemView.latest_message_imageview
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)

            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}
