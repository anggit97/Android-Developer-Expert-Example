package com.anggitprayogo.dicoding.notificationexample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.anggitprayogo.dicoding.notificationexample.NotificationService.REPLY_ACTION;

public class ReplyActivity extends AppCompatActivity {

    private static final String KEY_MESSAGE_ID = "key_message_id";
    private static final String KEY_NOTIFICATION_ID = "key_notify_id";

    private int messageId;
    private int noticationId;
    private EditText mEditReply;

    public static Intent getReplyMessageIntent(Context context, int noticationId, int messageId){
        Intent intent = new Intent(context, ReplyActivity.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_MESSAGE_ID, messageId);
        intent.putExtra(KEY_NOTIFICATION_ID, noticationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();

        if (REPLY_ACTION.equalsIgnoreCase(intent.getAction())){
            messageId = intent.getIntExtra(KEY_MESSAGE_ID, 0);
            noticationId = intent.getIntExtra(KEY_NOTIFICATION_ID, 0);
        }

        mEditReply = findViewById(R.id.edit_reply);
        ImageButton sendButton = findViewById(R.id.button_send);

        sendButton.setOnClickListener(v ->{
            sendMessage(noticationId, messageId);
        });
    }

    private void sendMessage(int noticationId, int messageId) {
        updateNotification(noticationId);
        String message = mEditReply.getText().toString().trim();
        Toast.makeText(this, "Message ID: " + messageId + "\nMessage: " + message,
                Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateNotification(int noticationId) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle("Terkirim")
                .setContentText("Isi Terkirim");

        notificationManagerCompat.notify(noticationId, builder.build());
    }
}
