package cn.edu.gdmec.s07131018.remotecalldemo;

import android.R.bool;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.gdmec.remoteService.AllResult;
import cn.edu.gdmec.remoteService.IMathService;

public class MainActivity extends Activity {
	private TextView tv;
	private Intent intent = new Intent("cn.edu.gdmec.s07131018.RemoteService");
	private IMathService myService;
	private boolean isBind = false;
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			myService = IMathService.Stub.asInterface(service);
			Toast.makeText(MainActivity.this,"service binder", 1000).show();
			isBind=true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			myService=null;
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void Add(View view){
		try {
			tv.setText("200+300="+String.valueOf(myService.Add(200, 300)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Other(View view){
		AllResult allResult=null ;
		long a = Math.round(Math.random()*100);
		long b = Math.round(Math.random()*100);
		try {
			allResult =myService.CompuerAny(a, b);
			
			String str =  String.valueOf(a)+" and "+String.valueOf(b)+"\n";
			str +="add :"+String.valueOf(allResult.AddResult)+"\n";
			str +="sub :"+String.valueOf(allResult.SubResult)+"\n";
			str +="mul :"+String.valueOf(allResult.MulResult)+"\n";
			str +="div :"+String.valueOf(allResult.DivResult)+"\n";
			tv.setText(str);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Bind(View view){
		bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
	}
	public void Unbind(View view){
		if(isBind){
			unbindService(mConnection);
			Toast.makeText(this, "unbind", 1000).show();
			isBind=false;
		}
	}
}
