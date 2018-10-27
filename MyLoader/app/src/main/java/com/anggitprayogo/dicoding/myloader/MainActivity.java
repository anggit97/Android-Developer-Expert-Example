package com.anggitprayogo.dicoding.myloader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener{

    private final String TAG = getClass().getSimpleName();

    private ListView lvContact;
    private ProgressBar pbContact;
    private ContactAdapter contactAdapter;
    private final int CONTACT_LOAD_ID = 110;
    private final int CONTACT_PHONE_ID = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = findViewById(R.id.lv_contact);
        pbContact = findViewById(R.id.progress_bar);

        lvContact.setVisibility(View.INVISIBLE);
        pbContact.setVisibility(View.VISIBLE);

        contactAdapter = new ContactAdapter(this, null, true);
        lvContact.setAdapter(contactAdapter);
        lvContact.setOnItemClickListener(this);
        getSupportLoaderManager().initLoader(CONTACT_LOAD_ID, null, this);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = null;
        if (i == CONTACT_LOAD_ID){
            String[] projectFields = new String[]{
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_URI
            };

            cursorLoader = new CursorLoader(MainActivity.this,
                    ContactsContract.Contacts.CONTENT_URI,
                    projectFields,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1",
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC"
                    );
        }

        if (i == CONTACT_PHONE_ID){
            String[] projectionsFields = new String[]{
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            cursorLoader = new CursorLoader(
                    MainActivity.this,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projectionsFields,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                            ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + " AND " +
                            ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + "=1",
                    new String[]{bundle.getString("id")},
                    null
            );
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "LoadFinished");
        if (loader.getId() == CONTACT_LOAD_ID){
            if (cursor.getCount() > 0){
                lvContact.setVisibility(View.VISIBLE);
                pbContact.setVisibility(View.GONE);
                contactAdapter.swapCursor(cursor);
            }
        }
        if (loader.getId() == CONTACT_PHONE_ID){
            String contactNumber = null;
            if (cursor.moveToFirst()) {
                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            Intent dialIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:"+contactNumber));
            startActivity(dialIntent);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == CONTACT_LOAD_ID){
            lvContact.setVisibility(View.VISIBLE);
            pbContact.setVisibility(View.GONE);
            contactAdapter.swapCursor(null);
            Log.d(TAG, "LoaderReset");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = contactAdapter.getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        long mContactId = cursor.getLong(0);
        Log.d(TAG, "Position : "+position+" "+mContactId);
        getPhoneNumber(String.valueOf(mContactId));
    }

    private void getPhoneNumber(String contactID){
        Bundle bundle = new Bundle();
        bundle.putString("id", contactID);
        getSupportLoaderManager().restartLoader(CONTACT_PHONE_ID, bundle, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
