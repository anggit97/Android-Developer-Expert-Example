package com.anggitprayogo.dicoding.notificationexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import static com.anggitprayogo.dicoding.notificationexample.NotificationService.REPLY_ACTION;

public class NotificationBroadcastReciever extends BroadcastReceiver{

    private static String KEY_NOTIFICATION_ID = "key_notification_id";
    private static String KEY_MESSAGE_ID = "key_message_id";

    public static Intent getReplyMessageIntent(Context context, int notificationId, int messageId){
        Intent intent = new Intent(context, NotificationBroadcastReciever.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_NOTIFICATION_ID, notificationId);
        intent.putExtra(KEY_MESSAGE_ID, messageId);
        return intent;
    }

    public NotificationBroadcastReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (REPLY_ACTION.equals(intent.getAction())){
            CharSequence message = NotificationService.getReplyMessage(intent);
            int messageId = intent.getIntExtra(KEY_MESSAGE_ID, 0);

            Toast.makeText(context, "Message id "+messageId+ " \n Message : "+message, Toast.LENGTH_SHORT).show();

            int notifyId = intent.getIntExtra(KEY_NOTIFICATION_ID, 1);
            updateNotification(context, notifyId);
        }else{
            Toast.makeText(context, "Message id ", Toast.LENGTH_LONG).show();
        }
    }

    private void updateNotification(Context context, int notifyId) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("notif title sent")
                .setContentText("Isi notifcatio")
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp);

        notificationManagerCompat.notify(notifyId, builder.build());

    }
}
