package lanchuang.wgc.WGCContactsSearch;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState )
	{
		// super.onCreate(savedInstanceState);

		TextView tv = new TextView(this);
		String string = "";
		super.onCreate(savedInstanceState);
		// 得到ContentResolver对象
		ContentResolver cr = getContentResolver();
		// 取得电话本中开始一项的光标
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI ,null ,
		        null ,null ,null);
		// 向下移动光标
		while(cursor.moveToNext())
		{
			// 取得联系人名字
			int nameFieldColumnIndex = cursor
			        .getColumnIndex(PhoneLookup.DISPLAY_NAME);
			String contact = cursor.getString(nameFieldColumnIndex);
			// 取得电话号码
			String ContactId = cursor.getString(cursor
			        .getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(
			        ContactsContract.CommonDataKinds.Phone.CONTENT_URI ,null ,
			        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
			                + ContactId ,null ,null);
			while(phone.moveToNext())
			{
				String PhoneNumber = phone
				        .getString(phone
				                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				string += (contact + ":" + PhoneNumber + "\n");
			}
		}
		cursor.close();
		// 设置TextView显示的内容
		tv.setText(string);
		// 显示到屏幕
		 setContentView(tv);
//		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main ,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item )
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
