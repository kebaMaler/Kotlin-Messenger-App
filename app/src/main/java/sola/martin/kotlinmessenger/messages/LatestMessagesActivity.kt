package sola.martin.kotlinmessenger.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.latest_messages_row.view.*
import sola.martin.kotlinmessenger.registerlogin.LoginActivity
import sola.martin.kotlinmessenger.R
import sola.martin.kotlinmessenger.models.ChatMessage
import sola.martin.kotlinmessenger.models.User
import sola.martin.kotlinmessenger.registerlogin.RegisterActivity
import sola.martin.kotlinmessenger.registerlogin.tag
import sola.martin.kotlinmessenger.views.LatestMessageRow
import java.net.UnknownServiceException

class LatestMessagesActivity : AppCompatActivity() {

    companion object{
        var currentUser: User? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        recyclerview_latest_messages.adapter = adapter
        recyclerview_latest_messages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        adapter.setOnItemClickListener { item, view ->
            val userItem = item
            Log.d("LatestMessagesActivity", "item is pressed")
            val intent = Intent(this, ChatLogActivity::class.java)

            val row = item as LatestMessageRow

            intent.putExtra(NewMessageActivity.USER_KEY,item.chatPartnerUser)
            startActivity(intent)

        }
//        adapter.setOnItemClickListener { item, view ->
//            Log.d(TAG, "123")
//        }

        listenForLatestMessages()

        fetchCurrentUser()

        verifyUserIsLoggedIn()


    }

    var latestMessagesMap = HashMap<String, ChatMessage>()


    private fun refreshRecyclerViewMessages(){
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener{

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }
            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }

    val adapter = GroupAdapter<ViewHolder>()

//    private fun setupDummyRows(){
//
//        adapter.add(LatestMessageRow())
//        adapter.add(LatestMessageRow())
//        adapter.add(LatestMessageRow())
//        adapter.add(LatestMessageRow())
//
//    }

    private fun fetchCurrentUser(){
        val uid =FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d("LatestMessages", "Current user ${currentUser?.username}")
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    private fun verifyUserIsLoggedIn() {

        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_new_massage ->{
                val intent = Intent(this, NewMessageActivity:: class.java)
                startActivity(intent)
            }
            R.id.menu_sing_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity:: class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
