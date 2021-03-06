package notafk.great_uni_hack_2016;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
//TODO remove unnecessary prints and logs
public class NewPictureActivity extends Activity {

	private PhotosObserver instUploadObserver = new PhotosObserver();
	public String absolutePathOfNewPhoto;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("ON Create");
		super.onCreate(savedInstanceState);

		this.getApplicationContext()
				.getContentResolver()
				.registerContentObserver(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
						instUploadObserver);
		Log.d("INSTANT", "registered content observer");
	}

	@Override
	public void onResume() {
		System.out.println("Resume");
		super.onResume();
	}

	@Override
	public void onDestroy() {
		System.out.println("Destroy");
		super.onDestroy();
		this.getApplicationContext().getContentResolver()
				.unregisterContentObserver(instUploadObserver);
		Log.d("INSTANT", "unregistered content observer");
	}

	private class PhotosObserver extends ContentObserver {

		public PhotosObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {

			super.onChange(selfChange);
			Media media = readFromMediaStore(getApplicationContext(),
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//			saved = "I detected " + media.file.getName();
			System.out.println("I detected picture "
//				+ MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//				+ "$"
				+ media.file.getAbsolutePath());
			absolutePathOfNewPhoto = media.file.getAbsolutePath();
			Log.d("INSTANT", "detected picture");
		}
	}

	private Media readFromMediaStore(Context context, Uri uri) {
//		System.out.println("read From Media Store");
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, "date_added DESC");
		Media media = null;
		if (cursor.moveToNext()) {
			int dataColumn = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			String filePath = cursor.getString(dataColumn);
			int mimeTypeColumn = cursor
					.getColumnIndexOrThrow(MediaColumns.MIME_TYPE);
			String mimeType = cursor.getString(mimeTypeColumn);
			media = new Media(new File(filePath), mimeType);
		}
		cursor.close();
		return media;
	}

	private class Media {
		private File file;
		@SuppressWarnings("unused")
		private String type;

		public Media(File file, String type) {
			this.file = file;
			this.type = type;
		}
	}
}
